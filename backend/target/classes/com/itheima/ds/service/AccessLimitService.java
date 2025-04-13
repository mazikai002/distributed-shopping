package com.itheima.ds.service;

import org.springframework.stereotype.Service;

import com.google.common.util.concurrent.RateLimiter;

import com.itheima.ds.config.SecKillConfig;


/**
 * 令牌桶算法
 * @author Yin
 *
 */
@Service
public class AccessLimitService {

  /**
   * 每秒钟只能发出指定个数个令牌，拿到令牌的请求才可以进入秒杀过程
   */
  private RateLimiter seckillRateLimiter = RateLimiter.create(SecKillConfig.SECKILL_FLU_NUM);
	
  
  /**
   * 尝试获取令牌
   */
  public boolean tryAcquirelToken(){
	  return seckillRateLimiter.tryAcquire();
  }
  
	
}
