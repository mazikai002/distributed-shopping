package com.itheima.ds.controller.v2;

import com.itheima.ds.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "测试接口")
@RestController
@RequestMapping("/test/v2")
@RequiredArgsConstructor
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
    
    @ApiOperation("系统信息")
    @GetMapping("/system")
    public Result<Map<String, Object>> systemInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("javaVersion", System.getProperty("java.version"));
        info.put("osName", System.getProperty("os.name"));
        info.put("processors", Runtime.getRuntime().availableProcessors());
        info.put("memoryFree", Runtime.getRuntime().freeMemory());
        info.put("memoryTotal", Runtime.getRuntime().totalMemory());
        return Result.success(info);
    }
    
    @ApiOperation("负载测试")
    @GetMapping("/load")
    public Result<Long> loadTest(@RequestParam(defaultValue = "1000") int iterations) {
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < iterations; i++) {
            // 模拟计算密集型操作
            Math.sqrt(Math.pow(i, 2));
        }
        
        long endTime = System.currentTimeMillis();
        return Result.success(endTime - startTime);
    }
} 