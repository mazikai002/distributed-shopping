package com.itheima.ds.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // 允许特定源
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedOrigin("http://localhost:5173");
        config.addAllowedOrigin("https://via.placeholder.com");
        
        // 允许跨域的请求头
        config.addAllowedHeader("*");
        
        // 允许跨域的方法
        config.addAllowedMethod("*");
        
        // 允许携带cookie等认证信息
        config.setAllowCredentials(true);
        
        // 设置跨域请求的有效期，单位秒
        config.setMaxAge(3600L);
        
        // 对所有接口都生效
        source.registerCorsConfiguration("/**", config);
        
        return new CorsFilter(source);
    }
} 