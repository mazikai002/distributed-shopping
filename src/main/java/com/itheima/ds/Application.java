package com.itheima.ds;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
  

@SpringBootApplication
public class Application extends SpringBootServletInitializer implements CommandLineRunner{
	 
    public static void main(String[] args) throws Exception {
       
    	int size = 1000000;
    	BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), size);
    	
    	for (int i = 0; i < size; i++) {
            bloomFilter.put(i);
        }

    	long startTime = System.nanoTime(); // 获取开始时间
        //判断这一百万个数中是否包含29999这个数
        if (bloomFilter.mightContain(29969)) {
            System.out.println("命中了");
        }
        long endTime = System.nanoTime();   // 获取结束时间
        System.out.println("程序运行时间： " + (endTime - startTime) + "ns");

          SpringApplication.run(Application.class, args);
    	
    }


	@Override
	public void run(String... arg0) throws Exception {
		System.out.println("系统启动完毕...");
	}

/*	@Override
	public void run(String... arg0) throws Exception { 
		
		//布隆过滤器
		
		System.out.println("系统启动完毕...");
	}*/
    
  
    
}
