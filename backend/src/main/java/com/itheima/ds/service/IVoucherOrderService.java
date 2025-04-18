package com.itheima.ds.service;

import com.itheima.ds.model.entity.VoucherOrder;
import java.util.List;

/**
 * 优惠券订单服务接口
 */
public interface IVoucherOrderService {
    
    /**
     * 创建优惠券订单
     * @param voucherOrder 优惠券订单信息
     * @return 是否成功
     */
    boolean createVoucherOrder(VoucherOrder voucherOrder);
    
    /**
     * 查询用户的优惠券订单
     * @param userId 用户ID
     * @param voucherId 优惠券ID
     * @return 优惠券订单
     */
    VoucherOrder findOrderByUserIdAndVoucherId(Long userId, Long voucherId);
    
    /**
     * 保存优惠券订单
     * @param voucherOrder 优惠券订单信息
     * @return 是否成功
     */
    boolean save(VoucherOrder voucherOrder);
    
    /**
     * 根据ID获取优惠券订单
     * @param id 订单ID
     * @return 优惠券订单
     */
    VoucherOrder getById(Long id);
    
    /**
     * 更新优惠券订单
     * @param voucherOrder 优惠券订单信息
     * @return 是否成功
     */
    boolean update(VoucherOrder voucherOrder);
    
    /**
     * 删除优惠券订单
     * @param id 订单ID
     * @return 是否成功
     */
    boolean removeById(Long id);
    
    /**
     * 获取用户的所有优惠券订单
     * @param userId 用户ID
     * @return 优惠券订单列表
     */
    List<VoucherOrder> getUserOrders(Long userId);
} 