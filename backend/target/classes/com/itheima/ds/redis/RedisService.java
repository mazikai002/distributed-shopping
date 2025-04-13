package com.itheima.ds.redis;

import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis服务类
 */
@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 获取对象
     */
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
        String realKey = prefix.getPrefix() + key;
        String str = stringRedisTemplate.opsForValue().get(realKey);
        return stringToBean(str, clazz);
    }

    /**
     * 设置对象
     */
    public <T> void set(KeyPrefix prefix, String key, T value) {
        String realKey = prefix.getPrefix() + key;
        String str = beanToString(value);
        if (prefix.expireSeconds() <= 0) {
            stringRedisTemplate.opsForValue().set(realKey, str);
        } else {
            stringRedisTemplate.opsForValue().set(realKey, str, prefix.expireSeconds(), TimeUnit.SECONDS);
        }
    }

    /**
     * 设置简单值
     */
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置带过期时间的简单值
     */
    public void set(String key, String value, long timeout) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 获取简单值
     */
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 判断key是否存在
     */
    public boolean exists(KeyPrefix prefix, String key) {
        String realKey = prefix.getPrefix() + key;
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(realKey));
    }

    /**
     * 删除
     */
    public void delete(KeyPrefix prefix, String key) {
        String realKey = prefix.getPrefix() + key;
        stringRedisTemplate.delete(realKey);
    }

    /**
     * 删除简单key
     */
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * 增加值
     */
    public Long incr(KeyPrefix prefix, String key) {
        String realKey = prefix.getPrefix() + key;
        return stringRedisTemplate.opsForValue().increment(realKey);
    }

    /**
     * 减少值
     */
    public Long decr(KeyPrefix prefix, String key) {
        String realKey = prefix.getPrefix() + key;
        return stringRedisTemplate.opsForValue().decrement(realKey);
    }

    /**
     * 将对象转换为字符串
     */
    private <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return String.valueOf(value);
        } else if (clazz == long.class || clazz == Long.class) {
            return String.valueOf(value);
        } else if (clazz == String.class) {
            return (String) value;
        } else {
            return JSON.toJSONString(value);
        }
    }

    /**
     * 将字符串转换为对象
     */
    @SuppressWarnings("unchecked")
    private <T> T stringToBean(String str, Class<T> clazz) {
        if (str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(str);
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(str);
        } else if (clazz == String.class) {
            return (T) str;
        } else {
            return JSON.parseObject(str, clazz);
        }
    }

    /**
     * 向Set中添加值
     */
    public void sAdd(String key, String value) {
        stringRedisTemplate.opsForSet().add(key, value);
    }

    /**
     * 从Set中删除值
     */
    public void sRem(String key, String value) {
        stringRedisTemplate.opsForSet().remove(key, value);
    }

    /**
     * 获取Set中的所有值
     */
    public List<String> sMembers(String key) {
        Set<String> members = stringRedisTemplate.opsForSet().members(key);
        return members != null ? List.copyOf(members) : List.of();
    }
} 