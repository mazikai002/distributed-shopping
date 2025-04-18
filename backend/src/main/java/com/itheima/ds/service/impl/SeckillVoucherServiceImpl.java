package com.itheima.ds.service.impl;

import com.itheima.ds.service.ISeckillVoucherService;
import com.itheima.ds.dao.SeckillVoucherDao;
import com.itheima.ds.model.entity.SeckillVoucher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 秒杀优惠券服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SeckillVoucherServiceImpl implements ISeckillVoucherService {

    private final SeckillVoucherDao seckillVoucherDao;

    @Override
    public List<SeckillVoucher> list() {
        return seckillVoucherDao.list();
    }
    
    @Override
    public SeckillVoucher getById(Long voucherId) {
        return seckillVoucherDao.findById(voucherId);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(SeckillVoucher seckillVoucher) {
        try {
            int affected = seckillVoucherDao.update(seckillVoucher);
            return affected > 0;
        } catch (Exception e) {
            log.error("更新优惠券信息失败, voucherId: {}", seckillVoucher.getVoucherId(), e);
            return false;
        }
    }

    @Override
    public Integer getVoucherStock(Long voucherId) {
        SeckillVoucher voucher = seckillVoucherDao.findById(voucherId);
        return voucher != null ? voucher.getStock() : 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deductVoucherStock(Long voucherId) {
        try {
            // 使用数据库行锁进行库存扣减
            int affected = seckillVoucherDao.deductStock(voucherId);
            return affected > 0;
        } catch (Exception e) {
            log.error("扣减优惠券库存失败, voucherId: {}", voucherId, e);
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addVoucherStock(Long voucherId, Integer amount) {
        try {
            int affected = seckillVoucherDao.addStock(voucherId, amount);
            return affected > 0;
        } catch (Exception e) {
            log.error("增加优惠券库存失败, voucherId: {}, amount: {}", voucherId, amount, e);
            return false;
        }
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Long voucherId) {
        try {
            int affected = seckillVoucherDao.removeById(voucherId);
            return affected > 0;
        } catch (Exception e) {
            log.error("删除优惠券失败, voucherId: {}", voucherId, e);
            return false;
        }
    }
} 