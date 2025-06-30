package com.runbrick;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 这个地方的代码和 Consumer2 是一样的，只需要研究 Consumer 即可
 */
public class Consumer2 extends BaseConfig {
    private final static String QUEUE_NAME = "work-queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = getConnectionFactory();

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        boolean durable = true;
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
        // 这个配置，保证一个消费者一次只能消费一个消息
        channel.basicQos(1);

        // autoAck = true 为自动确认，false 为手动确认
//        Boolean autoAck = true;
//        channel.basicConsume(QUEUE_NAME, autoAck, getDeliverCallbackAutoAck(), consumerTag -> {});

        Boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, getDeliverCallback(channel), consumerTag -> {});
    }
}
