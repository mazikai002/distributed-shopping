package com.itheima.ds.vo;

import com.itheima.ds.model.entity.SeckillGoods;

public class SeckillOrderVo {
	
	private SeckillGoods goods;
	
	private long userId;

	public SeckillGoods getGoods() {
		return goods;
	}

	public void setGoods(SeckillGoods goods) {
		this.goods = goods;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	

}