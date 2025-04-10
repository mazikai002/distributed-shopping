package com.itheima.ds.dao;

import com.itheima.ds.model.entity.Goods;
import com.itheima.ds.model.entity.SeckillGoods;
import com.itheima.ds.model.vo.GoodsVo;
import java.util.List;

/**
 * 商品数据访问接口
 */
public interface GoodsDao {
    /**
     * 获取商品列表
     */
    List<GoodsVo> getGoodsVoList();
    
    /**
     * 根据商品ID获取商品详情
     */
    GoodsVo getGoodsVoByGoodsId(long goodsId);
    
    /**
     * 减少库存
     */
    int reduceStock(SeckillGoods goods);
    
    /**
     * 查询库存
     */
    int selectStock(long goodsId);
    
    /**
     * 根据商品ID获取商品
     */
    Goods getGoodsByGoodsId(long goodsId);
} 