package pers.yhf.seckill.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

import pers.yhf.seckill.domain.User;

@Mapper
public interface UserRepository2 {
    
    @Select("select * from user where id = #{id}")
    public User getUserById(@Param("id") int id);
    
    @Insert("insert into user(id,name) values(#{id},#{name})")
    public int insert(User user);
    
    @Update("update user set flag = flag -1 where id = #{id}")
    public void decrease(User user);
}