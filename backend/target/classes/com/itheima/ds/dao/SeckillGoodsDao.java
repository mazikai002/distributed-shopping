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
} 