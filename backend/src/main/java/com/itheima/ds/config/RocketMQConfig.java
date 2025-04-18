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
import org.springframework.context.annotation.Primary;

import javax.annotation.PreDestroy;
import java.util.concurrent.ConcurrentHashMap;

/**
 * RocketMQ 配置类
 */
@Configuration
public class RocketMQConfig {

    @Value("${rocketmq.name-server:localhost:9876}")
    private String nameServer;

    @Value("${rocketmq.producer.group:seckill_producer}")
    private String producerGroup;

    private DefaultMQProducer producer;
    private DefaultMQPushConsumer consumer;

    /**
     * 配置 DefaultMQProducer
     */
    @Bean
    public DefaultMQProducer defaultMQProducer() throws Exception {
        producer = new DefaultMQProducer();
        producer.setProducerGroup(producerGroup);
        producer.setNamesrvAddr(nameServer);
        producer.setVipChannelEnabled(false);
        producer.setRetryTimesWhenSendAsyncFailed(3);
        producer.setRetryTimesWhenSendFailed(3);
        producer.setSendMsgTimeout(3000);
        producer.setInstanceName(producerGroup + "_" + System.currentTimeMillis());
        return producer;
    }

    /**
     * 配置 RocketMQTemplate
     */
    @Bean
    @Primary
    public RocketMQTemplate rocketMQTemplate() {
        RocketMQTemplate rocketMQTemplate = new RocketMQTemplate();
        rocketMQTemplate.setProducer(producer);
        return rocketMQTemplate;
    }

    /**
     * 配置消费者
     */
    @Bean
    public DefaultMQPushConsumer defaultMQPushConsumer() {
        consumer = new DefaultMQPushConsumer();
        consumer.setNamesrvAddr(nameServer);
        consumer.setInstanceName("consumer_" + System.currentTimeMillis());
        return consumer;
    }

    /**
     * 事务消息监听器
     */
    @Bean
    public TransactionListener transactionListener() {
        return new TransactionListener() {
            private final ConcurrentHashMap<String, LocalTransactionState> localTrans = new ConcurrentHashMap<>();
            
            @Override
            public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                String transactionId = msg.getProperty("TRANSACTION_ID");
                try {
                    localTrans.put(transactionId, LocalTransactionState.COMMIT_MESSAGE);
                    return LocalTransactionState.COMMIT_MESSAGE;
                } catch (Exception e) {
                    localTrans.put(transactionId, LocalTransactionState.ROLLBACK_MESSAGE);
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }
            }
            
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                String transactionId = msg.getProperty("TRANSACTION_ID");
                LocalTransactionState state = localTrans.get(transactionId);
                return state != null ? state : LocalTransactionState.COMMIT_MESSAGE;
            }
        };
    }

    @PreDestroy
    public void destroy() {
        if (producer != null) {
            producer.shutdown();
        }
        if (consumer != null) {
            consumer.shutdown();
        }
    }
}