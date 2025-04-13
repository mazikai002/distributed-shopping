package com.itheima.ds.dao;

import com.itheima.ds.model.entity.OrderInfo;
import com.itheima.ds.model.entity.SeckillOrder;
import java.util.List;

/**
 * 订单数据访问接口
 */
public interface OrderDao {
    /**
     * 根据用户ID和商品ID查询秒杀订单
     */
    SeckillOrder getSeckillOrderByUserIdAndGoodsId(Long userId, long goodsId);
    
    /**
     * 插入订单信息
     */
    long insertOrderInfo(OrderInfo orderInfo);
    
    /**
     * 插入秒杀订单
     */
    void insertSeckillOrder(SeckillOrder seckillOrder);
    
    /**
     * 根据订单ID查询订单信息
     */
    OrderInfo getOrderById(long orderId);
    
    /**
     * 删除秒杀订单
     */
    void deleteSeckillOrderByGoodIdAndUserId(Long goodsId, Long userId);
    
    /**
     * 获取所有秒杀订单
     */
    List<SeckillOrder> getAllSeckillOrders();
} 