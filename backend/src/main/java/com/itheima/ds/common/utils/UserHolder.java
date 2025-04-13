package com.itheima.ds.common.utils;

import com.itheima.ds.model.entity.SeckillUser;

/**
 * 用户信息持有类
 * 基于ThreadLocal实现线程级别的用户信息存储
 */
public class UserHolder {
    private static final ThreadLocal<SeckillUser> userThreadLocal = new ThreadLocal<>();

    /**
     * 设置当前线程的用户信息
     * @param user 用户信息
     */
    public static void setUser(SeckillUser user) {
        userThreadLocal.set(user);
    }

    /**
     * 获取当前线程的用户信息
     * @return 当前用户
     */
    public static SeckillUser getUser() {
        return userThreadLocal.get();
    }

    /**
     * 清除当前线程的用户信息
     */
    public static void removeUser() {
        userThreadLocal.remove();
    }
} 