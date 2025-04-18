package com.itheima.ds.model.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 秒杀优惠券实体类
 */
@Data
public class SeckillVoucher implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 关联的优惠券ID
     */
    private Long voucherId;
    
    /**
     * 库存数量
     */
    private Integer stock;
    
    /**
     * 秒杀开始时间
     */
    private LocalDateTime beginTime;
    
    /**
     * 秒杀结束时间
     */
    private LocalDateTime endTime;
    
    /**
     * 秒杀价格
     */
    private BigDecimal price;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 