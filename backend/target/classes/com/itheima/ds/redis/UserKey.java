package com.itheima.ds.redis;

/**
 * 用户相关的Redis键
 */
public class UserKey extends BasePrefix {

    public static final int TOKEN_EXPIRE = 3600 * 24 * 2; // 2天
    
    private UserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    
    /**
     * 用户Token缓存
     */
    public static final UserKey token = new UserKey(TOKEN_EXPIRE, "tk");
    
    /**
     * 用户ID缓存
     */
    public static final UserKey getById = new UserKey(0, "id");
} 