package com.itheima.ds.service.impl;

import com.itheima.ds.model.entity.VoucherOrder;
import com.itheima.ds.service.IVoucherOrderService;
import com.itheima.ds.dao.VoucherOrderDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 优惠券订单服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VoucherOrderServiceImpl implements IVoucherOrderService {

    private final VoucherOrderDao voucherOrderDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createVoucherOrder(VoucherOrder voucherOrder) {
        try {
            return save(voucherOrder);
        } catch (Exception e) {
            log.error("创建优惠券订单失败, voucherOrder: {}", voucherOrder, e);
            return false;
        }
    }

    @Override
    public VoucherOrder findOrderByUserIdAndVoucherId(Long userId, Long voucherId) {
        return voucherOrderDao.findByUserIdAndVoucherId(userId, voucherId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(VoucherOrder voucherOrder) {
        try {
            int affected = voucherOrderDao.save(voucherOrder);
            return affected > 0;
        } catch (Exception e) {
            log.error("保存优惠券订单失败, voucherOrder: {}", voucherOrder, e);
            return false;
        }
    }

    @Override
    public VoucherOrder getById(Long id) {
        return voucherOrderDao.findById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(VoucherOrder voucherOrder) {
        try {
            int affected = voucherOrderDao.update(voucherOrder);
            return affected > 0;
        } catch (Exception e) {
            log.error("更新优惠券订单失败, voucherOrder: {}", voucherOrder, e);
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Long id) {
        try {
            int affected = voucherOrderDao.removeById(id);
            return affected > 0;
        } catch (Exception e) {
            log.error("删除优惠券订单失败, id: {}", id, e);
            return false;
        }
    }

    @Override
    public List<VoucherOrder> getUserOrders(Long userId) {
        return voucherOrderDao.findByUserId(userId);
    }
} 