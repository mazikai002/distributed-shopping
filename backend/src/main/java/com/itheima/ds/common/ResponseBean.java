package com.itheima.ds.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * API响应对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBean<T> {
    
    /**
     * 状态码
     */
    private int code;
    
    /**
     * 消息
     */
    private String message;
    
    /**
     * 数据
     */
    private T data;
    
    /**
     * 成功响应
     */
    public static <T> ResponseBean<T> success() {
        return new ResponseBean<>(200, "操作成功", null);
    }
    
    /**
     * 成功响应（带数据）
     */
    public static <T> ResponseBean<T> success(T data) {
        return new ResponseBean<>(200, "操作成功", data);
    }
    
    /**
     * 成功响应（带消息和数据）
     */
    public static <T> ResponseBean<T> success(String message, T data) {
        return new ResponseBean<>(200, message, data);
    }
    
    /**
     * 失败响应
     */
    public static <T> ResponseBean<T> error() {
        return new ResponseBean<>(500, "操作失败", null);
    }
    
    /**
     * 失败响应（带消息）
     */
    public static <T> ResponseBean<T> error(String message) {
        return new ResponseBean<>(500, message, null);
    }
    
    /**
     * 失败响应（带状态码和消息）
     */
    public static <T> ResponseBean<T> error(int code, String message) {
        return new ResponseBean<>(code, message, null);
    }
} 