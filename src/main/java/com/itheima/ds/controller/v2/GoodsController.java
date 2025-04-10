package com.itheima.ds.controller.v2;

import com.itheima.ds.common.result.Result;
import com.itheima.ds.service.GoodsService;
import com.itheima.ds.vo.GoodsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = "商品接口")
@RestController
@RequestMapping("/goods/v2")
@RequiredArgsConstructor
public class GoodsController {

    private final GoodsService goodsService;

    @ApiOperation("获取商品详情")
    @GetMapping("/{goodsId}")
    public Result<GoodsVo> getGoods(@PathVariable Long goodsId) {
        return Result.success(goodsService.getGoodsVoByGoodsId(goodsId));
    }

    @ApiOperation("更新商品库存")
    @PutMapping("/{goodsId}/stock")
    public Result<Void> updateStock(@PathVariable Long goodsId, @RequestParam Integer stock) {
        goodsService.updateStockWithRedis(goodsId, stock);
        return Result.success("更新库存成功");
    }
} 