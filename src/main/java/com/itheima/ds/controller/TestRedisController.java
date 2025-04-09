package com.itheima.ds.controller;

 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
@RestController
@RequestMapping(value = "/redis")
public class TestRedisController {

    /*@Autowired
    RedisClientTemplate redisClientTemplate;

    @GetMapping(value = "/testSet")
    public Object testSet(){
        redisClientTemplate.setToRedis("Frank","Frank测试redis");
        System.out.println(redisClientTemplate.getRedis("Frank"));
        return null;
    }*/

} 