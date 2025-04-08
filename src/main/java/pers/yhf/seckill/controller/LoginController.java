package pers.yhf.seckill.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pers.yhf.seckill.config.SecKillConfig;
import pers.yhf.seckill.domain.SeckillUser;
import pers.yhf.seckill.redisCluster.RedisService;
import pers.yhf.seckill.redisCluster.SecKillActivityKey;
import pers.yhf.seckill.result.Result;
import pers.yhf.seckill.service.SeckillUserService;
import pers.yhf.seckill.common.utils.MD5Util;
import pers.yhf.seckill.common.utils.UUIDUtil;
import pers.yhf.seckill.vo.LoginVo; 
 

@Controller
@RequestMapping("/login")
public class LoginController {
	
	private static Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private RedisService redisService;
	
	@Autowired
	private SeckillUserService seckillUserService;
	
	
	@RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }
	
	
	@RequestMapping("/do_login")
    @ResponseBody
    public Result<String> doLogin(HttpServletResponse response,@RequestParam("nickname")String nickname,@RequestParam("password")String password) {
		/*//log.info(loginVo.toString());
		 * 
		 *  
	    //登录 
	   miaoshaUserService.login(response,nickname,password);
		 return Result.success(true);*/
		   
		    // System.out.println("password: "+password); 
		   
		SeckillUser user = this.seckillUserService.getSeckillUserByNickName(nickname);
		     // System.out.println(user.getSalt()); 
		   
		   String realPassword = MD5Util.inputPassToDBPass(password,user.getSalt());  //加密后的密码
		   
		     //System.out.println("realPassword: "+realPassword+"         "+realPassword.equals( user.getPassword() ) ); 
		   
		    //登录成功
		   if(!realPassword.equals(user.getPassword())){
			   //map.put("loginStatus", 0);
			   return Result.success("");
		   }
	 	  
		   //this.miaoshaUserService.updateMiaoshaUserLoginInfo(nickname); 
	    	
		    //map.put("loginStatus",1);
	 		//System.out.println("用户输入密码123456，则真正网络上传输的串："+MD5Util.inputPassToFormPass("123456"));
	       // System.out.println(MD5Util.inputPassToDBPass("123456","1a2b3c4d")); 
		    
		    String token = UUIDUtil.uuid();
		    generateCookie(response,token,user);
		    
		    
		     LoginVo.getInstance().setUserId(user.getId()); 
		     LoginVo.getInstance().setToken(token);  
		     
		     
		     seckillUserService.getUserInfoByToken(response,token);
	        
		    return Result.success(token);
		
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
	
	
	

	  

	
	
	
	
	
	
	
}
