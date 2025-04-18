package com.itheima.ds.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class ValidatorUtil {

	private static final Pattern mobile_pattern = Pattern.compile("1\\d{10}");
	
	public static void main(String[] args){
		System.out.println(isMobile("18912341234"));
		System.out.println(isMobile("1891234123"));
	}
	
	public static boolean isMobile(String src){
		if(StringUtils.isEmpty(src)){
			return false;
		}
		Matcher m = mobile_pattern.matcher(src);
		return m.matches();
	}
	
}