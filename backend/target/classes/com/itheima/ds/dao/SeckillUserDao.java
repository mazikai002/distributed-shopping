package com.itheima.ds.dao;

import com.itheima.ds.model.entity.SeckillUser;
import com.itheima.ds.model.entity.SeckillGoods;

/**
 * 秒杀用户数据访问接口
 */
public interface SeckillUserDao {
    /**
     * 根据ID获取用户
     */
    SeckillUser getUserById(long id);
    
    /**
     * 更新用户信息
     */
    void update(SeckillUser user);
    
    /**
     * 更新用户登录信息
     */
    void updateSeckillUserLoginInfo(String nickname);
    
    /**
     * 根据昵称获取用户
     */
    SeckillUser getSeckillUserByNickName(String nickname);
    
    /**
     * 获取商品库存
     */
    int getSeckillGoodsStockCountByGoodId(long goodsId);
    
    /**
     * 恢复库存
     */
    void backStockCount(SeckillGoods goods);
} 