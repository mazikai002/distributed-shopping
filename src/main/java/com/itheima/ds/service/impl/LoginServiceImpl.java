package com.itheima.ds.service.impl;

import com.itheima.ds.domain.SeckillUser;
import com.itheima.ds.redis.RedisService;
import com.itheima.ds.service.LoginService;
import com.itheima.ds.utils.JwtTokenUtil;
import com.itheima.ds.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 登录服务实现类
 */
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final RedisService redisService;
    private final JwtTokenUtil jwtTokenUtil;
    
    @Value("${token.expiration:3600}")
    private long tokenExpiration;
    
    @Value("${token.header:Authorization}")
    private String tokenHeader;
    
    private static final String TOKEN_PREFIX = "token:";

    @Override
    public String login(SeckillUser user) {
        // 验证用户名密码
        if (user == null || StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
            throw new GlobalException("用户名或密码不能为空");
        }
        
        // 这里应该有数据库验证逻辑，简化处理
        
        // 生成简单token
        String token = UUID.randomUUID().toString();
        
        // 将token存入Redis
        String key = TOKEN_PREFIX + token;
        redisService.set(key, user.getId().toString(), tokenExpiration);
        
        return token;
    }

    @Override
    public String loginWithToken(SeckillUser user) {
        // 验证用户名密码
        if (user == null || StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
            throw new GlobalException("用户名或密码不能为空");
        }
        
        // 这里应该有数据库验证逻辑，简化处理
        
        // 生成JWT token
        String token = jwtTokenUtil.generateToken(user);
        
        // 将token存入Redis，用于后续验证
        String key = TOKEN_PREFIX + token;
        redisService.set(key, user.getId().toString(), tokenExpiration);
        
        return token;
    }

    @Override
    public String refreshToken(String token) {
        if (StringUtils.isEmpty(token)) {
            throw new GlobalException("token不能为空");
        }
        
        // 验证token是否存在
        String key = TOKEN_PREFIX + token;
        String userId = redisService.get(key);
        if (StringUtils.isEmpty(userId)) {
            throw new GlobalException("token已过期");
        }
        
        // 获取用户信息
        SeckillUser user = new SeckillUser();
        user.setId(Long.parseLong(userId));
        // 这里应该从数据库或缓存中获取完整的用户信息
        
        // 生成新token
        String newToken = jwtTokenUtil.generateToken(user);
        
        // 删除旧token
        redisService.delete(key);
        
        // 存储新token
        String newKey = TOKEN_PREFIX + newToken;
        redisService.set(newKey, userId, tokenExpiration);
        
        return newToken;
    }

    @Override
    public void logout(String token) {
        if (StringUtils.isEmpty(token)) {
            throw new GlobalException("token不能为空");
        }
        
        // 删除Redis中的token
        String key = TOKEN_PREFIX + token;
        redisService.delete(key);
    }
} 