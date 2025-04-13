package com.itheima.ds.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 错误码与错误信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeMsg {
    
    private int code;
    private String message;
    
    // 通用错误码
    public static final CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static final CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
    public static final CodeMsg PARAMETER_ERROR = new CodeMsg(500101, "参数不合法");
    public static final CodeMsg REQUEST_ILLEGAL = new CodeMsg(500102, "请求非法");
    public static final CodeMsg ACCESS_LIMIT = new CodeMsg(500103, "访问太频繁");
    
    // 登录模块 5002XX
    public static final CodeMsg SESSION_ERROR = new CodeMsg(500210, "用户未登录");
    public static final CodeMsg PASSWORD_EMPTY = new CodeMsg(500211, "密码不能为空");
    public static final CodeMsg MOBILE_EMPTY = new CodeMsg(500212, "手机号不能为空");
    public static final CodeMsg MOBILE_ERROR = new CodeMsg(500213, "手机号格式错误");
    public static final CodeMsg MOBILE_NOT_EXISTS = new CodeMsg(500214, "手机号不存在");
    public static final CodeMsg PASSWORD_ERROR = new CodeMsg(500215, "密码错误");
    public static final CodeMsg TOKEN_EXPIRED = new CodeMsg(500216, "Token已过期");
    
    // 商品模块 5003XX
    public static final CodeMsg GOODS_NOT_EXIST = new CodeMsg(500300, "商品不存在");
    public static final CodeMsg STOCK_NOT_ENOUGH = new CodeMsg(500301, "库存不足");
    
    // 订单模块 5004XX
    public static final CodeMsg ORDER_NOT_EXIST = new CodeMsg(500400, "订单不存在");
    public static final CodeMsg ORDER_CREATE_FAILED = new CodeMsg(500401, "订单创建失败");
    
    // 秒杀模块 5005XX
    public static final CodeMsg SECKILL_OVER = new CodeMsg(500500, "秒杀已结束");
    public static final CodeMsg REPEATE_SECKILL = new CodeMsg(500501, "不能重复秒杀");
    public static final CodeMsg SECKILL_FAIL = new CodeMsg(500502, "秒杀失败");
    
    // 消息队列模块 5006XX
    public static final CodeMsg MQ_SEND_FAIL = new CodeMsg(500600, "消息发送失败");
    public static final CodeMsg MQ_RECEIVE_FAIL = new CodeMsg(500601, "消息接收失败");
    
    public CodeMsg fillArgs(Object... args) {
        String message = String.format(this.message, args);
        return new CodeMsg(this.code, message);
    }
}