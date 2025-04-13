package com.itheima.ds.component.cache.redis;

/**
 * Redis键前缀接口
 * 所有的键前缀都需要实现此接口
 */
public interface KeyPrefix {
    
    /**
     * 获取过期时间，单位秒
     * 0代表永不过期
     * @return 过期秒数
     */
    int expireSeconds();
    
    /**
     * 获取前缀
     * @return 前缀字符串
     */
    String getPrefix();
}