package com.itheima.ds.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class TimeUtil {
	
	public static int initalTime = 4;
	
	public static int maxtTime = 10;

	public static void main(String[] args) {

		System.out.println(getRandomTimeValue());
		
	} 
	
	/**
	 * 获取随机数值  (单位：秒)
	 * @param maxtTime
	 * @return
	 */
	public static int getRandomTimeValue(){
		Random rand = new Random();
		return 60*(initalTime + rand.nextInt(maxtTime));
	}
	
	
	public static String getRandomTimeId(){
		Calendar calendar = Calendar.getInstance(); // get current instance of the calendar  
		//SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String result = formatter.format(calendar.getTime());
		
		System.out.println();  
        return result;
	}
	

}