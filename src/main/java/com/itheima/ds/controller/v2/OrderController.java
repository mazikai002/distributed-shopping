package com.itheima.ds.controller.v2;

import com.itheima.ds.common.result.Result;
import com.itheima.ds.domain.SeckillOrder;
import com.itheima.ds.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = "订单接口")
@RestController
@RequestMapping("/order/v2")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @ApiOperation("获取订单详情")
    @GetMapping("/{orderId}")
    public Result<SeckillOrder> getOrder(@PathVariable Long orderId) {
        return Result.success(orderService.getOrderById(orderId));
    }

    @ApiOperation("创建订单")
    @PostMapping
    public Result<Long> createOrder(@RequestBody SeckillOrder order) {
        return Result.success(orderService.createOrderWithRedis(order));
    }
    
    @ApiOperation("获取用户订单列表")
    @GetMapping("/user/{userId}")
    public Result<SeckillOrder> getUserOrders(@PathVariable Long userId) {
        return Result.success(orderService.getUserOrders(userId));
    }
} 