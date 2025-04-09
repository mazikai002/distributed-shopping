package com.itheima.ds.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rabbitmq.client.AMQP; 
 
import com.itheima.ds.config.RabbitMQComponentConfig;
import com.itheima.ds.domain.SeckillGoods;
import com.itheima.ds.domain.SeckillOrder;
import com.itheima.ds.domain.SeckillUser;
import com.itheima.ds.rabbitmq.MQSender; 
import com.itheima.ds.rabbitmq.SeckillMessage;
import com.itheima.ds.redisCluster.RedisService;
import com.itheima.ds.redisCluster.SecKillActivityKey;
import com.itheima.ds.result.CodeMsg;
import com.itheima.ds.result.Result;
import com.itheima.ds.service.AccessLimitService;
import com.itheima.ds.service.GoodsService;
import com.itheima.ds.service.SeckillService; 
import com.itheima.ds.service.OrderService;
import com.itheima.ds.vo.GoodsVo;
 

@RestController
@RequestMapping("/seckill")
public class SeckillController implements InitializingBean{

	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private SeckillService seckillService;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private AccessLimitService accessLimitService;
	
	@Autowired
	private MQSender sender;
	
	private Map<Long,Boolean> isSecKillOverMap = new HashMap<Long,Boolean>();
	
	/**
	 * 系统初始化
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		
		System.out.println("开始...");
		
		//List<GoodsVo> goodsList = this.goodsService.getGoodsVoList();
		
		List<SeckillGoods> goodsList = this.seckillService.getSeckillGoodsVoList();
		 
		 
		 if(goodsList==null) return;
		//1 将商品数量加载到缓存中
		 //for(GoodsVo goods:goodsList){
	      for(SeckillGoods goods:goodsList){	 
			 System.out.println("存入redis: "+SecKillActivityKey.getSecKillGoodsStock+""+goods.getId() +" : "+goods.getStockCount()); 
			 
			 redisService.set(SecKillActivityKey.getSecKillGoodsStock, ""+goods.getId(), goods.getStockCount());
			 isSecKillOverMap.put(goods.getId(), false);
		 }
		
	}
	
	
	
	
	@RequestMapping(value="/{path}/doSeckill",method = RequestMethod.POST)
	@ResponseBody
    public Result<Integer>  doSeckill(Model model,SeckillUser user,
    		@RequestParam("goodsId")long goodsId,@PathVariable("path")String path) throws IOException, TimeoutException {
	    model.addAttribute("user",user); 
	    
	     if(user == null){ 
	        return Result.error(CodeMsg.SESSION_ERROR);   
	     }
	     
	     //验证path
	     boolean isPathValid = seckillService.checkPath(user,goodsId,path);
	     if(!isPathValid){
	       return Result.error(CodeMsg.REQUEST_ILLEGAL);
	     }
	      
	     //此处内存标记，目的在于减少对于Redis的访问
	     boolean over = isSecKillOverMap.get(goodsId);
	     if(over){  
	    	return Result.error(CodeMsg.SECKILL_OVER);
	     }
	     
	     //假如有10个商品，某用户同时发出两个请求 req1, req2
	     //req1判断库存 可以购买，req2同样OK
	     //两个请求再同时判断是否秒杀到了，显然两个请求都没有秒杀到
	     //结果创建了两个订单，也就是说一个用户秒杀了两件商品
	      
	     
	     //2 预减库存
	     long stock = redisService.decr(SecKillActivityKey.getSecKillGoodsStock, ""+goodsId);
	        System.out.println("预减库存：     id="+goodsId+"  stock="+stock); 
	       if(stock<0){
	    	   isSecKillOverMap.put(goodsId, true);  
	    	  return Result.error(CodeMsg.SECKILL_OVER);
	       }
	       
	     //3判断是否秒杀到了
	       SeckillOrder order = orderService.getSeckillOrderByUserIdAndGoodsId(user.getId(), goodsId);
	      if(order!=null){  
	    	return Result.error(CodeMsg.REPEAT_SECKILL);
	      }
	     
	     //4 ------------------------------------------入-----队----------------------------------------------- 
	     
	      
	      // 设置消息持久化
	      AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties().builder();
	      properties.deliveryMode(RabbitMQComponentConfig.DELIVERMODE);
	      //channel.basicPublish(MQConfig.EXCHANGE_NAME, "", properties.build(),"".getBytes("UTF-8"));
	      
	      
	      SeckillMessage mm = new SeckillMessage();
	      mm.setUser(user);
	      mm.setGoodsId(goodsId); 
	      
	      //String msg = user.getId()+"-"+goodsId;
	     // sender.sendMiaoshaMessage(msg);
	      
	      sender.sendSeckillMessage(mm);
	      
	      
	     /* if (channel != null) {
              channel.close();
          }
          if (connection != null) {
              connection.close();
          }*/
	    
	    return Result.success(0); //排队中
	 }
	
	
	
	/**
	 * 
	 * 秒杀成功，返回orderId
	 * 秒杀失败，返回-1
	 * 排队中， 0
	 */
	@RequestMapping(value="/result",method = RequestMethod.GET)
	@ResponseBody
    public Result<String> seckillResult(Model model,SeckillUser user,
    		@RequestParam("goodsId")long goodsId) {
	    model.addAttribute("user",user);
	     if(user == null)return Result.error(CodeMsg.SESSION_ERROR);
	     String result = seckillService.getSeckillResult(user.getId(),goodsId);
	     return Result.success(result);
	}
	
	
	
	/**
	 * 用户点击 立即秒杀 后，会调用该逻辑
	 * @param request
	 * @param user
	 * @param goodsId
	 * @param verifyCode
	 * @return
	 */
	@RequestMapping(value="/path",method = RequestMethod.GET)
	@ResponseBody
    public Result<String> getSecKillPath(HttpServletRequest request,SeckillUser user,
    		@RequestParam("goodsId")long goodsId,
    		@RequestParam("verifyCode")int verifyCode) { 
	    
	     if(user == null){ 
	    	  return Result.error(CodeMsg.SESSION_ERROR);
	     }
	     
	     
	      //查询访问次数
	     String uri = request.getRequestURI();
	     String key = uri+"_"+user.getId();
	      
	     
	    //若没有被限流器限制，则执行秒杀处理
	    if(accessLimitService.tryAcquirelToken()){
	       Integer count = redisService.get(SecKillActivityKey.access, key, Integer.class);
		   if(count == null) redisService.set(SecKillActivityKey.access, key, 1);  
		   else{
			  redisService.incr(SecKillActivityKey.access, key);  
		   }
		}
	    else{
	    	return Result.error(CodeMsg.ACCESS_LIMIT);
	    }
	     
	     
	      //访问太频繁的话，直接返回错误信息，以下任务都不执行
	     boolean check = seckillService.checkVerifyCode(user, goodsId, verifyCode);
	     if(!check){
	    	 //未通过验证
	    	 return Result.error(CodeMsg.REQUEST_ILLEGAL);  //非法请求 
	     }
	     
	     //获取秒杀接口URL
	     String path = seckillService.createSecKillPath(user,goodsId);
	      // System.out.println("秒杀接口URL： "+path);
	      
	     return Result.success(path); 
	}
	
	
	
	 
    //@Scheduled(fixedRate = 30000)
    //@Transactional
    public void cancelOrder(){
		  
		 System.out.println("正在扫描需要取消的订单信息...."); 
		 
		 //从订单表中查找
		 List<SeckillOrder> seckillOrderlist = this.orderService.getAllSeckillOrders();
		 SeckillOrder order = null;
		 SeckillGoods goods = null;
		 int stockCount = 0;
		 for(SeckillOrder seckillOrder:seckillOrderlist){
			 order = this.redisService.get(SecKillActivityKey.getSecKillOrderByUidGid, ""+seckillOrder.getUserId()+"_"+seckillOrder.getGoodsId(), SeckillOrder.class);
		     if(order==null){
		      //取消订单
		    	  System.out.println("失效订单信息：    goodsId:"+seckillOrder.getGoodsId() + "  userId:"+seckillOrder.getUserId());
		    	 
		    	 //删除缓存中秒杀商品
		 	    this.redisService.delete(SecKillActivityKey.getSecKillOrderByUidGid, ""+seckillOrder.getOrderId()+"_"+seckillOrder.getGoodsId()); 
		 	      
		 	     //订单取消
		 	    this.orderService.deleteOrder(seckillOrder.getGoodsId(), seckillOrder.getUserId()); 
		 	     
		 	     //库存还原
		 	     /**
		 	      * 此处需要考虑一个痛点：
		          *   
		          *   当获取某个秒杀商品库存10，
		          *      同时
		          *         A读取库存数为10，成功下订单库存数为9，
		          *         B读取库存数为10，成功下订单库存数为9
		          *   
		          *   此时实际库存应是8，而读到的库存是9
		          * https://www.cnblogs.com/lichihua/p/10803305.html
		 	      */
		 	    goods = new SeckillGoods();  //有错
		 	      stockCount = this.seckillService.getCurrentStockCountByGoodId(seckillOrder.getGoodsId()); 
		 	      goods.setStockCount(stockCount+1);
		 	      goods.setGoodsId(seckillOrder.getGoodsId()); 
	 	        this.seckillService.backStockCount(goods);
		 		
		 		goods = null;
		 		stockCount = 0;
		     }
		 }
		  
	}
	 
	
	
	
	//----------------------------------------------------------------------------------//
	
	//生成图片验证码
	@RequestMapping(value="/verifyCode",method = RequestMethod.GET)
	@ResponseBody
    public Result<String> generateVerifyCode(HttpServletResponse response,SeckillUser user,
    		@RequestParam("goodsId")long goodsId) { 
	    
	     if(user == null){ 
	    	  return Result.error(CodeMsg.SESSION_ERROR);
	     }
	      
	     try {
	    		BufferedImage image  = seckillService.createSecKillverifyCode(user, goodsId);
	    		OutputStream out = response.getOutputStream();
	    		ImageIO.write(image, "JPEG", out);
	    		out.flush();
	    		out.close();
	    		return null;
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    		return Result.error(CodeMsg.MIAOSHA_FAIL);
	    	} 
	}
	

	
	
}
