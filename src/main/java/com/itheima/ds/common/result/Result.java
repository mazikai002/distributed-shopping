package com.itheima.backend.common;

import com.itheima.backend.common.constant.CommonConstant;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 统一响应结果类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    
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
     * 判断是否成功
     */
    public boolean isSuccess() {
        return code == CommonConstant.SUCCESS_CODE;
    }
    
    /**
     * 设置失败结果
     */
    public void setResultFailed(String message) {
        this.code = CommonConstant.ERROR_CODE;
        this.message = message;
        this.data = null;
    }
    
    /**
     * 设置成功结果
     */
    public void setResultSuccess(String message, T data) {
        this.code = CommonConstant.SUCCESS_CODE;
        this.message = message;
        this.data = data;
    }
    
    /**
     * 成功响应
     */
    public static <T> Result<T> success() {
        return new Result<>(CommonConstant.SUCCESS_CODE, CommonConstant.SUCCESS_MESSAGE, null);
    }
    
    /**
     * 成功响应（带数据）
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(CommonConstant.SUCCESS_CODE, CommonConstant.SUCCESS_MESSAGE, data);
    }
    
    /**
     * 成功响应（带消息和数据）
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(CommonConstant.SUCCESS_CODE, message, data);
    }
    
    /**
     * 失败响应
     */
    public static <T> Result<T> error() {
        return new Result<>(CommonConstant.ERROR_CODE, CommonConstant.ERROR_MESSAGE, null);
    }
    
    /**
     * 失败响应（带消息）
     */
    public static <T> Result<T> error(String message) {
        return new Result<>(CommonConstant.ERROR_CODE, message, null);
    }
    
    /**
     * 失败响应（带状态码和消息）
     */
    public static <T> Result<T> error(int code, String message) {
        return new Result<>(code, message, null);
    }
    
    /**
     * 参数错误响应
     */
    public static <T> Result<T> paramError() {
        return new Result<>(CommonConstant.PARAM_ERROR_CODE, CommonConstant.PARAM_ERROR_MESSAGE, null);
    }
    
    /**
     * 未授权响应
     */
    public static <T> Result<T> unauthorized() {
        return new Result<>(CommonConstant.UNAUTHORIZED_CODE, CommonConstant.UNAUTHORIZED_MESSAGE, null);
    }
    
    /**
     * 禁止访问响应
     */
    public static <T> Result<T> forbidden() {
        return new Result<>(CommonConstant.FORBIDDEN_CODE, CommonConstant.FORBIDDEN_MESSAGE, null);
    }
} 