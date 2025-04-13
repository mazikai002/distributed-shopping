package com.itheima.ds.component.mq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.UUID;

/**
 * RocketMQ生产者示例
 * 
 * @author
 */
public class ProducerDirectDemo {
    
    public static void main(String[] args) {
        // 指定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("seckill_producer");
        // 指定NameServer地址
        producer.setNamesrvAddr("127.0.0.1:9876");
        
        try {
            // 启动生产者
            producer.start();
            System.out.println("Producer started.");
            
            // 创建消息主题
            String topic = "seckill_topic";
            // 创建消息标签
            String tag = "direct";
            
            // 循环发送消息
            for (int i = 0; i < 10; i++) {
                // 创建消息内容
                String messageBody = UUID.randomUUID().toString();
                // 创建消息
                Message message = new Message(topic, tag, messageBody.getBytes());
                
                // 发送消息并获取结果
                SendResult sendResult = producer.send(message);
                System.out.printf("Message sent: %s, result: %s%n", messageBody, sendResult);
                
                // 等待1秒
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭生产者
            producer.shutdown();
            System.out.println("Producer shutdown.");
        }
    }
} 