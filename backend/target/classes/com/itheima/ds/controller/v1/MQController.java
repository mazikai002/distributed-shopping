package com.itheima.ds.controller.v1;

import com.itheima.ds.common.result.Result;
import com.itheima.ds.service.MQService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = "消息队列接口")
@RestController
@RequestMapping("/mq/v1")
@RequiredArgsConstructor
public class MQController {

    private final MQService mqService;

    @ApiOperation("发送消息")
    @PostMapping("/send")
    public Result<Void> sendMessage(@RequestBody String message) {
        mqService.sendMessage(message);
        return Result.success();
    }

    @ApiOperation("接收消息")
    @GetMapping("/receive")
    public Result<String> receiveMessage() {
        return Result.success(mqService.receiveMessage());
    }
} 