package com.itheima.ds.config;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

/**
 * RocketMQ配置类
 */
@Configuration
public class RocketMQConfig {
    
    @Value("${rocketmq.name-server:127.0.0.1:9876}")
    private String nameServer;
    
    @Value("${rocketmq.producer.group:seckill_producer}")
    private String producerGroup;
    
    @Bean  // 生产mq_producer
    public DefaultMQProducer defaultMQProducer() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer();
        producer.setNamesrvAddr(nameServer);
        producer.setProducerGroup(producerGroup);
        producer.start();
        return producer;
    }
    
    @Bean  // 消费mq_consumer
    public DefaultMQPushConsumer defaultMQPushConsumer() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
        consumer.setNamesrvAddr(nameServer);
        return consumer;
    }

    /**
     * 事务消息监听器
     */
    @Bean
    public TransactionListener transactionListener() {
        return new TransactionListener() {
            // 用于存储事务消息的状态
            private final ConcurrentHashMap<String, LocalTransactionState> localTrans = new ConcurrentHashMap<>();
            
            /**
             * 执行本地事务
             */
            @Override
            public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                // 从消息中获取事务ID
                String transactionId = msg.getProperty("TRANSACTION_ID");
                
                try {
                    // 执行本地事务（这里应该是具体的业务逻辑）
                    // 比如创建订单、减库存等操作
                    
                    // 模拟事务执行成功
                    localTrans.put(transactionId, LocalTransactionState.COMMIT_MESSAGE);
                    return LocalTransactionState.COMMIT_MESSAGE;
                } catch (Exception e) {
                    // 事务执行失败，记录回滚状态
                    localTrans.put(transactionId, LocalTransactionState.ROLLBACK_MESSAGE);
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }
            }
            
            /**
             * 检查本地事务状态
             * 用于回查本地事务状态
             */
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                // 从消息中获取事务ID
                String transactionId = msg.getProperty("TRANSACTION_ID");
                
                // 查询本地事务状态
                LocalTransactionState state = localTrans.get(transactionId);
                
                // 如果找不到状态，或者状态为未知，则需要回查数据库确认最终状态
                if (state == null || state == LocalTransactionState.UNKNOW) {
                    // 这里应该查询数据库，确认事务状态
                    // 如果事务已提交，返回COMMIT_MESSAGE
                    // 如果事务已回滚，返回ROLLBACK_MESSAGE
                    // 如果事务状态未知，返回UNKNOW
                    
                    // 模拟查询数据库后确认事务已提交
                    return LocalTransactionState.COMMIT_MESSAGE;
                }
                
                return state;
            }
        };
    }
}