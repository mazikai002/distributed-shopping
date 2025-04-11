package com.itheima.ds.service;

import com.itheima.ds.model.entity.SeckillUser;

/**
 * 登录服务接口
 */
public interface LoginService {
    
    /**
     * 用户登录
     * @param user 用户信息
     * @return 登录成功后的token
     */
    String login(SeckillUser user);
    
    /**
     * 基于Token的用户登录
     * @param user 用户信息
     * @return 登录成功后的token
     */
    String loginWithToken(SeckillUser user);
    
    /**
     * 刷新Token
     * @param token 原token
     * @return 新token
     */
    String refreshToken(String token);
    
    /**
     * 用户登出
     * @param token 用户token
     */
    void logout(String token);
} 