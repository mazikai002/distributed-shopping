package com.itheima.ds.config;

import com.itheima.ds.component.cache.redis.RedisClient;
import com.itheima.ds.common.utils.RedisIdWorker;
import com.itheima.ds.service.GoodsService;
import com.itheima.ds.service.ISeckillService;
import com.itheima.ds.service.OrderService;
import com.itheima.ds.service.impl.v2.SeckillServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 服务配置类
 * 注册不同版本的服务实现
 */
@Configuration
public class ServiceConfig {

    @Autowired
    private GoodsService goodsService;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private RedisClient redisClient;
    
    @Autowired
    private RedisIdWorker redisIdWorker;

    /**
     * 注册V2版本的秒杀服务实现
     * 使用Redis进行库存扣减，提高并发性能
     */
    @Bean("v2SeckillService")
    public ISeckillService v2SeckillService() {
        return new SeckillServiceImpl(goodsService, orderService, redisClient, redisIdWorker);
    }
} 