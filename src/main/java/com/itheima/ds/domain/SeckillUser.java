package com.itheima.ds.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * 秒杀用户
 */
@Data
@TableName("seckill_user")
public class SeckillUser implements Serializable {
    
    private Long id;
    
    @NotBlank(message = "用户名不能为空")
    private String username;
    
    @NotBlank(message = "密码不能为空")
    private String password;
    
    private String nickname;
    
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式错误")
    private String mobile;
    
    private String email;
    
    private String head;
    
    private Date registerDate;
    
    private Date lastLoginDate;
    
    private Integer loginCount;
} 