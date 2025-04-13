package com.itheima.ds.service;

import com.itheima.ds.model.dto.UserDTO;
import com.itheima.ds.model.entity.User;
import com.itheima.ds.model.vo.LoginVO;

/**
 * 用户服务接口
 */
public interface IUserService {
    
    /**
     * 用户登录
     * @param loginVo 登录信息
     * @return 用户DTO
     */
    UserDTO login(LoginVO loginVo);
    
    /**
     * 根据ID获取用户
     * @param id 用户ID
     * @return 用户
     */
    User getById(long id);
    
    /**
     * 根据手机号获取用户
     * @param mobile 手机号
     * @return 用户
     */
    User getByMobile(String mobile);
    
    /**
     * 更新用户信息
     * @param user 用户
     * @return 是否成功
     */
    boolean updateUser(User user);
} 