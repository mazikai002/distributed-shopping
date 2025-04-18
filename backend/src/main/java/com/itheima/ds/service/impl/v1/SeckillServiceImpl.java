package com.itheima.ds.service.impl.v1;

import com.itheima.ds.model.entity.SeckillOrder;
import com.itheima.ds.model.entity.SeckillUser;
import com.itheima.ds.service.ISeckillService;
import com.itheima.ds.service.GoodsService;
import com.itheima.ds.service.OrderService;
import com.itheima.ds.service.SeckillService;
import com.itheima.ds.model.vo.GoodsVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("v1SeckillService")
@RequiredArgsConstructor
public class SeckillServiceImpl implements ISeckillService {

    private final GoodsService goodsService;
    private final OrderService orderService;
    private final SeckillService seckillService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long doSeckill(Long voucherId) {
        // 获取当前用户，这里假设从上下文获取，实际项目中可能需要从请求中获取
        SeckillUser user = getCurrentUser();
        if (user == null) {
            throw new RuntimeException("用户未登录");
        }

        // 判断库存
        GoodsVO goods = goodsService.getGoodsVoByGoodsId(voucherId);
        int stock = goods.getStockCount();
        if (stock <= 0) {
            throw new RuntimeException("秒杀已结束");
        }

        // 判断是否已经秒杀到了
        SeckillOrder order = orderService.getSeckillOrderByUserIdGoodsId(user.getId(), voucherId);
        if (order != null) {
            throw new RuntimeException("重复秒杀");
        }

        // 减库存 下订单 写入秒杀订单
        return seckillService.seckill(user, goods).getId();
    }

    // 模拟获取当前用户，实际项目中需要根据具体实现获取
    private SeckillUser getCurrentUser() {
        // 这里需要实现获取当前用户的逻辑
        return null;
    }
} 