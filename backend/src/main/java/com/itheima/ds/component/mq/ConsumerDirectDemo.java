package com.itheima.ds.component.mq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * RocketMQ消费者示例
 * 
 * @author
 */
public class ConsumerDirectDemo {
    
    public static void main(String[] args) {
        try {
            // 指定消费者组名
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("seckill_consumer");
            // 指定NameServer地址
            consumer.setNamesrvAddr("127.0.0.1:9876");
            
            // 订阅主题和标签
            consumer.subscribe("seckill_topic", "direct");
            
            // 设置消费模式为广播模式
            consumer.setMessageModel(MessageModel.BROADCASTING);
            
            // 注册回调函数，处理消息
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    for (MessageExt msg : msgs) {
                        String topic = msg.getTopic();
                        String tags = msg.getTags();
                        String body = new String(msg.getBody());
                        
                        System.out.printf("收到消息: Topic=%s, Tags=%s, Message=%s%n", topic, tags, body);
                    }
                    // 消费成功
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            
            // 启动消费者
            consumer.start();
            System.out.println("Consumer started.");
            
            // 让消费者运行
            System.out.println("Press any key to exit...");
            System.in.read();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 