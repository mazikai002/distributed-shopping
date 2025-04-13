package com.itheima.ds.dao;

import com.itheima.ds.model.entity.SeckillGoods;
import java.util.List;

/**
 * 秒杀商品数据访问接口
 */
public interface SeckillGoodsDao {
    /**
     * 获取秒杀商品列表
     */
    List<SeckillGoods> getSeckillGoodsList();
    
    /**
     * 根据商品ID获取秒杀商品
     */
    SeckillGoods getSeckillGoodsByGoodsId(long goodsId);
    
    /**
     * 获取秒杀商品VO列表
     */
    List<SeckillGoods> getSeckillGoodsVoList();
    
    /**
     * 获取秒杀商品版本号
     * @param goodsId 商品ID
     * @return 版本号
     */
    long getSeckillGoodsVersion(Long goodsId);
    
    /**
     * 减少库存，使用乐观锁
     * @param goodsId 商品ID
     * @param version 版本号
     * @return 影响的行数
     */
    int reduceStockByVersion(Long goodsId, long version);
} 