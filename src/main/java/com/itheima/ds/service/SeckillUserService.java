package com.itheima.ds.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import com.itheima.ds.config.SecKillConfig;
import com.itheima.ds.domain.SeckillGoods;
import com.itheima.ds.domain.SeckillUser;
import com.itheima.ds.common.exception.GlobalException;
import com.itheima.ds.mapper.SeckillUserMapper;
import com.itheima.ds.redisCluster.RedisService;
import com.itheima.ds.redisCluster.SecKillActivityKey;
import com.itheima.ds.result.CodeMsg;
import com.itheima.ds.common.utils.MD5Util;
import com.itheima.ds.common.utils.UUIDUtil;
import com.itheima.ds.vo.LoginVo;

@Service
public class SeckillUserService {
	  

	@Autowired
	private SeckillUserMapper seckillUserMapper;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private SeckillUserService seckillUserService;
	
	
	
	public List<SeckillGoods> getSeckillGoodsVoList(){
		// 查询所有参与秒杀的商品Id
	   List<SeckillGoods> seckillGoodsList = this.seckillUserMapper.getSeckillGoodsList();
	    
		return seckillGoodsList; 
	} 
	
	
	   
	
	public SeckillUser getUserById(long id){
		  //取缓存
		SeckillUser user = redisService.get(SecKillActivityKey.getById, ""+id, SeckillUser.class);
		if(user!=null) return user;
		
		//若用户为空，则需要取数据库
		user = this.seckillUserMapper.getUserById(id);
		if(user!=null){
			redisService.set(SecKillActivityKey.getById, ""+id, user);
		}
		return user;
	} 
	
	
	public boolean upatePassword(String token,long id,String newPassword){
		//取user
		SeckillUser user = getUserById(id);
		if(user == null) throw new GlobalException(CodeMsg.MOBILE_NOT_EXISTS);
		
		  //更新数据库
		SeckillUser toBeUpdate = new SeckillUser();
		toBeUpdate.setId(id);
		toBeUpdate.setPassword(MD5Util.formPassToDBPass(newPassword, user.getSalt()));
		this.seckillUserMapper.update(toBeUpdate);
		
		//处理缓存,否则会出现缓存不一致的问题
		    //删除的原因是
		redisService.delete(SecKillActivityKey.getById, ""+id);
		  //如若删除token 则无法登录，此处只需要更新token
		user.setPassword(toBeUpdate.getPassword());
		redisService.set(SecKillActivityKey.token, token,user);
		return true;
	}

	
	@RequestMapping(value="/do_login",method=RequestMethod.GET) 
    @ResponseBody 
	public Map<String,Object> login(HttpServletResponse response,@RequestParam("nickname")String nickname,@RequestParam("password")String password) { 
	 
		//if(loginVo == null)   throw new GlobalException(CodeMsg.SERVER_ERROR);
 	  

		Map<String,Object> map =new HashMap<String,Object>();
		   
		SeckillUser user = this.seckillUserService.getSeckillUserByNickName(nickname);
		     // System.out.println(user.getSalt()); 
		   
		   String realPassword = MD5Util.inputPassToDBPass(password,user.getSalt());  //加密后的密码
		    //登录成功
		   if(!realPassword.equals(user.getPassword())) map.put("loginStatus", 0);
	 	   this.seckillUserService.updateSeckillUserLoginInfo(nickname); 
	    	map.put("loginStatus",1);
	 		//System.out.println("用户输入密码123456，则真正网络上传输的串："+MD5Util.inputPassToFormPass("123456"));
	       // System.out.println(MD5Util.inputPassToDBPass("123456","1a2b3c4d")); 
		    
		    String token = UUIDUtil.uuid();
		    generateCookie(response,token,user);
		    
		    
		     LoginVo.getInstance().setUserId(user.getId()); 
		     LoginVo.getInstance().setToken(token); 
		     
		     
		    this.seckillUserService.getUserInfoByToken(response,token);
	        
		    return map;
	   }
	
	 
	
	  /**
	 * 登录信息更新
	 */
	public void updateSeckillUserLoginInfo(String nickname){
	    this.seckillUserMapper.updateSeckillUserLoginInfo(nickname);	
	}
	 
	
	public SeckillUser getSeckillUserByNickName(String nickname){
		return this.seckillUserMapper.getSeckillUserByNickName(nickname);
	}
	
	
	
	
	private void generateCookie(HttpServletResponse response,String token,SeckillUser user){
		//生成cookie
		  //String token = UUIDUtil.uuid();
		   //token对应的用户信息写入redis
		  redisService.set(SecKillActivityKey.token, token, user);
		  Cookie cookie = new Cookie(SecKillConfig.COOKIE_NAME_TOKEN,token); 
		  cookie.setMaxAge(SecKillActivityKey.token.expireSeconds()); //cookie有效期
		  cookie.setPath("/");
		  response.addCookie(cookie); 
	}

 
	public SeckillUser getUserInfoByToken(HttpServletResponse response,String token) { 
		if(StringUtils.isEmpty(token)){
			return null;
		}
		//System.out.println("token:"+token);
		SeckillUser user = redisService.get(SecKillActivityKey.token, token, SeckillUser.class);
		//延长有效期 
		 if(user!=null){
			 //System.out.println("user==null: "+ (user==null));
			 generateCookie(response,token,user);
		 }
		 return user;
	 }
	
}
