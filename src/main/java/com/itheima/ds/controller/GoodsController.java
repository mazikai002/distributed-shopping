package com.itheima.ds.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import com.itheima.ds.domain.SeckillUser;
import com.itheima.ds.redisCluster.RedisService;
import com.itheima.ds.redisCluster.SecKillActivityKey;
import com.itheima.ds.result.Result;
import com.itheima.ds.service.GoodsService; 
import com.itheima.ds.vo.GoodsDetailVo;
import com.itheima.ds.vo.GoodsVo;
 
@Controller
@RequestMapping("/goods") 
public class GoodsController {
	
	private static Logger log = LoggerFactory.getLogger(GoodsController.class);

	@Autowired
	private RedisService redisService;
	
	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	ThymeleafViewResolver thymeleafViewResolver;
	
	@Autowired
	ApplicationContext applicationContext;
	
	@RequestMapping(value="/to_list",produces="text/html")
	@ResponseBody
    public String toList(HttpServletRequest request,HttpServletResponse response,
    		Model model,SeckillUser user) {
	    model.addAttribute("user",user);
	    
	    //先从Redis处访问,取缓存
	     String html = redisService.get(SecKillActivityKey.getGoodsList, "", String.class);
	     
	     
	     if(!StringUtils.isEmpty(html)){
	    	  System.out.println("Redis有数据"); 
	    	 return html;
	     }
	    
	     
	      //查询商品列表
	      List<GoodsVo> goodsList = this.goodsService.getGoodsVoList();
	      
	      int len = goodsList.size();
	      for(int i=0;i<len;i++){
	    	  System.out.print(goodsList.get(i).getStockCount()+"  ");  
	      } 
	      
	         System.out.println();
	      model.addAttribute("goodsList", goodsList);
		//return "goods_list";
	      
	    
	      //手动渲染
	     
	      SpringWebContext ctx = new SpringWebContext(request,response,request.getServletContext(),
	    		                                     request.getLocale(),model.asMap(),applicationContext);
	    		                                     
	     
	    // WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
	     
	     
	     html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
	     
       if(!StringUtils.isEmpty(html)){
    	   //保存到缓存中
    	   redisService.set(SecKillActivityKey.getGoodsList, "",html);
       }
       return html;
	
	}
	
	
	@RequestMapping(value="/detail/{goodsId}")
	@ResponseBody
	public Result<GoodsDetailVo> detail(HttpServletRequest request,HttpServletResponse response,Model model,SeckillUser user,
    		@PathVariable("goodsId")long goodsId) {
		  GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
	      long startAt = goods.getStartDate().getTime();
	      long endAt = goods.getEndDate().getTime();
	      long now = System.currentTimeMillis();
	      int miaoshaStatus = 0;
          int remainSeconds = 0;  //还剩多少秒开始
	     
	     if(now<startAt){
	    	 //秒杀尚未开始，进入倒计时
	    	 miaoshaStatus = 0;
	    	 remainSeconds = (int) ((startAt-now)/1000);
	     }
	     else if(now > endAt){
	    	  //秒杀已经结束
	    	 miaoshaStatus = 2;
	    	 remainSeconds = -1;
	     }
	     else{
	    	 //秒杀正在进行中
	    	 miaoshaStatus = 1;
	    	 remainSeconds = 0;
	     }
	   
	     GoodsDetailVo vo = new GoodsDetailVo();
	     vo.setGoods(goods); 
	     vo.setUser(user);
	     vo.setMiaoshaStatus(miaoshaStatus);
	     vo.setRemainSeconds(remainSeconds); 
	    
       return Result.success(vo);   
    }
	
	
	@RequestMapping(value="/to_detail2/{goodsId}",produces="text/html")
	@ResponseBody
	public String toDetail2(HttpServletRequest request,HttpServletResponse response,Model model,SeckillUser user,
    		@PathVariable("goodsId")long goodsId) {
		  //snowflake 生成userId
		
	      model.addAttribute("user",user); 
	      
	      //先从Redis处访问,取缓存
		     String html = redisService.get(SecKillActivityKey.getGoodsDetail, "", String.class);    
		     if(!StringUtils.isEmpty(html)){
		    	  System.out.println("Redis有数据"); 
		    	 return html;
		     }
	      
		   //手动渲染
	    GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
	     model.addAttribute("goods",goods);
	     
	     //秒杀的开始与结束时间
	     long startAt = goods.getStartDate().getTime();
	     long endAt = goods.getEndDate().getTime();
	     
	     long now = System.currentTimeMillis();
	     int miaoshaStatus = 0;
         int remainSeconds = 0;  //还剩多少秒开始
	     
	     if(now<startAt){
	    	 //秒杀尚未开始，进入倒计时
	    	 miaoshaStatus = 0;
	    	 remainSeconds = (int) ((startAt-now)/1000);
	     }
	     else if(now > endAt){
	    	  //秒杀已经结束
	    	 miaoshaStatus = 2;
	    	 remainSeconds = -1;
	     }
	     else{
	    	 //秒杀正在进行中
	    	 miaoshaStatus = 1;
	    	 remainSeconds = 0;
	     }
	     
	     model.addAttribute("miaoshaStatus", miaoshaStatus);
	     model.addAttribute("remainSeconds", remainSeconds);
	       
		//return "goods_detail";
	     
	    SpringWebContext ctx = new SpringWebContext(request,response,request.getServletContext(),
	    		                                     request.getLocale(),model.asMap(),applicationContext);
	     
	    // WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap()); 
	     
	     html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);
       if(!StringUtils.isEmpty(html)){
    	   //保存到缓存中
    	   redisService.set(SecKillActivityKey.getGoodsDetail, ""+goodsId,html);
       }
       return html;  
    }
	
	
	
	 
}
