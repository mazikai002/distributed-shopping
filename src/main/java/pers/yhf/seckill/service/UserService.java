package pers.yhf.seckill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pers.yhf.seckill.domain.User;
import pers.yhf.seckill.repository.UserRepository2;

@Service
public class UserService {

	@Autowired
	private UserRepository2 userMapper;
	
	
	public User getUserById(int id){
		return userMapper.getUserById(id);
	}


	// @Transactional
	public boolean tx() { 
		User u1 = new User();
		u1.setId(2);
		u1.setUsername("222"); 
		userMapper.insert(u1);
		
		User u2 = new User();
		u2.setId(1);
		u2.setUsername("1111"); 
		userMapper.insert(u2);
		return true;
	}


	@Transactional
	public void txDecrease() {
		 
			 User u1 = new User();
				u1.setId(1);
				u1.setUsername("222"); 
				u1.setPassword("123");
				u1.setPhone("12345679");
				
			    this.userMapper.decrease(u1);
			    User user = this.userMapper.getUserById(u1.getId());
			   System.out.println("flag="+user.getFlag()); 
			 
			  
		
	     if(user.getFlag()<4) throw new RuntimeException();
		
	}
	
}
