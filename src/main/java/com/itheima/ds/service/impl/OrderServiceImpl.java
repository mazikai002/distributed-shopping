package com.itheima.ds.service.impl;

import com.itheima.ds.domain.SeckillOrder;
import com.itheima.ds.exception.GlobalException;
import com.itheima.ds.mapper.OrderMapper;
import com.itheima.ds.redis.OrderKey;
import com.itheima.ds.redis.RedisService;
import com.itheima.ds.service.OrderService;
import com.itheima.ds.utils.RedisIdWorker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单服务实现类
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final RedisService redisService;
    private final RedisIdWorker redisIdWorker;
    
    private static final String ORDER_ID_PREFIX = "order";
    private static final String ORDER_USER_KEY = "order:user:";

    @Override
    public SeckillOrder getOrderById(Long orderId) {
        // 先从缓存中获取
        SeckillOrder order = redisService.get(OrderKey.getById, "" + orderId, SeckillOrder.class);
        if(order != null) {
            return order;
        }
        
        // 缓存未命中，从数据库查询
        order = orderMapper.getById(orderId);
        if(order != null) {
            // 写入缓存
            redisService.set(OrderKey.getById, "" + orderId, order);
        }
        return order;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createOrder(SeckillOrder order) {
        // 设置创建时间等
        order.setCreateTime(LocalDateTime.now());
        
        // 保存到数据库
        int result = orderMapper.insert(order);
        if(result <= 0) {
            throw new GlobalException("订单创建失败");
        }
        
        // 写入缓存
        redisService.set(OrderKey.getById, "" + order.getId(), order);
        
        return order.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createOrderWithRedis(SeckillOrder order) {
        // 1. 使用Redis生成ID
        long orderId = redisIdWorker.nextId(ORDER_ID_PREFIX);
        order.setId(orderId);
        
        // 2. 设置创建时间等
        order.setCreateTime(LocalDateTime.now());
        
        // 3. 保存到Redis
        String orderKey = OrderKey.getById.getPrefix() + orderId;
        redisService.set(orderKey, order);
        
        // 4. 保存到用户订单列表
        String userOrderKey = ORDER_USER_KEY + order.getUserId();
        redisService.sAdd(userOrderKey, String.valueOf(orderId));
        
        // 5. 异步保存到数据库
        int result = orderMapper.insert(order);
        if(result <= 0) {
            // 数据库保存失败，删除Redis缓存
            redisService.delete(orderKey);
            redisService.sRem(userOrderKey, String.valueOf(orderId));
            throw new GlobalException("订单创建失败");
        }
        
        return orderId;
    }

    @Override
    public List<SeckillOrder> getUserOrders(Long userId) {
        // 从Redis获取用户订单ID列表
        String userOrderKey = ORDER_USER_KEY + userId;
        List<String> orderIds = redisService.sMembers(userOrderKey);
        
        // 查询订单详情
        return orderMapper.getByUserId(userId);
    }

    @Override
    public SeckillOrder getSeckillOrderByUserIdGoodsId(Long userId, Long goodsId) {
        // 先查缓存
        SeckillOrder order = redisService.get(OrderKey.getOrderByUidGid, "" + userId + "_" + goodsId, SeckillOrder.class);
        if(order != null) {
            return order;
        }
        
        // 从数据库查询
        order = orderMapper.getOrderByUserIdGoodsId(userId, goodsId);
        if(order != null) {
            // 写入缓存
            redisService.set(OrderKey.getOrderByUidGid, "" + userId + "_" + goodsId, order);
        }
        return order;
    }
} 