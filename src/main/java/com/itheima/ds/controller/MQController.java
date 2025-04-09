package com.itheima.ds.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itheima.ds.rabbitmq.MQSender;
 
@Controller
@RequestMapping("/demo")
public class MQController {
	
	@Autowired
	private MQSender sender; 
	
	
	/*@RequestMapping("/mq/header")
    @ResponseBody
    public Result<String> header() {
 		sender.sendHeader("hello WQ"); 
        return Result.success("hello world");
    }
	
	@RequestMapping("/mq/fanout")
    @ResponseBody
    public Result<String> fanout() {
 		sender.sendFanout("hello WQ"); 
        return Result.success("hello world");
    }
	
	
	@RequestMapping("/mq/topic")
    @ResponseBody
    public Result<String> topic() {
 		sender.sendTopic("hello WQ"); 
        return Result.success("hello world");
    }
	
	
	 	@RequestMapping("/mq")
	    @ResponseBody
	    public Result<String> mq() {
	 		sender.send("hello Ses"); 
	        return Result.success("hello world");
	    }*/
	
	
	
	
	  
}
