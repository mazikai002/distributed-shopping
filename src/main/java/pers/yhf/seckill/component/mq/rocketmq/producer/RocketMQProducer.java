package pers.yhf.seckill.component.mq.rocketmq.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.yhf.seckill.component.mq.rocketmq.message.BaseMessage;

import com.alibaba.fastjson.JSON;

/**
 * RocketMQ生产者基类
 */
@Component
public class RocketMQProducer {
    
    @Autowired
    private DefaultMQProducer producer;
    
    /**
     * 发送消息
     * @param topic 主题
     * @param message 消息内容
     * @return 发送结果
     * @throws Exception 发送异常
     */
    public SendResult sendMessage(String topic, BaseMessage message) throws Exception {
        Message msg = new Message(topic, JSON.toJSONString(message).getBytes());
        return producer.send(msg);
    }
    
    /**
     * 发送延迟消息
     * @param topic 主题
     * @param message 消息内容
     * @param delayTimeLevel 延迟级别
     * @return 发送结果
     * @throws Exception 发送异常
     */
    public SendResult sendDelayMessage(String topic, BaseMessage message, int delayTimeLevel) throws Exception {
        Message msg = new Message(topic, JSON.toJSONString(message).getBytes());
        msg.setDelayTimeLevel(delayTimeLevel);
        return producer.send(msg);
    }
}