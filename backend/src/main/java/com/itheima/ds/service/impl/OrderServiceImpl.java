package com.itheima.ds.service.impl;

import java.util.Date;
import java.util.List;

import com.itheima.ds.component.cache.redis.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import com.itheima.ds.model.entity.SeckillOrder;
import com.itheima.ds.model.entity.SeckillUser;
import com.itheima.ds.model.entity.OrderInfo;
import com.itheima.ds.dao.mapper.OrderMapper;
import com.itheima.ds.component.cache.redis.SecKillActivityKey;
import com.itheima.ds.common.utils.TimeUtil;
import com.itheima.ds.model.vo.GoodsVO;
import com.itheima.ds.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private RedisClient redisClient;
	
	public SeckillOrder getSeckillOrderByUserIdAndGoodsId(Long userId, long goodsId) {
		return redisClient.get(SecKillActivityKey.getSecKillOrderByUidGid, ""+userId+"_"+goodsId, SeckillOrder.class);
	}
 
	@Transactional
	public OrderInfo createOrder(SeckillUser user, GoodsVO goods) {
		
		String orderId = TimeUtil.getRandomTimeId();
		
		OrderInfo orderInfo = new OrderInfo();
		
		orderInfo.setOrderId(orderId); 
		orderInfo.setCreateDate(new Date()); 
		orderInfo.setDeliveryAddrId(0L); 
		orderInfo.setGoodsCount(1);
		orderInfo.setGoodsId(goods.getId());
		orderInfo.setGoodsName(goods.getGoodsName()); 
		
		orderInfo.setGoodsPrice(goods.getSeckillPrice()); 
		orderInfo.setOrderChannel(1);
		orderInfo.setStatus(0);
		orderInfo.setUserId(user.getId()); 
		
		orderMapper.insertOrderInfo(orderInfo);
		
		SeckillOrder seckillOrder = new SeckillOrder();
		seckillOrder.setGoodsId(goods.getId());
		seckillOrder.setOrderId(orderInfo.getOrderId()); 
		seckillOrder.setUserId(user.getId());
		
		orderMapper.insertSeckillOrder(seckillOrder);
		
		//生成订单后，需要写入缓存中
		redisClient.set(SecKillActivityKey.getSecKillOrderByUidGid, ""+user.getId()+"_"+goods.getId(), seckillOrder);
		
		return orderInfo;
	}
	
	public OrderInfo getOrderById(long orderId) { 
		return orderMapper.getOrderById(orderId);
	}

	@Override
	public void deleteOrder(Long goodsId, Long userId) {
		this.orderMapper.deleteSeckillOrderByGoodIdAndUserId(goodsId, userId);
	}

	@Override
	public SeckillOrder getOrderById(Long orderId) {
		// Implementation needed
		return null;
	}

	@Override
	public Long createOrder(SeckillOrder order) {
		// Implementation needed
		return null;
	}

	@Override
	public Long createOrderWithRedis(SeckillOrder order) {
		// Implementation needed
		return null;
	}

	@Override
	public List<SeckillOrder> getUserOrders(Long userId) {
		// Implementation needed
		return null;
	}

	@Override
	public SeckillOrder getSeckillOrderByUserIdGoodsId(Long userId, Long goodsId) {
		return getSeckillOrderByUserIdAndGoodsId(userId, goodsId);
	}

	@Override
	public List<String> findOrdersByTime(String crateTime) {
		return orderMapper.findOrdersByTime(crateTime);
	}
	
	@Override
	public List<SeckillOrder> getAllSeckillOrders() {
		// 查询所有秒杀订单
		return orderMapper.listAllSeckillOrders();
	}
} 