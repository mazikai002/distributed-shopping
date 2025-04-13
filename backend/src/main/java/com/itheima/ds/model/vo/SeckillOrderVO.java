package com.itheima.ds.model.vo;

import java.io.Serializable;

/**
 * 秒杀订单VO
 */
public class SeckillOrderVO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private long id;
    private long userId;
    private long goodsId;
    private String goodsName;
    private int status;
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public long getGoodsId() {
        return goodsId;
    }
    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }
    public String getGoodsName() {
        return goodsName;
    }
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
}