package com.itheima.ds.service;

import java.util.List;
import com.itheima.ds.model.vo.GoodsVO;

/**
 * 商品服务接口
 */
public interface GoodsService {
    
    /**
     * 获取所有商品VO列表
     * @return 商品VO列表
     */
    List<GoodsVO> listGoodsVO();
    
    /**
     * 根据商品ID获取商品VO
     * @param goodsId 商品ID
     * @return 商品VO
     */
    GoodsVO getGoodsVoByGoodsId(Long goodsId);
    
    /**
     * 减少商品库存
     * @param goodsId 商品ID
     * @return 是否成功
     */
    boolean reduceStock(Long goodsId);
    
    /**
     * 更新商品库存
     * @param goodsId 商品ID
     * @param newStock 新库存
     * @return 是否成功
     */
    boolean updateStock(Long goodsId, Integer newStock);
    
    /**
     * 使用Redis更新商品库存，提高性能
     * @param goodsId 商品ID
     * @param stock 库存
     */
    void updateStockWithRedis(Long goodsId, Integer stock);
}
