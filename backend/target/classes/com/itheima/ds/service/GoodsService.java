package com.itheima.ds.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itheima.ds.model.entity.SeckillGoods;
import com.itheima.ds.mapper.GoodsMapper;
import com.itheima.ds.mapper.SeckillUserMapper;
import com.itheima.ds.model.vo.GoodsVo;

/**
 * 商品服务接口
 */
public interface GoodsService {
    
    /**
     * 获取商品列表
     * @return 商品列表
     */
    List<GoodsVo> listGoodsVo();
    
    /**
     * 根据商品ID获取商品详情
     * @param goodsId 商品ID
     * @return 商品详情
     */
    GoodsVo getGoodsVoByGoodsId(Long goodsId);
    
    /**
     * 更新商品库存
     * @param goodsId 商品ID
     * @param stock 库存
     */
    void updateStock(Long goodsId, Integer stock);
    
    /**
     * 使用Redis更新商品库存，提高性能
     * @param goodsId 商品ID
     * @param stock 库存
     */
    void updateStockWithRedis(Long goodsId, Integer stock);
}

@Service
public class GoodsServiceImpl implements GoodsService {

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
