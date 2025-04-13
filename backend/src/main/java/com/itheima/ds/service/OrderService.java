package com.itheima.ds.service;

import java.util.List;
import com.itheima.ds.model.entity.SeckillOrder;
import com.itheima.ds.model.entity.SeckillUser;
import com.itheima.ds.model.vo.GoodsVO;
import com.itheima.ds.model.entity.OrderInfo;

/**
 * 订单服务接口
 */
public interface OrderService {
    
    /**
     * 获取订单详情
     * @param orderId 订单ID
     * @return 订单详情
     */
    SeckillOrder getOrderById(Long orderId);
    
    /**
     * 创建订单
     * @param order 订单信息
     * @return 订单ID
     */
    Long createOrder(SeckillOrder order);
    
    /**
     * 使用Redis创建订单，提高性能
     * @param order 订单信息
     * @return 订单ID
     */
    Long createOrderWithRedis(SeckillOrder order);
    
    /**
     * 获取用户的订单列表
     * @param userId 用户ID
     * @return 订单列表
     */
    List<SeckillOrder> getUserOrders(Long userId);
    
    /**
     * 通过用户ID和商品ID获取秒杀订单
     * @param userId 用户ID
     * @param goodsId 商品ID
     * @return 秒杀订单
     */
    SeckillOrder getSeckillOrderByUserIdGoodsId(Long userId, Long goodsId);

    List<String> findOrdersByTime(String crateTime);
    
    /**
     * 获取所有秒杀订单
     * @return 秒杀订单列表
     */
    List<SeckillOrder> getAllSeckillOrders();
    
    /**
     * 删除用户的秒杀订单
     * @param goodsId 商品ID
     * @param userId 用户ID
     */
    void deleteOrder(Long goodsId, Long userId);
    
    /**
     * 为用户创建秒杀订单
     * @param user 用户信息
     * @param goods 商品信息
     * @return 订单信息
     */
    OrderInfo createOrder(SeckillUser user, GoodsVO goods);
}
