package com.itheima.ds.controller.v2;

import com.itheima.ds.common.ResponseBean;
import com.itheima.ds.service.ISeckillService;
import com.itheima.ds.service.GoodsService;
import com.itheima.ds.model.vo.GoodsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * V2 版本的秒杀控制器
 * 使用Redis进行库存扣减以提高性能
 */
@Slf4j
@RestController
@RequestMapping("/api/v2/seckill")
@RequiredArgsConstructor
@Api(tags = "秒杀接口-V2")
public class SeckillController {

    @Qualifier("v2SeckillService")
    private final ISeckillService seckillService;
    
    private final GoodsService goodsService;

    /**
     * 获取秒杀商品列表
     * @return 商品列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取秒杀商品列表")
    public ResponseEntity<List<GoodsVO>> list() {
        try {
            List<GoodsVO> goodsList = goodsService.listGoodsVO();
            return ResponseEntity.ok(goodsList);
        } catch (Exception e) {
            log.error("获取秒杀商品列表失败", e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * 执行秒杀操作
     * @param goodsId 商品ID
     * @return 订单ID
     */
    @PostMapping("/{goodsId}")
    public ResponseEntity<Long> doSeckill(@PathVariable("goodsId") Long goodsId) {
        log.info("v2秒杀请求, 商品ID: {}", goodsId);
        
        try {
            // 调用秒杀服务
            Long orderId = seckillService.seckill(goodsId);
            return ResponseEntity.ok(orderId);
        } catch (Exception e) {
            log.error("v2秒杀失败", e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * 预加载商品库存到Redis
     * @param goodsId 商品ID
     * @return 预加载结果
     */
    @PostMapping("/preload/{goodsId}")
    public ResponseEntity<String> preloadStock(@PathVariable("goodsId") Long goodsId) {
        log.info("预加载商品库存到Redis, 商品ID: {}", goodsId);
        
        try {
            seckillService.preloadStock(goodsId);
            return ResponseEntity.ok("商品库存预加载成功");
        } catch (Exception e) {
            log.error("商品库存预加载失败", e);
            return ResponseEntity.badRequest().body("商品库存预加载失败: " + e.getMessage());
        }
    }

    /**
     * 健康检查接口
     *
     * @return 健康状态
     */
    @ApiOperation(value = "健康检查", notes = "测试接口是否正常响应")
    @GetMapping("/health")
    public ResponseBean<String> health() {
        return ResponseBean.success("V2秒杀服务正常");
    }
} 