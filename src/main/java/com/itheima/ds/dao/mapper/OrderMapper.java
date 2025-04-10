package com.itheima.ds.dao.mapper;

import com.itheima.ds.dao.OrderDao;
import org.apache.ibatis.annotations.*;
import com.itheima.ds.model.entity.OrderInfo;
import com.itheima.ds.model.entity.SeckillOrder;
import java.util.List;

/**
 * 订单Mapper接口，实现OrderDao接口
 */
@Mapper
public interface OrderMapper implements OrderDao {
    
    @Override
    @Select("SELECT * FROM seckill_order WHERE user_id = #{userId} AND goods_id = #{goodsId}")
    SeckillOrder getSeckillOrderByUserIdAndGoodsId(@Param("userId") Long userId, @Param("goodsId") long goodsId);
    
    @Override
    @Insert("INSERT INTO order_info(order_id, user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date, delivery_addr_id) " +
           "VALUES(#{orderId}, #{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel}, #{status}, #{createDate}, #{deliveryAddrId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    long insertOrderInfo(OrderInfo orderInfo);
    
    @Override
    @Insert("INSERT INTO seckill_order (user_id, order_id, goods_id) VALUES(#{userId}, #{orderId}, #{goodsId})")
    void insertSeckillOrder(SeckillOrder seckillOrder);
    
    @Override
    @Select("SELECT * FROM order_info WHERE order_id = #{orderId}")
    OrderInfo getOrderById(@Param("orderId") long orderId);
    
    @Override
    @Delete("DELETE FROM seckill_order WHERE goods_id = #{goodsId} AND user_id = #{userId}")
    void deleteSeckillOrderByGoodIdAndUserId(@Param("goodsId") long goodsId, @Param("userId") long userId);
    
    @Override
    @Select("SELECT * FROM seckill_order")
    List<SeckillOrder> getAllSeckillOrders();
}
