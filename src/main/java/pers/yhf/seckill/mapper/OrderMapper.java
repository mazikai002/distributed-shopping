package pers.yhf.seckill.mapper;

import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;

import pers.yhf.seckill.domain.SeckillOrder;
import pers.yhf.seckill.domain.OrderInfo; 
 
@Mapper
public interface OrderMapper {
 
    @Select("SELECT * FROM seckill_order WHERE user_id = #{userId} AND goods_id = #{goodsId}")
    public SeckillOrder getSeckillOrderByUserIdAndGoodsId(@Param("userId")Long userId, @Param("goodsId")long goodsId);
 
    @Insert("INSERT INTO order_info(order_id, user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date, delivery_addr_id) " +
           "VALUES(#{orderId}, #{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel}, #{status}, #{createDate}, #{deliveryAddrId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public long insertOrderInfo(OrderInfo orderInfo);
 
    @Insert("INSERT INTO seckill_order (user_id, order_id, goods_id) VALUES(#{userId}, #{orderId}, #{goodsId})")
    public void insertSeckillOrder(SeckillOrder seckillOrder);
 
    @Select("SELECT * FROM order_info WHERE order_id = #{orderId}")
    public OrderInfo getOrderById(@Param("orderId")long orderId);
 
    @Delete("DELETE FROM seckill_order WHERE goods_id = #{goodsId} AND user_id = #{userId}")
    public void deleteSeckillOrderByGoodIdAndUserId(@Param("goodsId")long goodsId, @Param("userId")long userId);
 
    @Select("SELECT * FROM seckill_order")
    public List<SeckillOrder> getAllSeckillOrders();  
 
}
