package pers.yhf.seckill.model.param;

import lombok.Data;

/**
 * 秒杀请求参数
 */
@Data
public class SeckillParam {
    private Long goodsId;
    private String path;
}