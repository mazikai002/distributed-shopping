package pers.yhf.seckill.common.constant;

import pers.yhf.seckill.common.utils.TimeUtil;

/**
 * Redis相关常量配置
 * @author Administrator
 */
public class RedisConstants {
    
    public static final String COOKIE_NAME_TOKEN = "token";
    public static final int TOKEN_EXPIRE = 3600*24*2;  //两天
    
    //秒杀商品缓存操作的过期时间设置   10min  (单位：秒)，其中0表示永久
    public static final int GOODSLIST_EXPIRE = 600 + TimeUtil.getRandomTimeValue();  //秒杀商品清单                  
    public static final int GOODSDETAIL_EXPIRE = 60;           //秒杀商品详情
    public static final int SECKILL_GOODSSTOCK_EXPIRE = 0;      //秒杀商品库存
     
    public static final int JUDGE_GOODSOVER_EXPIRE = 0;        //判断库存是否够
    public static final int SECKILL_PATH_EXPIRE = 60;           //获取的秒杀路径
    public static final int SECKILL_VERIFYCODE_EXPIRE = 300;      //秒杀验证码
    public static final int SECKILL_ACTIVITY_EXPIRE = 5;          //限流操作，秒杀按钮5秒内最多点击5次
    
    public static final int GETBYID_EXPIRE = 0;     //对象缓存希望永久
    
    //秒杀订单中的中的uid与gid       订单有效时间：半小时
    public static final int SECKILL_ORDERBYUIDGID_EXPIRE = 1800 + TimeUtil.getRandomTimeValue();
}