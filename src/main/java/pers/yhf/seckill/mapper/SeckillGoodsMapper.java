package pers.yhf.seckill.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SeckillGoodsMapper {

    @Select("SELECT version FROM seckill_goods WHERE id = #{goodsId}")
    public long getSeckillGoodsVersion(@Param("goodsId")long goodsId); 
 
}
