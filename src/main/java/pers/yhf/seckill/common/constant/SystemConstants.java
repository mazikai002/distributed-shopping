package pers.yhf.seckill.common.constant;

/**
 * 系统常量定义
 */
public class SystemConstants {
    
    /**
     * 登录相关常量
     */
    public static class Login {
        public static final String COOKIE_NAME_TOKEN = "token";
        public static final int TOKEN_EXPIRE = 3600 * 24 * 2; // 2天
    }
    
    /**
     * 秒杀相关常量
     */
    public static class SecKill {
        public static final int GOODS_STOCK_EMPTY = 0;
        public static final long SECKILL_PATH_EXPIRE = 60; // 秒杀地址有效期60秒
    }
    
    /**
     * Redis相关常量
     */
    public static class Redis {
        public static final String SECKILL_GOODS_STOCK = "sk:gs:";
        public static final String SECKILL_PATH = "sk:path:";
        public static final String SECKILL_VERIFY_CODE = "sk:vc:";
        public static final String ACCESS_COUNT = "access:count:";
    }
    
    /**
     * 业务相关常量
     */
    public static class Business {
        public static final int ORDER_NOT_EXIST = -1;
        public static final int MOBILE_LENGTH = 11;
    }
}