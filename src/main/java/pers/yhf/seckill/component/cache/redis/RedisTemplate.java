package pers.yhf.seckill.component.cache.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.JedisCluster;

/**
 * Redis操作模板组件
 * 提供Redis集群操作的基础设施支持
 * @author Administrator
 */
@Component
public class RedisTemplate {

    private static final Logger log = LoggerFactory.getLogger(RedisTemplate.class);
    
    @Autowired
    private JedisClusterConfig jedisClusterConfig;

    /**
     * 设置对象
     * @param prefix 键前缀
     * @param key 键
     * @param value 值
     * @return 是否成功
     */
    public <T> boolean set(KeyPrefix prefix, String key, T value) {
        JedisCluster jedisCluster = null;
        try {
            jedisCluster = jedisClusterConfig.getJedisCluster();
            
            String str = beanToString(value);
            if (str == null || str.length() <= 0) {
                return false;
            }
            String realKey = prefix.getPrefix() + key;
            int seconds = prefix.expireSeconds();
            
            if (seconds <= 0) {
                jedisCluster.set(realKey, str);
            } else {
                jedisCluster.setex(realKey, seconds, str);
            }
            return true;
        } catch (Exception ex) {
            log.error("setToRedis:{Key:" + key + ",value" + value + "}", ex);
        }
        return false;
    }

    /**
     * 获取对象
     * @param prefix 键前缀
     * @param key 键
     * @param clazz 类型
     * @return 对象
     */
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
        JedisCluster jedisCluster = null;
        try { 
            jedisCluster = jedisClusterConfig.getJedisCluster();
            String realKey = prefix.getPrefix() + key;
            String str = jedisCluster.get(realKey);
            return stringToBean(str, clazz);
        } finally {}
    }

    /**
     * 字符串转换为对象
     */
    @SuppressWarnings("unchecked")
    private static <T> T stringToBean(String str, Class<T> clazz) {
        if (str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T)Integer.valueOf(str); 
        } else if (clazz == String.class) {
            return (T)str;
        } else if (clazz == long.class || clazz == Long.class) {
            return (T)Long.valueOf(str); 
        } else { 
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

    /**
     * 对象转换为字符串
     */
    private static <T> String beanToString(T value) { 
        if (value == null) {
            return null;
        }
        Class<?> clazz = value.getClass(); 
        if (clazz == int.class || clazz == Integer.class) {
            return "" + value;
        } else if (clazz == String.class) {
            return (String)value;
        } else if (clazz == long.class || clazz == Long.class) {
            return "" + value;
        } else {
            return JSON.toJSONString(value);
        }
    }

    /**
     * 判断key是否存在
     */
    public <T> boolean exists(KeyPrefix prefix, String key) {
        JedisCluster jedisCluster = null; 
        try { 
            jedisCluster = jedisClusterConfig.getJedisCluster();
            String realKey = prefix.getPrefix() + key;
            return jedisCluster.exists(realKey);
        } finally {}
    }

    /**
     * 增加值
     */
    public <T> Long incr(KeyPrefix prefix, String key) {
        JedisCluster jedisCluster = null; 
        try {
            jedisCluster = jedisClusterConfig.getJedisCluster();
            String realKey = prefix.getPrefix() + key;
            return jedisCluster.incr(realKey); 
        } finally {}
    }

    /**
     * 减少值
     */
    public <T> Long decr(KeyPrefix prefix, String key) {
        JedisCluster jedisCluster = null; 
        try {
            jedisCluster = jedisClusterConfig.getJedisCluster();
            String realKey = prefix.getPrefix() + key;
            return jedisCluster.decr(realKey);
        } finally {}
    }

    /**
     * 删除
     */
    public boolean delete(KeyPrefix prefix, String key) {
        JedisCluster jedisCluster = null; 
        try {
            jedisCluster = jedisClusterConfig.getJedisCluster();
            String realKey = prefix.getPrefix() + key;
            long ret = jedisCluster.del(realKey);
            return ret > 0;
        } finally {}
    }
}