package com.itheima.ds.controller.v2;

import com.itheima.ds.common.result.Result;
import com.itheima.ds.domain.SeckillUser;
import com.itheima.ds.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = "登录接口")
@RestController
@RequestMapping("/login/v2")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @ApiOperation("用户登录")
    @PostMapping("/do_login")
    public Result<String> doLogin(@RequestBody SeckillUser user) {
        return Result.success(loginService.loginWithToken(user));
    }
    
    @ApiOperation("刷新Token")
    @PostMapping("/refresh")
    public Result<String> refreshToken(@RequestHeader("Authorization") String token) {
        return Result.success(loginService.refreshToken(token));
    }
    
    @ApiOperation("用户登出")
    @PostMapping("/logout")
    public Result<Void> logout(@RequestHeader("Authorization") String token) {
        loginService.logout(token);
        return Result.success();
    }
} 