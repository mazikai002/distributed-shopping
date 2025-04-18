package com.itheima.ds.controller.v1;

import com.itheima.ds.common.result.Result;
import com.itheima.ds.service.ISeckillService;
import com.itheima.ds.service.GoodsService;
import com.itheima.ds.model.vo.GoodsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * V1 版本的秒杀控制器
 * 使用数据库进行库存扣减
 */
@Slf4j
@RestController("seckillControllerV1")
@RequestMapping("/api/v1/seckill")
@Api(tags = "秒杀接口-V1")
public class SeckillController {

    private final ISeckillService seckillService;
    private final GoodsService goodsService;

    public SeckillController(@Qualifier("v1SeckillService") ISeckillService seckillService, GoodsService goodsService) {
        this.seckillService = seckillService;
        this.goodsService = goodsService;
    }

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
     * @param voucherId 商品ID
     * @return 订单ID
     */
    @PostMapping("/{voucherId}")
    public ResponseEntity<Long> doSeckill(@PathVariable("voucherId") Long voucherId) {
        log.info("v1秒杀请求, 商品ID: {}", voucherId);
        
        try {
            // 调用秒杀服务
            Long orderId = seckillService.doSeckill(voucherId);
            return ResponseEntity.ok(orderId);
        } catch (Exception e) {
            log.error("v1秒杀失败", e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * 预加载商品库存到Redis
     * @param voucherId 商品ID
     * @return 预加载结果
     */
    @PostMapping("/preload/{voucherId}")
    public ResponseEntity<String> preloadStock(@PathVariable("voucherId") Long voucherId) {
        log.info("预加载商品库存到Redis, 商品ID: {}", voucherId);
        
        try {
            seckillService.preloadStock(voucherId);
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
    public Result<String> health() {
        return Result.success("V1秒杀服务正常");
    }
} 