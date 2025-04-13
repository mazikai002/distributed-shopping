package com.itheima.ds.service.impl;

import com.itheima.ds.model.entity.SeckillVoucher;
import com.itheima.ds.model.entity.VoucherOrder;
import com.itheima.ds.service.ISeckillService;
import com.itheima.ds.service.ISeckillVoucherService;
import com.itheima.ds.service.IVoucherOrderService;
import com.itheima.ds.common.utils.RedisIdWorker;
import com.itheima.ds.common.utils.UserHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SeckillServiceImpl implements ISeckillService {

    private final ISeckillVoucherService seckillVoucherService;
    private final IVoucherOrderService voucherOrderService;
    private final RedisIdWorker redisIdWorker;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long doSeckill(Long voucherId) {
        // 1. 查询优惠券
        SeckillVoucher voucher = seckillVoucherService.getById(voucherId);
        // 2. 判断秒杀是否开始
        if (voucher.getBeginTime().isAfter(LocalDateTime.now())) {
            throw new RuntimeException("秒杀尚未开始！");
        }
        // 3. 判断秒杀是否已经结束
        if (voucher.getEndTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("秒杀已经结束！");
        }
        // 4. 判断库存是否充足
        if (voucher.getStock() < 1) {
            throw new RuntimeException("库存不足！");
        }
        // 5. 扣减库存
        boolean success = seckillVoucherService.update()
                .setSql("stock = stock - 1")
                .eq("voucher_id", voucherId)
                .update();
        if (!success) {
            throw new RuntimeException("库存不足！");
        }
        // 6. 创建订单
        VoucherOrder voucherOrder = new VoucherOrder();
        // 6.1 设置订单id
        long orderId = redisIdWorker.nextId("order");
        voucherOrder.setId(orderId);
        // 6.2 设置用户id
        Long userId = UserHolder.getUser().getId();
        voucherOrder.setUserId(userId);
        // 6.3 设置代金券id
        voucherOrder.setVoucherId(voucherId);
        // 6.4 设置订单状态
        voucherOrder.setStatus(1);
        // 6.5 设置创建时间
        voucherOrder.setCreateTime(LocalDateTime.now());
        // 6.6 设置支付时间
        voucherOrder.setPayTime(LocalDateTime.now());
        // 6.7 设置使用时间
        voucherOrder.setUseTime(LocalDateTime.now());
        // 6.8 设置退款时间
        voucherOrder.setRefundTime(LocalDateTime.now());
        // 7. 保存订单
        voucherOrderService.save(voucherOrder);
        // 8. 返回订单id
        return orderId;
    }
} 