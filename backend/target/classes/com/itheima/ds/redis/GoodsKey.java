package com.itheima.ds.redis;

/**
 * 商品相关的Redis键
 */
public class GoodsKey extends BasePrefix {

    private GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    
    /**
     * 商品列表的缓存时间为60秒
     */
    public static final GoodsKey getGoodsList = new GoodsKey(60, "gl");
    
    /**
     * 商品详情的缓存时间为60秒
     */
    public static final GoodsKey getGoodsDetail = new GoodsKey(60, "gd");
    
    /**
     * 商品库存的缓存不过期
     */
    public static final GoodsKey getGoodsStock = new GoodsKey(0, "gs");
} 