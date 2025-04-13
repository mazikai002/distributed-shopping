package com.itheima.ds.dao.mapper;

import org.apache.ibatis.annotations.*;
import com.itheima.ds.dao.SeckillGoodsDao;
import com.itheima.ds.model.entity.SeckillGoods;

/**
 * 秒杀商品Mapper接口，实现SeckillGoodsDao接口
 */
@Mapper
public interface SeckillGoodsMapper extends SeckillGoodsDao {

    /**
     * 获取秒杀商品版本号，用于乐观锁控制
     * @param goodsId 商品ID
     * @return 版本号
     */
    @Select("SELECT version FROM seckill_goods WHERE goods_id = #{goodsId}")
    long getSeckillGoodsVersion(@Param("goodsId") Long goodsId);
    
    /**
     * 减少库存，使用乐观锁防止超卖
     * @param goodsId 商品ID
     * @param version 版本号
     * @return 影响的行数
     */
    @Update("UPDATE seckill_goods SET stock_count = stock_count - 1, version = version + 1 WHERE goods_id = #{goodsId} AND version = #{version} AND stock_count > 0")
    int reduceStockByVersion(@Param("goodsId") Long goodsId, @Param("version") long version);
} 