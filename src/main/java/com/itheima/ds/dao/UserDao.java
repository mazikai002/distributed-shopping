package com.itheima.ds.dao;

import com.itheima.ds.model.entity.User;

/**
 * 用户数据访问接口
 */
public interface UserDao {
    /**
     * 通过ID查询用户
     */
    User findById(long id);

    /**
     * 保存用户信息
     */
    int save(User user);

    /**
     * 更新用户信息
     */
    int update(User user);
}
