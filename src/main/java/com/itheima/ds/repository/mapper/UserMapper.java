package com.itheima.ds.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.itheima.ds.model.entity.User;

/**
 * 用户数据访问层
 */
@Mapper
public interface UserMapper {
    
    /**
     * 通过ID查询用户
     *
     * @param id 用户ID
     * @return 用户实体
     */
    @Select("select * from user where id = #{id}")
    User findById(@Param("id") long id);
    
    /**
     * 通过手机号查询用户
     *
     * @param mobile 手机号
     * @return 用户实体
     */
    @Select("select * from user where mobile = #{mobile}")
    User findByMobile(@Param("mobile") String mobile);
    
    /**
     * 保存用户信息
     *
     * @param user 用户实体
     * @return 影响行数
     */
    int save(User user);
    
    /**
     * 更新用户信息
     *
     * @param user 用户实体
     * @return 影响行数
     */
    int update(User user);
}