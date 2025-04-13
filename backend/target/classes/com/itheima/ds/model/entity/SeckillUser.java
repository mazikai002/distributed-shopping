package com.itheima.ds.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * 秒杀用户实体类
 */
@Data
@TableName("seckill_user")
public class SeckillUser implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 用户名
     */
    @NotNull(message = "用户名不能为空")
    @NotEmpty(message = "用户名不能为空")
    private String username;
    
    /**
     * 密码
     */
    @NotNull(message = "密码不能为空")
    @NotEmpty(message = "密码不能为空")
    private String password;
    
    /**
     * 昵称
     */
    private String nickname;
    
    /**
     * 盐值
     */
    private String salt;
    
    /**
     * 手机号
     */
    @NotNull(message = "手机号不能为空")
    @NotEmpty(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式错误")
    private String mobile;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 头像
     */
    private String head;
    
    /**
     * 注册时间
     */
    private Date registerDate;
    
    /**
     * 最后登录时间
     */
    private Date lastLoginDate;
    
    /**
     * 登录次数
     */
    private Integer loginCount;
}