package com.itheima.ds.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.itheima.ds.domain.Goods;
import com.itheima.ds.domain.SeckillGoods;
import com.itheima.ds.vo.GoodsVo;
 
@Mapper
public interface GoodsMapper { 
	 
	public List<GoodsVo> getGoodsVoList();
  
	public GoodsVo getGoodsVoByGoodsId(@Param("goodsId")long goodsId);
 
	public int reduceStock(SeckillGoods g);  
	
	public int selectStock(@Param("goodsId")long goodsId);

	public Goods getGoodsByGoodsId(@Param("goodsId") long goodId);    

}
