package com.itheima.ds;

import java.io.IOException;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * 接收默认消息
 * 
 * @author
 * @date 2019/01/10 11:14:32
 */
public class ConsumerDirectDemo {
	
	
    public static void main(String[] args) {
        String queneName = "testQuene";
        Connection connection = null;
        Channel channel = null;
        try {

            ConnectionFactory factory = new ConnectionFactory();
              factory.setHost("47.92.251.151");
              factory.setPort(5672);
              factory.setUsername("admin");
              factory.setPassword("admin");
              factory.setVirtualHost("/");
            
            connection = factory.newConnection();
            channel = connection.createChannel();

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                        byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println(envelope.getExchange() + "," + envelope.getRoutingKey() + "," + message);
                }
            };
            // channel绑定队列，autoAck为true表示一旦收到消息则自动回复确认消息
            channel.basicConsume(queneName, true, consumer);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        
        
    }
}