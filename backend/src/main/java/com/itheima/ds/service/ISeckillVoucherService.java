package com.itheima.ds.service;

import com.itheima.ds.model.entity.SeckillVoucher;
import java.util.List;

/**
 * 秒杀优惠券服务接口
 */
public interface ISeckillVoucherService {
    
    /**
     * 查询所有秒杀优惠券
     * @return 优惠券列表
     */
    List<SeckillVoucher> list();
    
    /**
     * 根据ID查询优惠券
     * @param voucherId 优惠券ID
     * @return 优惠券信息
     */
    SeckillVoucher getById(Long voucherId);
    
    /**
     * 更新优惠券信息
     * @param seckillVoucher 优惠券信息
     * @return 是否更新成功
     */
    boolean update(SeckillVoucher seckillVoucher);
    
    /**
     * 查询优惠券库存
     * @param voucherId 优惠券ID
     * @return 库存数量
     */
    Integer getVoucherStock(Long voucherId);
    
    /**
     * 扣减优惠券库存
     * @param voucherId 优惠券ID
     * @return 是否扣减成功
     */
    boolean deductVoucherStock(Long voucherId);
    
    /**
     * 增加优惠券库存
     * @param voucherId 优惠券ID
     * @param amount 增加数量
     * @return 是否增加成功
     */
    boolean addVoucherStock(Long voucherId, Integer amount);
    
    /**
     * 删除优惠券
     * @param voucherId 优惠券ID
     * @return 是否删除成功
     */
    boolean removeById(Long voucherId);
} 