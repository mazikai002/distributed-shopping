package com.itheima.ds.common.utils;

import org.apache.commons.codec.digest.DigestUtils;


public class MD5Util {

	/**
	 *  两次MD5
	 *  
	 *  1用户端： pass = MD5 （明文+固定salt） 防止用户的明文密码在网络上传输
	 *  
	 *  2服务端： pass = MD5 （用户输入+随机salt） 
	 */
	
	public static void main(String[] args){
         System.out.println("用户输入密码123456，则真正网络上传输的串："+inputPassToFormPass("123456"));
       // System.out.println(formPassToDBPass(inputPassToFormPass("123456"),"1a2b3c4d")); 
         System.out.println(inputPassToDBPass("123456","1a2b3c4d")); 
	}
	
	public static String md5(String src){
		return DigestUtils.md5Hex(src);
	}
	
	private static final String salt = "1a2b3c4d";
	
	
	/**
	 * 将用户输入的密码转化为 通过作一次MD5 到服务端进行传递
	 * @param inputPass
	 * @return
	 */
	public static String inputPassToFormPass(String inputPass){
		String str = "" + salt.charAt(0)+salt.charAt(2)+inputPass+salt.charAt(5)+salt.charAt(4);
	    return md5(str);
	}
	
	
	//salt为随机的salt
	public static String formPassToDBPass(String formPass,String salt){
		String str = "" + salt.charAt(0)+salt.charAt(2)+formPass+salt.charAt(5)+salt.charAt(4);
	    return md5(str);
	}
	
	
	public static String inputPassToDBPass(String input,String saltDB){
		String formPass = inputPassToFormPass(input);
		String dbPass = formPassToDBPass(formPass,saltDB);
		return dbPass;
	}
	

}