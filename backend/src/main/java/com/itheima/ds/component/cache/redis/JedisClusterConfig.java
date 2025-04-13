package com.itheima.ds.component.cache.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * Redis集群配置
 * 由于项目中使用了JedisCluster，需要提供相应的配置支持
 */
@Component
public class JedisClusterConfig {

    private static final Logger log = LoggerFactory.getLogger(JedisClusterConfig.class);
    
    @Value("${redis.host}")
    private String host;
    
    @Value("${redis.port}")
    private int port;
    
    @Value("${redis.timeout}")
    private int timeout;
    
    @Value("${redis.poolMaxTotal}")
    private int poolMaxTotal;
    
    @Value("${redis.poolMaxIdle}")
    private int poolMaxIdle;
    
    @Value("${redis.poolMaxWait}")
    private int poolMaxWait;
    
    private JedisCluster jedisCluster;
    
    /**
     * 获取JedisCluster实例
     * 如果项目只配置了单点Redis，则将其包装为集群配置
     * @return JedisCluster实例
     */
    @Bean
    public JedisCluster getJedisCluster() {
        if (jedisCluster == null) {
            synchronized (this) {
                if (jedisCluster == null) {
                    JedisPoolConfig poolConfig = new JedisPoolConfig();
                    poolConfig.setMaxTotal(poolMaxTotal);
                    poolConfig.setMaxIdle(poolMaxIdle);
                    poolConfig.setMaxWaitMillis(poolMaxWait);
                    
                    Set<HostAndPort> nodes = new HashSet<>();
                    // 添加Redis节点，这里只有一个节点，但使用JedisCluster接口保持API一致性
                    nodes.add(new HostAndPort(host, port));
                    
                    try {
                        jedisCluster = new JedisCluster(nodes, timeout, poolConfig);
                        log.info("JedisCluster initialized successfully with host: {}, port: {}", host, port);
                    } catch (Exception e) {
                        log.error("Failed to initialize JedisCluster", e);
                    }
                }
            }
        }
        return jedisCluster;
    }
} 