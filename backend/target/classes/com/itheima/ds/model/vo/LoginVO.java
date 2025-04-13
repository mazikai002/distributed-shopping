package com.itheima.ds.model.vo;

import java.io.Serializable;

/**
 * 登录数据（单例模式）
 * 用于在系统中共享登录信息
 */
public class LoginVO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // 使用 volatile 关键字确保多线程可见性
    private static volatile LoginVO instance = null;
    
    // 用户ID
    private Long userId;
    
    // 登录令牌
    private String token;
    
    // 手机号
    private String mobile;
    
    // 密码
    private String password;
    
    // 私有构造函数，防止外部实例化
    private LoginVO() {}
    
    /**
     * 获取单例实例（双重检查锁定）
     */
    public static LoginVO getInstance() {
        if (instance == null) {
            return createInstance();
        }
        return instance;
    }
    
    /**
     * 创建实例（同步方法，线程安全）
     */
    private synchronized static LoginVO createInstance() {
        if (instance == null) {
            instance = new LoginVO();
        }
        return instance;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getMobile() {
        return mobile;
    }
    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String toString() {
        return "LoginVO{" +
                "userId=" + userId +
                ", token='" + token + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
} 