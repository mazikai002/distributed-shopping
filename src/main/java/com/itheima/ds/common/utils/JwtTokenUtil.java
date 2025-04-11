package com.itheima.ds.common.utils;

import com.itheima.ds.model.entity.SeckillUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JWT Token工具类
 */
@Component
public class JwtTokenUtil {

    @Value("${jwt.secret:secret}")
    private String secret;

    @Value("${jwt.expiration:3600}")
    private long expiration;

    /**
     * 从token中获取用户名
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * 从token中获取用户ID
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return Long.parseLong(claims.get("userId", String.class));
    }

    /**
     * 从token中获取过期时间
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * 从token中获取指定的claim
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 获取token中的所有claim
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 检查token是否已过期
     */
    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * 生成token
     */
    public String generateToken(SeckillUser user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId().toString());
        return doGenerateToken(claims, user.getUsername());
    }

    /**
     * 生成token的具体实现
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 验证token是否有效
     */
    public Boolean validateToken(String token, SeckillUser user) {
        final String username = getUsernameFromToken(token);
        final Long userId = getUserIdFromToken(token);
        return (username.equals(user.getUsername()) && userId.equals(user.getId()) && !isTokenExpired(token));
    }
} 