package pers.yhf.seckill.mapper;

import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;

import pers.yhf.seckill.domain.SeckillGoods;
import pers.yhf.seckill.domain.SeckillUser; 
 
@Mapper
public interface SeckillUserMapper {

    @Select("SELECT * FROM seckill_user WHERE id = #{id}")
    public SeckillUser getUserById(@Param("id")long id);
 
    @Update("UPDATE seckill_user SET password = #{password}, salt = #{salt}, register_date = #{registerDate}, last_login_date = #{lastLoginDate} WHERE id = #{id}")
    public void update(SeckillUser toBeUpdate);

    @Update("UPDATE seckill_user SET last_login_date = NOW() WHERE nickname = #{nickname}")
    public void updateSeckillUserLoginInfo(@Param("nickname")String nickname);
 
    @Select("SELECT * FROM seckill_user WHERE nickname = #{nickname}")
    public SeckillUser getSeckillUserByNickName(@Param("nickname")String nickname);

    @Select("SELECT stock_count FROM seckill_goods WHERE id = #{goodsId}")
    public int getSeckillGoodsStockCountByGoodId(@Param("goodsId")long goodsId);
    
    @Update("UPDATE seckill_goods SET stock_count = #{stockCount} WHERE id = #{goodsId}")
    public void backStockCount(SeckillGoods goods);

    @Select("SELECT * FROM seckill_goods")
    public List<SeckillGoods> getSeckillGoodsList();

    @Select("SELECT * FROM seckill_goods WHERE id = #{goodsId}")
    public SeckillGoods getSeckillGoodsByGoodsId(@Param("goodsId")long goodsId);

    @Select("SELECT g.*, sg.stock_count, sg.start_date, sg.end_date, sg.seckill_price, sg.version FROM seckill_goods sg LEFT JOIN goods g ON sg.goods_id = g.id")
    public List<SeckillGoods> getSeckillGoodsVoList();        

}
