package pers.yhf.seckill.component.mq.rocketmq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

/**
 * RocketMQ消费者基类
 */
@Component
public abstract class RocketMQConsumer {
    
    @Autowired
    private DefaultMQPushConsumer consumer;
    
    /**
     * 消息消费处理
     * @param message 消息内容
     * @return 是否消费成功
     */
    protected abstract boolean handleMessage(String message);
    
    /**
     * 启动消费者
     * @param topic 主题
     * @param consumerGroup 消费者组
     * @throws Exception 启动异常
     */
    @PostConstruct
    protected void startConsumer(String topic, String consumerGroup) throws Exception {
        consumer.setConsumerGroup(consumerGroup);
        consumer.subscribe(topic, "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt msg : msgs) {
                    String messageBody = new String(msg.getBody());
                    try {
                        if (handleMessage(messageBody)) {
                            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                        }
                    } catch (Exception e) {
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
    }
    
    /**
     * 停止消费者
     */
    @PreDestroy
    protected void stopConsumer() {
        if (consumer != null) {
            consumer.shutdown();
        }
    }
}