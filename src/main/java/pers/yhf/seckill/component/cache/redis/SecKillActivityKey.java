package pers.yhf.seckill.component.cache.redis;

import pers.yhf.seckill.common.constant.RedisConstants;

/**
 * 商品秒杀活动前缀类
 * @author Administrator
 */
public class SecKillActivityKey implements KeyPrefix {

    private int expireSeconds;
    private String prefix;
    
    /**
     * 过期时间
     */
    @Override
    public int expireSeconds() {  //默认0表示永不过期
        return expireSeconds;
    }
    
    @Override
    public int expireSeconds(int seconds) {
        expireSeconds = seconds;
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className+":"+prefix;
    }

    public SecKillActivityKey(int expireSeconds, String prefix) { 
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    //秒杀商品
    public static SecKillActivityKey getGoodsList = new SecKillActivityKey(RedisConstants.GOODSLIST_EXPIRE,"goods_list");
    public static SecKillActivityKey getGoodsDetail = new SecKillActivityKey(RedisConstants.GOODSDETAIL_EXPIRE,"good_detail"); 
    public static SecKillActivityKey getSecKillGoodsStock = new SecKillActivityKey(RedisConstants.SECKILL_GOODSSTOCK_EXPIRE,"goods_stock");
    
    //商品状态和操作
    public static SecKillActivityKey isGoodsOver = new SecKillActivityKey(RedisConstants.JUDGE_GOODSOVER_EXPIRE,"go");
    public static SecKillActivityKey getSecKillPath = new SecKillActivityKey(RedisConstants.SECKILL_PATH_EXPIRE,"mp");  //获取的秒杀路径有效期为60秒
    public static SecKillActivityKey getSecKillVerifyCode = new SecKillActivityKey(RedisConstants.SECKILL_VERIFYCODE_EXPIRE,"vc");
    public static SecKillActivityKey access = new SecKillActivityKey(RedisConstants.SECKILL_ACTIVITY_EXPIRE,"access");  //5秒内最多点击5次
    
    //登录用户前缀
    public static SecKillActivityKey token = new SecKillActivityKey(RedisConstants.TOKEN_EXPIRE,"tk");
    public static SecKillActivityKey getById = new SecKillActivityKey(RedisConstants.GETBYID_EXPIRE,"id"); //对象缓存希望永久
    
    //秒杀订单中的中的uid与gid
    public static SecKillActivityKey getSecKillOrderByUidGid = new SecKillActivityKey(RedisConstants.SECKILL_ORDERBYUIDGID_EXPIRE, "order(uid,gid)");
}