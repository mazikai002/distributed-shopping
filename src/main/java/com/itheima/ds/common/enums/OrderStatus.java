package com.itheima.ds.common.enums;

/**
 * 订单状态枚举
 */
public enum OrderStatus {
    
    NEW(0, "新建未支付"),
    PAID(1, "已支付"),
    SHIPPED(2, "已发货"),
    RECEIVED(3, "已收货"),
    REFUNDED(4, "已退款"),
    FINISHED(5, "已完成");
    
    private int code;
    private String message;
    
    OrderStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }
    
    public int getCode() {
        return code;
    }
    
    public String getMessage() {
        return message;
    }
    
    public static OrderStatus of(int code) {
        for (OrderStatus status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid order status code: " + code);
    }
}