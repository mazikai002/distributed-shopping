package com.itheima.ds.service.impl;

import com.itheima.ds.common.exception.GlobalException;
import com.itheima.ds.service.MQService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 消息队列服务实现类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MQServiceImpl implements MQService {

    private final DefaultMQProducer producer;
    
    @Value("${rocketmq.topic.seckill:seckill_topic}")
    private String seckillTopic;
    
    @Value("${rocketmq.topic.order:order_topic}")
    private String orderTopic;
    
    @Value("${rocketmq.consumer.group:seckill_consumer}")
    private String consumerGroup;
    
    // 模拟消息队列，实际项目中使用RocketMQ等
    private static final BlockingQueue<String> MESSAGE_QUEUE = new ArrayBlockingQueue<>(100);

    @Override
    public void sendMessage(String message) {
        try {
            // 同步发送消息
            Message msg = new Message(seckillTopic, message.getBytes(StandardCharsets.UTF_8));
            SendResult sendResult = producer.send(msg);
            log.info("消息发送成功，消息ID: {}", sendResult.getMsgId());
        } catch (Exception e) {
            log.error("消息发送失败", e);
            throw new GlobalException("消息发送失败");
        }
    }

    @Override
    public String receiveMessage() {
        try {
            // 从队列中获取消息，实际项目中通过RocketMQ消费者消费
            return MESSAGE_QUEUE.take();
        } catch (InterruptedException e) {
            log.error("接收消息失败", e);
            Thread.currentThread().interrupt();
            throw new GlobalException("接收消息失败");
        }
    }

    @Override
    public void sendAsyncMessage(String message) {
        try {
            // 异步发送消息
            Message msg = new Message(seckillTopic, message.getBytes(StandardCharsets.UTF_8));
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    log.info("异步消息发送成功，消息ID: {}", sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable throwable) {
                    log.error("异步消息发送失败", throwable);
                }
            });
        } catch (Exception e) {
            log.error("异步消息发送失败", e);
            throw new GlobalException("异步消息发送失败");
        }
    }

    @Override
    public void sendDelayedMessage(String message, int delaySeconds) {
        try {
            // 延迟等级对应关系：
            // 1s, 5s, 10s, 30s, 1m, 2m, 3m, 4m, 5m, 6m, 7m, 8m, 9m, 10m, 20m, 30m, 1h, 2h
            // 延迟等级从1开始，这里默认使用1（1秒）
            int delayLevel = 1;
            
            // 根据传入的延迟秒数确定延迟等级
            if (delaySeconds >= 30 * 60) {
                delayLevel = 16; // 30分钟
            } else if (delaySeconds >= 10 * 60) {
                delayLevel = 14; // 10分钟
            } else if (delaySeconds >= 5 * 60) {
                delayLevel = 13; // 5分钟
            } else if (delaySeconds >= 1 * 60) {
                delayLevel = 5;  // 1分钟
            } else if (delaySeconds >= 30) {
                delayLevel = 4;  // 30秒
            } else if (delaySeconds >= 10) {
                delayLevel = 3;  // 10秒
            } else if (delaySeconds >= 5) {
                delayLevel = 2;  // 5秒
            }
            
            // 发送延迟消息
            Message msg = new Message(orderTopic, message.getBytes(StandardCharsets.UTF_8));
            msg.setDelayTimeLevel(delayLevel);
            SendResult sendResult = producer.send(msg);
            
            log.info("延迟消息发送成功，延迟等级: {}, 消息ID: {}", delayLevel, sendResult.getMsgId());
        } catch (Exception e) {
            log.error("延迟消息发送失败", e);
            throw new GlobalException("延迟消息发送失败");
        }
    }

    @Override
    public void sendTransactionMessage(String message) {
        try {
            String transactionId = UUID.randomUUID().toString();
            // 发送事务消息
            Message msg = new Message(seckillTopic, message.getBytes(StandardCharsets.UTF_8));
            msg.putUserProperty("TRANSACTION_ID", transactionId);
            
            // 注意：这里需要实现 TransactionListener 接口
            // 由于简化处理，这里直接发送普通消息
            SendResult sendResult = producer.send(msg);
            log.info("事务消息发送成功，消息ID: {}", sendResult.getMsgId());
        } catch (Exception e) {
            log.error("事务消息发送失败", e);
            throw new GlobalException("事务消息发送失败");
        }
    }
} 