package com.itheima.ds.service;

import com.itheima.ds.model.entity.SeckillVoucher;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 秒杀优惠券服务接口
 */
public interface ISeckillVoucherService extends IService<SeckillVoucher> {
    
    /**
     * 根据ID获取秒杀优惠券
     * @param voucherId 优惠券ID
     * @return 秒杀优惠券
     */
    SeckillVoucher getById(Long voucherId);
    
    /**
     * 更新秒杀优惠券
     * @param seckillVoucher 秒杀优惠券
     * @return 更新是否成功
     */
    boolean update(SeckillVoucher seckillVoucher);
} 