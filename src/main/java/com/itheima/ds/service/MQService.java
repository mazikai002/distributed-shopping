package com.itheima.ds.service;

/**
 * 消息队列服务接口
 */
public interface MQService {
    
    /**
     * 发送消息
     * @param message 消息内容
     */
    void sendMessage(String message);
    
    /**
     * 接收消息
     * @return 消息内容
     */
    String receiveMessage();
    
    /**
     * 发送异步消息
     * @param message 消息内容
     */
    void sendAsyncMessage(String message);
    
    /**
     * 发送延迟消息
     * @param message 消息内容
     * @param delaySeconds 延迟秒数
     */
    void sendDelayedMessage(String message, int delaySeconds);
    
    /**
     * 发送事务消息
     * @param message 消息内容
     */
    void sendTransactionMessage(String message);
} 