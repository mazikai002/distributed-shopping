package com.itheima.ds.redis;

/**
 * Redis键前缀接口
 */
public interface KeyPrefix {
    
    /**
     * 过期时间（秒）
     * @return 过期时间
     */
    int expireSeconds();
    
    /**
     * 获取前缀
     * @return 前缀
     */
    String getPrefix();
} 