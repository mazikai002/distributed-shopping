package com.itheima.ds.dao;

import com.itheima.ds.model.entity.SeckillVoucher;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 秒杀优惠券数据访问层接口
 */
@Mapper
public interface SeckillVoucherDao {
    
    /**
     * 查询所有秒杀优惠券
     * @return 优惠券列表
     */
    @Select("SELECT * FROM tb_seckill_voucher")
    List<SeckillVoucher> list();
    
    /**
     * 根据ID查询优惠券
     * @param voucherId 优惠券ID
     * @return 优惠券信息
     */
    @Select("SELECT * FROM tb_seckill_voucher WHERE voucher_id = #{voucherId}")
    SeckillVoucher findById(@Param("voucherId") Long voucherId);
    
    /**
     * 更新优惠券信息
     * @param seckillVoucher 优惠券信息
     * @return 影响的行数
     */
    @Update("UPDATE tb_seckill_voucher SET title = #{title}, begin_time = #{beginTime}, end_time = #{endTime}, stock = #{stock} WHERE voucher_id = #{voucherId}")
    int update(SeckillVoucher seckillVoucher);
    
    /**
     * 扣减库存
     * @param voucherId 优惠券ID
     * @return 影响的行数
     */
    @Update("UPDATE tb_seckill_voucher SET stock = stock - 1 WHERE voucher_id = #{voucherId} AND stock > 0")
    int deductStock(@Param("voucherId") Long voucherId);
    
    /**
     * 增加库存
     * @param voucherId 优惠券ID
     * @param amount 增加数量
     * @return 影响的行数
     */
    @Update("UPDATE tb_seckill_voucher SET stock = stock + #{amount} WHERE voucher_id = #{voucherId}")
    int addStock(@Param("voucherId") Long voucherId, @Param("amount") Integer amount);
    
    /**
     * 删除优惠券
     * @param voucherId 优惠券ID
     * @return 影响的行数
     */
    @Delete("DELETE FROM tb_seckill_voucher WHERE voucher_id = #{voucherId}")
    int removeById(@Param("voucherId") Long voucherId);
} 