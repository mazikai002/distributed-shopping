package com.itheima.ds.model.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SeckillOrder {
	/**
	 *  限制：一个用户只能秒杀一个商品，  解决方法：在表中加一个唯一索引即可
	          这样的话 第一个请求可以插入到数据库中，第二个请求由于userId重复了，
	           会在创建订单那报错，直接回滚.
	           
	    当然这样的问题只是理论上会出现，实际在做秒杀时 提交表单之前会要求输入验证码，不会让用户同时发出两个请求
	    但为了防止出现理论上的这种问题，我们还是要在 miaosha_order表上上建一个唯一索引
	 */
	
    private Long id;

    private Long userId;

    private String orderId;

    private Long goodsId;
    
    private String goodsName;
    
    private Double goodsPrice;
    
    private LocalDateTime createTime;
    
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
    
    public String getGoodsName() {
        return goodsName;
    }
    
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
    
    public Double getGoodsPrice() {
        return goodsPrice;
    }
    
    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }
    
    public LocalDateTime getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
}