package com.itheima.ds.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import com.itheima.ds.model.entity.SeckillOrder;
import com.itheima.ds.model.entity.SeckillUser;
import com.itheima.ds.model.entity.OrderInfo;
import com.itheima.ds.mapper.OrderMapper;
import com.itheima.ds.redisCluster.RedisService;
import com.itheima.ds.redisCluster.SecKillActivityKey;
import com.itheima.ds.common.utils.TimeUtil;
import com.itheima.ds.model.vo.GoodsVo;

/**
 * 订单服务接口
 */
public interface OrderService {
    
    /**
     * 获取订单详情
     * @param orderId 订单ID
     * @return 订单详情
     */
    SeckillOrder getOrderById(Long orderId);
    
    /**
     * 创建订单
     * @param order 订单信息
     * @return 订单ID
     */
    Long createOrder(SeckillOrder order);
    
    /**
     * 使用Redis创建订单，提高性能
     * @param order 订单信息
     * @return 订单ID
     */
    Long createOrderWithRedis(SeckillOrder order);
    
    /**
     * 获取用户的订单列表
     * @param userId 用户ID
     * @return 订单列表
     */
    List<SeckillOrder> getUserOrders(Long userId);
    
    /**
     * 通过用户ID和商品ID获取秒杀订单
     * @param userId 用户ID
     * @param goodsId 商品ID
     * @return 秒杀订单
     */
    SeckillOrder getSeckillOrderByUserIdGoodsId(Long userId, Long goodsId);
}

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private RedisService redisService;
	
	public SeckillOrder getSeckillOrderByUserIdAndGoodsId(Long userId, long goodsId) {
	    // return this.orderDao.getMiaoshaOrderByUserIdAndGoodsId(userId,goodsId);
		return redisService.get(SecKillActivityKey.getSecKillOrderByUidGid, ""+userId+"_"+goodsId, SeckillOrder.class);
	}
 
	
	
	@Transactional
	public OrderInfo createOrder(SeckillUser user, GoodsVo goods) { 
		
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
		redisService.set(SecKillActivityKey.getSecKillOrderByUidGid, ""+user.getId()+"_"+goods.getId(), seckillOrder);
		
		return orderInfo;
	}
	
	
	
	
 
	public OrderInfo getOrderById(long orderId) { 
		 return orderMapper.getOrderById(orderId);
	}

	
	public void deleteOrder(long goodsId, long userId) {
		this.orderMapper.deleteSeckillOrderByGoodIdAndUserId(goodsId,userId);
	}

	
	public List<SeckillOrder> getAllSeckillOrders() { 
	     return this.orderMapper.getAllSeckillOrders();
	}

	 
	
	
}
