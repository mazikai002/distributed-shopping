package pers.yhf.seckill.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.yhf.seckill.domain.SeckillGoods;
import pers.yhf.seckill.result.Result;
import pers.yhf.seckill.service.GoodsService;
import pers.yhf.seckill.service.SeckillUserService;
import pers.yhf.seckill.service.UserService; 

@Controller
@RequestMapping("/test")
public class TestController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private SeckillUserService seckillUserService;
	
	@Autowired
	private GoodsService goodsService;
	
	
	@RequestMapping("/db/tx2")
 	@ResponseBody
 	public  Result<Boolean> dbTx(){
 		   this.userService.txDecrease();
 	    return Result.success(true);
 	}
	
	
	@RequestMapping("/sec/tx")
 	@ResponseBody
 	public  Result<Boolean> getSeckillGoods(){
 		   List<SeckillGoods> list = this.seckillUserService.getSeckillGoodsVoList();
 		   
 		  SeckillGoods sg = this.goodsService.getSeckillGoodsByGoodsId(2);
 		       System.out.println("测试时间： "+list+"    "+sg);
 	    return Result.success(true);
 	}
}
