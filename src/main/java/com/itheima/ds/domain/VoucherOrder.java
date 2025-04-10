package com.itheima.ds.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 优惠券订单
 */
@Data
@TableName("voucher_order")
public class VoucherOrder implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键
     */
    private Long id;
    
    /**
     * 用户id
     */
    @NotNull(message = "用户ID不能为空")
    private Long userId;
    
    /**
     * 代金券id
     */
    @NotNull(message = "优惠券ID不能为空")
    private Long voucherId;
    
    /**
     * 订单状态
     */
    private Integer status;
    
    /**
     * 下单时间
     */
    private LocalDateTime createTime;
    
    /**
     * 支付时间
     */
    private LocalDateTime payTime;
    
    /**
     * 使用时间
     */
    private LocalDateTime useTime;
    
    /**
     * 退款时间
     */
    private LocalDateTime refundTime;
} 