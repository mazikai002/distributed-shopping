package com.itheima.ds.dao.mapper;

import com.itheima.ds.dao.GoodsDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.itheima.ds.model.entity.Goods;
import com.itheima.ds.model.entity.SeckillGoods;
import com.itheima.ds.model.vo.GoodsVo;
import java.util.List;

/**
 * 商品Mapper接口，实现GoodsDao接口
 */
@Mapper
public interface GoodsMapper implements GoodsDao {
    
    @Override
    List<GoodsVo> getGoodsVoList();
    
    @Override
    GoodsVo getGoodsVoByGoodsId(@Param("goodsId") long goodsId);
    
    @Override
    int reduceStock(SeckillGoods goods);
    
    @Override
    int selectStock(@Param("goodsId") long goodsId);
    
    @Override
    Goods getGoodsByGoodsId(@Param("goodsId") long goodsId);
} 