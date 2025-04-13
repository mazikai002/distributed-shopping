package com.itheima.ds.component.mq.rocketmq.message;

import java.io.Serializable;

/**
 * RocketMQ基础消息类
 */
public class BaseMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String messageId;
    private long timestamp;
    
    public BaseMessage() {
        this.timestamp = System.currentTimeMillis();
    }
    
    public String getMessageId() {
        return messageId;
    }
    
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}