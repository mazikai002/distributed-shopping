package com.itheima.ds.dao.mapper;

import com.itheima.ds.dao.GoodsDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import com.itheima.ds.model.entity.Goods;
import com.itheima.ds.model.entity.SeckillGoods;
import com.itheima.ds.model.vo.GoodsVO;
import java.util.List;

/**
 * 商品Mapper接口，实现GoodsDao接口
 */
@Mapper
public interface GoodsMapper extends GoodsDao {
    
    @Override
    List<GoodsVO> getGoodsVoList();
    
    @Override
    GoodsVO getGoodsVoByGoodsId(@Param("goodsId") long goodsId);
    
    @Override
    int reduceStock(SeckillGoods goods);
    
    @Override
    int selectStock(@Param("goodsId") long goodsId);
    
    @Override
    Goods getGoodsByGoodsId(@Param("goodsId") long goodsId);
    
    /**
     * 更新商品库存
     * @param goodsId 商品ID
     * @param newStock 新库存数量
     * @return 影响行数
     */
    @Update("UPDATE seckill_goods SET stock_count = #{newStock} WHERE goods_id = #{goodsId}")
    int updateStock(@Param("goodsId") Long goodsId, @Param("newStock") Integer newStock);
} 