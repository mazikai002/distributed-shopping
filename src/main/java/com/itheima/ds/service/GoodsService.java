package com.itheima.ds.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itheima.ds.domain.SeckillGoods;
import com.itheima.ds.mapper.GoodsMapper;
import com.itheima.ds.mapper.SeckillUserMapper;
import com.itheima.ds.vo.GoodsVo;

@Service
public class GoodsService {

	@Autowired
	private GoodsMapper goodsMapper;
	
	
	@Autowired
	private SeckillUserMapper seckillUserMapper;
	
	
	public List<GoodsVo> getGoodsVoList(){
		
		/*List<SeckillGoods> seckillGoodList = this.seckillUserMapper.getSeckillGoodsList();
		
		//查询各秒杀商品的id
				List<Long> seckillGoodsIds = new ArrayList<Long>();
				List<GoodsVo> goodsVoList = new ArrayList<GoodsVo>();
				for(SeckillGoods sg : seckillGoodList){
					seckillGoodsIds.add(sg.getGoodsId());
				} 
				GoodsVo goodsVo = null;
				Goods goods = null;
				SeckillGoods seckillGoods = null;
				for(Long goodId : seckillGoodsIds){
					goods = this.goodsMapper.getGoodsByGoodsId(goodId);
					   System.out.println("---"+goods.getGoodsName()+"   "+goods.getGoodsImg()+"   "+goods.getGoodsPrice());
					goodsVo = new GoodsVo();
					
					goodsVo.setGoods(goods); 
					seckillGoods = this.getSeckillGoodsByGoodsId(goodId);
					goodsVo.setStockCount(seckillGoods.getStockCount());
					goodsVo.setStartDate(seckillGoods.getStartDate());
					goodsVo.setEndDate(seckillGoods.getEndDate());
					goodsVo.setSeckillPrice(seckillGoods.getSeckillPrice()); 
					
					goodsVoList.add(goodsVo);
					 
				}*/
		 
		//return goodsVoList;
		 return this.goodsMapper.getGoodsVoList(); 
	}

	
	
	public SeckillGoods getSeckillGoodsByGoodsId(long goodsId){
		return this.seckillUserMapper.getSeckillGoodsByGoodsId(goodsId);
	}
	
	
	
	public GoodsVo getGoodsVoByGoodsId(long goodsId) {
	  return this.goodsMapper.getGoodsVoByGoodsId(goodsId);
	}



	public boolean reduceStock(GoodsVo goods,long version) {
		SeckillGoods seckillGoods = new SeckillGoods();
		    seckillGoods.setGoodsId(goods.getId());
		    seckillGoods.setVersion(version);
	    int ret = goodsMapper.reduceStock(seckillGoods);
		 return ret>0;
	 }
	 

/*
	// @Transactional
	public boolean tx() { 
		User u1 = new User();
		u1.setId(2);
		u1.setName("222"); 
		userDao.insert(u1);
		
		User u2 = new User();
		u2.setId(1);
		u2.setName("1111"); 
		userDao.insert(u2);
		return true;
	}*/
	
}
