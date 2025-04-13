package com.itheima.ds.model.vo;

import com.itheima.ds.model.entity.OrderInfo;
import com.itheima.ds.model.entity.SeckillUser;

/**
 * 订单详情VO
 */
public class OrderDetailVO {
	
	private GoodsVO goods;
	
	private OrderInfo order;
	
	private SeckillUser user;
	
	public GoodsVO getGoods() {
		return goods;
	}
	public void setGoods(GoodsVO goods) {
		this.goods = goods;
	}
	public OrderInfo getOrder() {
		return order;
	}
	public void setOrder(OrderInfo order) {
		this.order = order;
	}
	public SeckillUser getUser() {
		return user;
	}
	public void setUser(SeckillUser user) {
		this.user = user;
	}
}