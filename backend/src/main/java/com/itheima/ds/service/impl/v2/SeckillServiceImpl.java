package com.itheima.ds.service.impl.v2;

import com.itheima.ds.common.exception.GlobalException;
import com.itheima.ds.component.cache.redis.RedisClient;
import com.itheima.ds.model.entity.SeckillOrder;
import com.itheima.ds.model.entity.SeckillUser;
import com.itheima.ds.model.entity.VoucherOrder;

import com.itheima.ds.service.GoodsService;
import com.itheima.ds.service.ISeckillService;
import com.itheima.ds.service.OrderService;
import com.itheima.ds.common.utils.RedisIdWorker;
import com.itheima.ds.common.utils.UserHolder;
import com.itheima.ds.model.vo.GoodsVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 秒杀服务实现类（v2版本）- 使用Redis进行库存扣减
 */
@Slf4j
@Service("v2SeckillService")
@RequiredArgsConstructor
public class SeckillServiceImpl implements ISeckillService {

    private final GoodsService goodsService;
    private final OrderService orderService;
    private final RedisClient redisClient;
    private final RedisIdWorker redisIdWorker;
    
    private static final String STOCK_KEY = "seckill:stock:";
    private static final String SECKILL_ORDER_KEY = "seckill:order:";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * Lua脚本，用于原子性地执行库存检查和扣减
     */
    private static final String STOCK_SCRIPT = 
        "if (redis.call('exists', KEYS[1]) == 1) then " +
            "local stock = tonumber(redis.call('get', KEYS[1])); " +
            "if (stock <= 0) then " +
                "return -1; " +
            "end; " +
            "redis.call('decr', KEYS[1]); " +
            "return stock - 1; " +
        "else " +
            "return -2; " +
        "end;";

    /**
     * 执行秒杀操作 - 接口方法，匹配ISeckillService接口
     */
    @Override
    public Long doSeckill(Long voucherId) {
        return this.processSecKill(voucherId);
    }
    
    /**
     * 执行秒杀操作 - 提供给Controller调用的方法
     */
    public Long seckill(Long voucherId) {
        return this.processSecKill(voucherId);
    }
    
    /**
     * 秒杀处理的实际逻辑
     */
    @Transactional(rollbackFor = Exception.class)
    private Long processSecKill(Long voucherId) {
        // 获取当前用户，这里假设从上下文获取，实际项目中可能需要从请求中获取
        SeckillUser user = getCurrentUser();
        if (user == null) {
            throw new GlobalException("用户未登录");
        }
        
        // 1. 判断当前用户是否已经秒杀过该商品（从Redis中查询）
        String orderKey = SECKILL_ORDER_KEY + user.getId() + ":" + voucherId;
        boolean hasOrder = redisClient.exists(orderKey);
        if (hasOrder) {
            throw new GlobalException("重复秒杀");
        }
        
        // 2. 使用Redis Lua脚本进行原子性库存检查和扣减
        String stockKey = STOCK_KEY + voucherId;
        
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(STOCK_SCRIPT);
        redisScript.setResultType(Long.class);
        
        Long result = redisTemplate.execute(redisScript, Arrays.asList(stockKey));
        
        if (result == null || result < 0) {
            log.error("Redis库存扣减失败, voucherId: {}, result: {}", voucherId, result);
            if (result == -1) {
                throw new RuntimeException("商品库存不足");
            } else {
                throw new RuntimeException("商品库存未初始化");
            }
        }
        
        log.info("Redis库存扣减成功, voucherId: {}, 剩余库存: {}", voucherId, result);
        
        // 3. 获取商品信息
        GoodsVO goods = goodsService.getGoodsVoByGoodsId(voucherId);
        
        // 4. 创建订单（异步方式）
        SeckillOrder order = new SeckillOrder();
        long orderId = redisIdWorker.nextId("order");
        order.setId(orderId);
        order.setUserId(user.getId());
        order.setGoodsId(voucherId);
        order.setGoodsName(goods.getGoodsName());
        order.setGoodsPrice(goods.getSeckillPrice() != null ? goods.getSeckillPrice().doubleValue() : null);
        order.setCreateTime(LocalDateTime.now());
        order.setStatus(1); // 1: 已支付
        
        // 5. 保存订单到Redis
        redisClient.set(orderKey, order);
        
        // 6. 使用Redis创建订单
        Long orderIdFromRedis = orderService.createOrderWithRedis(order);
        
        // 7. 异步更新数据库库存（实际项目中应该通过消息队列异步处理）
        asyncUpdateStock(voucherId, goods.getStockCount() - 1);
        
        return orderIdFromRedis;
    }
    
    /**
     * 异步更新库存到数据库
     * 在实际系统中，这应该是一个通过消息队列触发的异步操作
     */
    private void asyncUpdateStock(Long voucherId, Integer newStock) {
        try {
            // 这里简化实现，实际项目中应通过消息队列异步更新
            goodsService.updateStock(voucherId, newStock);
        } catch (Exception e) {
            log.error("异步更新库存失败：voucherId={}, newStock={}", voucherId, newStock, e);
            // 实际系统应有更完善的异常处理和重试机制
        }
    }
    
    /**
     * 预加载库存到Redis - 提供给Controller调用的方法
     */
    public void preloadStock(Long voucherId) {
        this.preloadStockToRedis(voucherId);
    }
    
    /**
     * 系统初始化时预热库存缓存
     * 将商品库存加载到Redis中
     */
    public void preloadStockToRedis(Long voucherId) {
        GoodsVO goods = goodsService.getGoodsVoByGoodsId(voucherId);
        if (goods != null && goods.getStockCount() > 0) {
            // 将库存设置到Redis
            String stockKey = STOCK_KEY + voucherId;
            redisTemplate.opsForValue().set(stockKey, goods.getStockCount());
        }
    }

    // 模拟获取当前用户，实际项目中需要根据具体实现获取
    private SeckillUser getCurrentUser() {
        // 这里需要实现获取当前用户的逻辑
        // 简化示例，实际项目中通常从ThreadLocal或Session中获取
        return UserHolder.getUser();
    }
} 