package com.itheima.ds.controller.v1;

import com.itheima.ds.common.result.Result;
import com.itheima.ds.model.entity.SeckillOrder;
import com.itheima.ds.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order/v1")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{orderId}")
    public Result<SeckillOrder> getOrder(@PathVariable Long orderId) {
        return Result.success(orderService.getOrderById(orderId));
    }

    @PostMapping
    public Result<Long> createOrder(@RequestBody SeckillOrder order) {
        return Result.success(orderService.createOrder(order));
    }
} 