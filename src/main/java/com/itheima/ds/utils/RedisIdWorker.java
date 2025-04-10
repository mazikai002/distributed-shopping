package com.itheima.ds.utils;

import com.itheima.ds.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Redis分布式ID生成器
 */
@Component
@RequiredArgsConstructor
public class RedisIdWorker {

    private final StringRedisTemplate stringRedisTemplate;
    
    /**
     * 开始时间戳
     */
    private static final long BEGIN_TIMESTAMP = 1640995200L; // 2022-01-01 00:00:00
    
    /**
     * 序列号的位数
     */
    private static final int COUNT_BITS = 32;

    /**
     * 生成全局唯一ID
     * @param keyPrefix 业务前缀
     * @return 全局唯一ID
     */
    public long nextId(String keyPrefix) {
        // 1. 生成时间戳
        LocalDateTime now = LocalDateTime.now();
        long nowSecond = now.toEpochSecond(ZoneOffset.UTC);
        long timestamp = nowSecond - BEGIN_TIMESTAMP;
        
        // 2. 生成序列号
        // 2.1 获取当前日期，精确到天
        String date = now.format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
        // 2.2 自增长
        long count = stringRedisTemplate.opsForValue().increment("icr:" + keyPrefix + ":" + date);
        
        // 3. 拼接并返回
        // 时间戳向左移32位，然后与序列号做或运算
        return timestamp << COUNT_BITS | count;
    }
} 