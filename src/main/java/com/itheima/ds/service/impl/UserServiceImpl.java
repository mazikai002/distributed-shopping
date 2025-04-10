package com.itheima.ds.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itheima.ds.common.result.CodeMsg;
import com.itheima.ds.model.dto.UserDTO;
import com.itheima.ds.model.entity.User;
import com.itheima.ds.model.vo.LoginVO;
import com.itheima.ds.repository.UserRepository;
import com.itheima.ds.service.UserService;
import com.itheima.ds.util.MD5Util;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDTO login(LoginVO loginVO) {
        if (loginVO == null) {
            throw new RuntimeException(CodeMsg.SERVER_ERROR.getMsg());
        }
        
        String mobile = loginVO.getMobile();
        String formPass = loginVO.getPassword();
        
        // 判断手机号是否存在
        User user = getByMobile(mobile);
        if (user == null) {
            throw new RuntimeException(CodeMsg.MOBILE_NOT_EXISTS.getMsg());
        }
        
        // 验证密码
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
        if (!calcPass.equals(dbPass)) {
            throw new RuntimeException(CodeMsg.PASSWORD_ERROR.getMsg());
        }
        
        // 转换为DTO
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setNickname(user.getNickname());
        userDTO.setMobile(mobile);
        
        return userDTO;
    }
    
    @Override
    public User getById(long id) {
        return userRepository.findById(id);
    }
    
    @Override
    public User getByMobile(String mobile) {
        return userRepository.findByMobile(mobile);
    }
    
    @Override
    public boolean updateUser(User user) {
        if (user == null || user.getId() == null) {
            return false;
        }
        return userRepository.update(user) > 0;
    }
}