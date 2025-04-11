package com.itheima.ds.service.impl;

import com.itheima.ds.model.entity.Goods;
import com.itheima.ds.model.entity.SeckillGoods;
import com.itheima.ds.common.exception.GlobalException;
import com.itheima.ds.mapper.GoodsMapper;
import com.itheima.ds.redis.GoodsKey;
import com.itheima.ds.redis.RedisService;
import com.itheima.ds.service.GoodsService;
import com.itheima.ds.model.vo.GoodsVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品服务实现类
 */
@Service
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {

    private final GoodsMapper goodsMapper;
    private final RedisService redisService;
    
    private static final String STOCK_KEY = "goods:stock:";

    @Override
    public List<GoodsVo> listGoodsVo() {
        return goodsMapper.listGoodsVo();
    }

    @Override
    public GoodsVo getGoodsVoByGoodsId(Long goodsId) {
        // 先查询Redis缓存
        GoodsVo goodsVo = redisService.get(GoodsKey.getGoodsDetail, "" + goodsId, GoodsVo.class);
        if (goodsVo != null) {
            return goodsVo;
        }
        
        // 缓存未命中，查询数据库
        goodsVo = goodsMapper.getGoodsVoByGoodsId(goodsId);
        if (goodsVo == null) {
            throw new GlobalException("商品不存在");
        }
        
        // 写入缓存
        redisService.set(GoodsKey.getGoodsDetail, "" + goodsId, goodsVo);
        return goodsVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStock(Long goodsId, Integer stock) {
        // 更新数据库
        int result = goodsMapper.updateStock(goodsId, stock);
        if (result == 0) {
            throw new GlobalException("商品库存更新失败");
        }
        
        // 清除缓存
        redisService.delete(GoodsKey.getGoodsDetail, "" + goodsId);
        redisService.delete(GoodsKey.getGoodsList, "");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStockWithRedis(Long goodsId, Integer stock) {
        // 1. 更新Redis缓存中的库存
        String stockKey = STOCK_KEY + goodsId;
        redisService.set(stockKey, String.valueOf(stock));
        
        // 2. 更新数据库
        int result = goodsMapper.updateStock(goodsId, stock);
        if (result == 0) {
            // 数据库更新失败，删除Redis缓存
            redisService.delete(stockKey);
            throw new GlobalException("商品库存更新失败");
        }
        
        // 3. 清除商品详情和列表缓存
        redisService.delete(GoodsKey.getGoodsDetail, "" + goodsId);
        redisService.delete(GoodsKey.getGoodsList, "");
    }
} 