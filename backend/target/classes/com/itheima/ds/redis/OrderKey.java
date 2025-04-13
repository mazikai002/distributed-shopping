package com.itheima.ds.redis;

/**
 * 订单相关的Redis键
 */
public class OrderKey extends BasePrefix {

    private OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    
    /**
     * 订单详情缓存
     */
    public static final OrderKey getById = new OrderKey(3600, "id");
    
    /**
     * 用户订单列表缓存
     */
    public static final OrderKey getByUserId = new OrderKey(3600, "uid");
    
    /**
     * 用户和商品的秒杀订单缓存
     */
    public static final OrderKey getOrderByUidGid = new OrderKey(3600, "ug");
} 