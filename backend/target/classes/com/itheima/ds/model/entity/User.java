package com.itheima.ds.model.entity;

import java.io.Serializable;

/**
 * 用户实体类
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String nickname;
    private String password;
    private String salt;
    private String head;
    private String registerDate;
    private String lastLoginDate;
    private Integer loginCount;
    
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
    
    public String getHead() {
        return head;
    }
    
    public void setHead(String head) {
        this.head = head;
    }
    
    public String getRegisterDate() {
        return registerDate;
    }
    
    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }
    
    public String getLastLoginDate() {
        return lastLoginDate;
    }
    
    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }
    
    public Integer getLoginCount() {
        return loginCount;
    }
    
    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }
}