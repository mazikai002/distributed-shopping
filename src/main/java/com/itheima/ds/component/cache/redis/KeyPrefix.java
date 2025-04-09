package com.itheima.ds.component.cache.redis;

/**
 * Redis键前缀接口
 * @author Administrator
 */
public interface KeyPrefix {
    
    /**
     * 获取过期时间
     * @return 过期秒数，0表示永不过期
     */
    int expireSeconds();
    
    /**
     * 设置过期时间
     * @param seconds 过期秒数
     * @return 过期秒数
     */
    int expireSeconds(int seconds);

    /**
     * 获取前缀
     * @return 前缀字符串
     */
    String getPrefix();
}