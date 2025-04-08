package pers.yhf.seckill.common.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ReadUtil {

	public static String path = "src/main/resources/application.properties"; 
	
	
	public static void main(String[] args){
		
		  String rabbitmqHost = ReadUtil.getContentFromProperties("rabbitMQHost");
	      String rabbitMQUserName = ReadUtil.getContentFromProperties("rabbitMQUserName");
	      String rabbitMQPassword = ReadUtil.getContentFromProperties("rabbitMQPassword");
	      
	      System.out.println(rabbitmqHost+"   "+rabbitMQUserName+"   "+rabbitMQPassword);
		
	}
	
	
	public static String getContentFromProperties(String propertyName){
		FileReader reader;
		String property = "";
		try {
			reader = new  FileReader(path); 
		     Properties properties = new Properties(); 
		     try {
				properties.load(reader);
			} catch (IOException e) { 
				e.printStackTrace();
			} 
		     property = properties.getProperty(propertyName); 
		} catch (FileNotFoundException e) { 
			e.printStackTrace();
		}
		return property; 
	    
	}
	
}