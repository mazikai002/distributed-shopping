package com.itheima.ds.model.vo;

import javax.validation.constraints.NotNull;
import com.itheima.ds.validator.IsMobile;

/**
 * 登录视图对象
 */
public class LoginVO {
    
    @NotNull
    @IsMobile
    private String mobile;
    
    @NotNull
    private String password;
    
    public String getMobile() {
        return mobile;
    }
    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String toString() {
        return "LoginVO{" +
                "mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}