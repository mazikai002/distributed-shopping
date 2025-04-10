package com.itheima.ds.dao.mapper;

import com.itheima.ds.dao.SeckillGoodsDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.itheima.ds.model.entity.SeckillGoods;
import java.util.List;

/**
 * 秒杀商品Mapper接口，实现SeckillGoodsDao接口
 */
@Mapper
public interface SeckillGoodsMapper implements SeckillGoodsDao {
    
    @Override
    @Select("SELECT * FROM seckill_goods")
    List<SeckillGoods> getSeckillGoodsList();
    
    @Override
    @Select("SELECT * FROM seckill_goods WHERE id = #{goodsId}")
    SeckillGoods getSeckillGoodsByGoodsId(@Param("goodsId") long goodsId);
    
    @Override
    @Select("SELECT g.*, sg.stock_count, sg.start_date, sg.end_date, sg.seckill_price, sg.version " +
            "FROM seckill_goods sg LEFT JOIN goods g ON sg.goods_id = g.id")
    List<SeckillGoods> getSeckillGoodsVoList();
}
