package com.itheima.ds.dao;

import com.itheima.ds.model.entity.VoucherOrder;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 优惠券订单数据访问层接口
 */
@Mapper
public interface VoucherOrderDao {
    
    /**
     * 保存优惠券订单
     * @param voucherOrder 优惠券订单信息
     * @return 影响的行数
     */
    @Insert("INSERT INTO voucher_order (id, user_id, voucher_id, status, create_time, pay_time, use_time, refund_time) " +
            "VALUES (#{id}, #{userId}, #{voucherId}, #{status}, #{createTime}, #{payTime}, #{useTime}, #{refundTime})")
    int save(VoucherOrder voucherOrder);
    
    /**
     * 根据用户ID和优惠券ID查询订单
     * @param userId 用户ID
     * @param voucherId 优惠券ID
     * @return 优惠券订单
     */
    @Select("SELECT * FROM voucher_order WHERE user_id = #{userId} AND voucher_id = #{voucherId}")
    VoucherOrder findByUserIdAndVoucherId(@Param("userId") Long userId, @Param("voucherId") Long voucherId);
    
    /**
     * 根据ID查询优惠券订单
     * @param id 订单ID
     * @return 优惠券订单
     */
    @Select("SELECT * FROM voucher_order WHERE id = #{id}")
    VoucherOrder findById(Long id);
    
    /**
     * 更新优惠券订单
     * @param voucherOrder 优惠券订单信息
     * @return 影响的行数
     */
    @Update("UPDATE voucher_order SET status = #{status}, pay_time = #{payTime}, " +
            "use_time = #{useTime}, refund_time = #{refundTime} WHERE id = #{id}")
    int update(VoucherOrder voucherOrder);
    
    /**
     * 删除优惠券订单
     * @param id 订单ID
     * @return 影响的行数
     */
    @Delete("DELETE FROM voucher_order WHERE id = #{id}")
    int removeById(Long id);
    
    /**
     * 查询用户的所有优惠券订单
     * @param userId 用户ID
     * @return 优惠券订单列表
     */
    @Select("SELECT * FROM voucher_order WHERE user_id = #{userId} ORDER BY create_time DESC")
    List<VoucherOrder> findByUserId(Long userId);
} 