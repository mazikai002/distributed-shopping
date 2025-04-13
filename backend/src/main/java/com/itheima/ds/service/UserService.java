package com.itheima.ds.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.ds.model.entity.User;
import com.itheima.ds.dao.mapper.UserMapper;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;
	
	
	public User getUserById(int id){
		return userMapper.findById(id);
	}


	// @Transactional
	public boolean tx() { 
		User u1 = new User();
		u1.setId(2L);
		u1.setNickname("222");
		userMapper.save(u1);
		
		User u2 = new User();
		u2.setId(1L);
		u2.setNickname("1111");
		userMapper.save(u2);
		return true;
	}


	@Transactional
	public void txDecrease() {
		 
		User u1 = new User();
		u1.setId(1L);
		u1.setNickname("222");
		u1.setPassword("123");
				
		this.userMapper.update(u1);
		User user = this.userMapper.findById(u1.getId());
		if(user.getLoginCount() != null && user.getLoginCount() < 4) throw new RuntimeException();
	}
	
}
