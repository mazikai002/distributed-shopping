package com.itheima.ds.model.dto;

import java.io.Serializable;

/**
 * 用户数据传输对象
 */
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String nickname;
    private String mobile;
    private String password;
    private String salt;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNickname() {
        return nickname;
    }
    
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
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
    
    public String getSalt() {
        return salt;
    }
    
    public void setSalt(String salt) {
        this.salt = salt;
    }
}