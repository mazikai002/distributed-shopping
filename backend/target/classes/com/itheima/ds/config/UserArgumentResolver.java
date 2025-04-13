package com.itheima.ds.config;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.thymeleaf.util.StringUtils;

import com.itheima.ds.model.entity.SeckillUser;
import com.itheima.ds.service.SeckillUserService;
 

@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver{

	@Autowired
	private SeckillUserService miaoshaUserService;
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) { 
	   Class<?> clazz = parameter.getParameterType();
	   return clazz == SeckillUser.class;
	}

	
	
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mvContainer, 
			NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception { 
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
		
		String paramToken = request.getParameter(SecKillConfig.COOKIE_NAME_TOKEN);
		String cookieToken = getCookieValue(request,SecKillConfig.COOKIE_NAME_TOKEN);
		
		if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)){
			return null;
		}
		String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
		return miaoshaUserService.getUserInfoByToken(response, token);
	 }



	private String getCookieValue(HttpServletRequest request, String cookieName) {
		 Cookie[] cookies = request.getCookies();
		 
		   //该部分缺失 会在jmeter压测时出现错误率100%的问题
		 if(cookies == null || cookies.length <= 0){
				return null;
			}
		 for(Cookie cookie:cookies){
			 if(cookie.getName().equals(cookieName)){
				 return cookie.getValue();
			 }
		 }
		return null;
	}

	
	
	
}
