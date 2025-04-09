package com.itheima.ds.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itheima.ds.domain.SeckillUser;
import com.itheima.ds.domain.OrderInfo;
import com.itheima.ds.redisCluster.RedisService;
import com.itheima.ds.result.CodeMsg;
import com.itheima.ds.result.Result;
import com.itheima.ds.service.GoodsService;
import com.itheima.ds.service.SeckillUserService;
import com.itheima.ds.service.OrderService;
import com.itheima.ds.vo.GoodsVo;
import com.itheima.ds.vo.OrderDetailVo;
 

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	SeckillUserService userService;
	
	@Autowired
	RedisService redisService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	GoodsService goodsService;
	
    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> info(Model model,SeckillUser user,
    		@RequestParam("orderId") long orderId) {
    	if(user == null) {
    		return Result.error(CodeMsg.SESSION_ERROR);
    	}
    	OrderInfo order = orderService.getOrderById(orderId);
    	if(order == null) {
    		return Result.error(CodeMsg.ORDER_NOT_EXIST);
    	}
    	long goodsId = order.getGoodsId();
    	GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
    	OrderDetailVo vo = new OrderDetailVo();
    	vo.setOrder(order);
    	vo.setGoods(goods);
    	return Result.success(vo);
    }
    
}
