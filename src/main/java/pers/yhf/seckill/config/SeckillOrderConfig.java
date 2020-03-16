package pers.yhf.seckill.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import pers.yhf.seckill.domain.SeckillGoods;
import pers.yhf.seckill.domain.SeckillOrder;
import pers.yhf.seckill.redisCluster.RedisService;
import pers.yhf.seckill.redisCluster.SecKillActivityKey;
import pers.yhf.seckill.service.OrderService;
import pers.yhf.seckill.service.SeckillService;

public class SeckillOrderConfig {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private SeckillService seckillService;
	
	@Autowired
	private RedisService redisService;
	
	    
	 
   //@Scheduled(fixedRate = 30000)
   @Transactional
   public void cancelOrder(){
		
	   System.out.println("正在扫描失效订单信息...."); 
		
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
		     }//if
		     
		 }//for
		  
		
   }//

}
