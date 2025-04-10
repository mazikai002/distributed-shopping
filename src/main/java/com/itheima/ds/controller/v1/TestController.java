package com.itheima.ds.controller.v1;

import com.itheima.ds.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(tags = "测试接口")
@RestController
@RequestMapping("/test/v1")
public class TestController {

    @ApiOperation("健康检查")
    @GetMapping("/health")
    public Result<String> healthCheck() {
        return Result.success("Service is up and running!");
    }

    @ApiOperation("测试接口")
    @GetMapping("/hello")
    public Result<String> hello(@RequestParam(defaultValue = "World") String name) {
        return Result.success("Hello, " + name + "!");
    }
} 