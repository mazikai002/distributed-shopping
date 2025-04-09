package pers.yhf.seckill.model.dto;

import lombok.Data;

/**
 * 秒杀请求参数DTO
 */
@Data
public class SeckillDTO {
    private Long goodsId;
    private String path;
}