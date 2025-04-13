package com.itheima.ds.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itheima.ds.model.entity.SeckillGoods;
import com.itheima.ds.dao.mapper.GoodsMapper;
import com.itheima.ds.dao.mapper.SeckillUserMapper;
import com.itheima.ds.model.vo.GoodsVO;
import com.itheima.ds.service.GoodsService;

@Service
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private GoodsMapper goodsMapper;
	
	@Autowired
	private SeckillUserMapper seckillUserMapper;
	
	@Override
	public List<GoodsVO> listGoodsVO() {
		return this.goodsMapper.getGoodsVoList();
		/*List<SeckillGoods> seckillGoodList = this.seckillUserMapper.getSeckillGoodsList();
		List<Goods> goodsList = this.goodsMapper.selectList(null);
		
		if(null == seckillGoodList || seckillGoodList.size() <=0){
			return Collections.emptyList();  //默认空list
		}
		
		if(null == goodsList || goodsList.size() <=0){
			return Collections.emptyList();  //默认空list
		}
		
		List<GoodsVo>  goodsVoList = new ArrayList<>();
		for(SeckillGoods  seckillGoods : seckillGoodList){
			GoodsVo goodsVo = new GoodsVo();
			for(Goods good: goodsList){
				if(seckillGoods.getGoodsId().equals(good.getId())){
					BeanUtils.copyProperties(seckillGoods, goodsVo);
					BeanUtils.copyProperties(good, goodsVo);
					goodsVoList.add(goodsVo);
					break;
				}
			}
		}
		
		return goodsVoList;*/
	}
	
	@Override
	public GoodsVO getGoodsVoByGoodsId(Long goodsId) {
		return this.goodsMapper.getGoodsVoByGoodsId(goodsId);
	}
	
	@Override
	public boolean reduceStock(Long goodsId) {
		SeckillGoods goods = new SeckillGoods();
		goods.setGoodsId(goodsId);
		int result = goodsMapper.reduceStock(goods);
		return result > 0;
	}
	
	@Override
	public boolean updateStock(Long goodsId, Integer newStock) {
		int result = goodsMapper.updateStock(goodsId, newStock);
		return result > 0;
	}

	public List<GoodsVO> getGoodsVoList(){
		// Commented out legacy code
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

	@Override
	public void updateStockWithRedis(Long goodsId, Integer stock) {
		// Implementation for updateStockWithRedis using Redis
	}
} 