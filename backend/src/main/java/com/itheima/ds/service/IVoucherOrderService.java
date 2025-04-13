package com.itheima.ds.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.ds.model.entity.VoucherOrder;

/**
 * 优惠券订单服务接口
 */
public interface IVoucherOrderService extends IService<VoucherOrder> {
    
    /**
     * 创建优惠券订单
     * @param voucherOrder 优惠券订单信息
     * @return 是否成功
     */
    default boolean createVoucherOrder(VoucherOrder voucherOrder) {
        return save(voucherOrder);
    }
    
    /**
     * 查询用户的优惠券订单
     * @param userId 用户ID
     * @param voucherId 优惠券ID
     * @return 优惠券订单
     */
    VoucherOrder findOrderByUserIdAndVoucherId(Long userId, Long voucherId);
} 