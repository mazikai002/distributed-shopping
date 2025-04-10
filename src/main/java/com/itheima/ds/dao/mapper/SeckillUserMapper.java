package com.itheima.ds.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;

import com.itheima.ds.dao.SeckillUserDao;
import com.itheima.ds.model.entity.SeckillUser;
import com.itheima.ds.model.entity.SeckillGoods;

/**
 * 秒杀用户Mapper接口，实现SeckillUserDao接口
 */
@Mapper
public interface SeckillUserMapper implements SeckillUserDao {

    @Override
    @Select("SELECT * FROM seckill_user WHERE id = #{id}")
    SeckillUser getUserById(@Param("id") long id);

    @Override
    @Update("UPDATE seckill_user SET password = #{password}, salt = #{salt}, register_date = #{registerDate}, last_login_date = #{lastLoginDate} WHERE id = #{id}")
    void update(SeckillUser user);

    @Override
    @Update("UPDATE seckill_user SET last_login_date = NOW() WHERE nickname = #{nickname}")
    void updateSeckillUserLoginInfo(@Param("nickname") String nickname);

    @Override
    @Select("SELECT * FROM seckill_user WHERE nickname = #{nickname}")
    SeckillUser getSeckillUserByNickName(@Param("nickname") String nickname);

    @Override
    @Select("SELECT stock_count FROM seckill_goods WHERE id = #{goodsId}")
    int getSeckillGoodsStockCountByGoodId(@Param("goodsId") long goodsId);

    @Override
    @Update("UPDATE seckill_goods SET stock_count = #{stockCount} WHERE id = #{goodsId}")
    void backStockCount(SeckillGoods goods);

    @Select("SELECT * FROM seckill_goods")
    public List<SeckillGoods> getSeckillGoodsList();

    @Select("SELECT * FROM seckill_goods WHERE id = #{goodsId}")
    public SeckillGoods getSeckillGoodsByGoodsId(@Param("goodsId")long goodsId);

    @Select("SELECT g.*, sg.stock_count, sg.start_date, sg.end_date, sg.seckill_price, sg.version FROM seckill_goods sg LEFT JOIN goods g ON sg.goods_id = g.id")
    public List<SeckillGoods> getSeckillGoodsVoList();        

}
