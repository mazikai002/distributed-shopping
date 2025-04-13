package com.itheima.ds.service;

/**
 * 秒杀服务接口
 */
public interface ISeckillService {
    
    /**
     * 执行秒杀
     * @param voucherId 优惠券ID
     * @return 订单ID
     */
    Long doSeckill(Long voucherId);
    
    /**
     * 执行秒杀 - 等同于doSeckill，为了兼容不同版本的调用
     * @param goodsId 商品ID
     * @return 订单ID
     */
    default Long seckill(Long goodsId) {
        return doSeckill(goodsId);
    }
    
    /**
     * 预加载商品库存到Redis
     * @param goodsId 商品ID
     */
    default void preloadStock(Long goodsId) {
        // 默认实现为空，由具体实现类提供功能
    }
} 