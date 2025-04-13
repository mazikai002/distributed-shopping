package com.itheima.ds.dao;

import com.itheima.ds.model.entity.Goods;
import com.itheima.ds.model.entity.SeckillGoods;
import com.itheima.ds.model.vo.GoodsVO;
import java.util.List;

/**
 * 商品数据访问接口
 */
public interface GoodsDao {
    /**
     * 获取商品列表
     */
    List<GoodsVO> getGoodsVoList();
    
    /**
     * 根据商品ID获取商品详情
     */
    GoodsVO getGoodsVoByGoodsId(long goodsId);
    
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
    
    /**
     * 更新商品库存
     * @param goodsId 商品ID
     * @param newStock 新库存数量
     * @return 影响行数
     */
    int updateStock(Long goodsId, Integer newStock);
} 