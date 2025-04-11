package com.itheima.ds.dao.mapper;

import com.itheima.ds.dao.UserDao;
import org.apache.ibatis.annotations.*;
import com.itheima.ds.model.entity.User;

/**
 * 用户Mapper接口，实现UserDao接口
 */
@Mapper
public interface UserMapper implements UserDao {
    
    @Override
    @Select("select * from user where id = #{id}")
    User findById(@Param("id") long id);
    
    @Override
    @Insert("insert into user(nickname, password, salt, mobile, register_date, last_login_date, login_count) " +
            "values(#{nickname}, #{password}, #{salt}, #{mobile}, #{registerDate}, #{lastLoginDate}, #{loginCount})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int save(User user);
    
    @Override
    @Update("update user set nickname = #{nickname}, password = #{password}, salt = #{salt}, " +
            "mobile = #{mobile}, last_login_date = #{lastLoginDate}, login_count = #{loginCount} " +
            "where id = #{id}")
    int update(User user);
    
    /**
     * 通过手机号查询用户（Mapper特有方法）
     *
     * @param mobile 手机号
     * @return 用户实体
     */
    @Select("select * from user where mobile = #{mobile}")
    User findByMobile(@Param("mobile") String mobile);
    
    /**
     * 更新用户登录信息（Mapper特有方法）
     *
     * @param userId 用户ID
     * @return 影响行数
     */
    @Update("update user set last_login_date = now(), login_count = login_count + 1 where id = #{userId}")
    int updateLoginInfo(@Param("userId") long userId);
    
    /**
     * 删除用户（Mapper特有方法）
     *
     * @param id 用户ID
     * @return 影响行数
     */
    @Delete("delete from user where id = #{id}")
    int deleteById(@Param("id") long id);
} 