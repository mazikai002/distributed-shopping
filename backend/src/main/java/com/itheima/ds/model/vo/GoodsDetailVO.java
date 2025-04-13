package com.itheima.ds.model.vo;

import com.itheima.ds.model.entity.SeckillUser;

/**
 * 商品详情VO
 */
public class GoodsDetailVO {
    
    private int remainSeconds;
    private int seckillStatus;
    private SeckillUser user;
    private GoodsVO goods;
    
    public int getRemainSeconds() {
        return remainSeconds;
    }
    
    public void setRemainSeconds(int remainSeconds) {
        this.remainSeconds = remainSeconds;
    }
    
    public int getSeckillStatus() {
        return seckillStatus;
    }
    
    public void setSeckillStatus(int seckillStatus) {
        this.seckillStatus = seckillStatus;
    }
    
    public SeckillUser getUser() {
        return user;
    }
    
    public void setUser(SeckillUser user) {
        this.user = user;
    }
    
    public GoodsVO getGoods() {
        return goods;
    }
    
    public void setGoods(GoodsVO goods) {
        this.goods = goods;
    }
}