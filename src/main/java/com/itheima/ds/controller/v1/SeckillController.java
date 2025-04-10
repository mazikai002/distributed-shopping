package com.itheima.ds.controller.v1;

import com.itheima.ds.common.result.Result;
import com.itheima.ds.service.ISeckillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = "秒杀活动接口")
@RestController
@RequestMapping("/seckill/v1")
@RequiredArgsConstructor
public class SeckillController {

	private final ISeckillService seckillService;

	@ApiOperation("秒杀优惠券")
	@PostMapping("/{id}")
	public Result<Long> seckillVoucher(@PathVariable("id") Long voucherId) {
		return Result.success(seckillService.doSeckill(voucherId));
	}
} 