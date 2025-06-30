package com.runbrick;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 一些一样的代码统一挪到了 BaseConfig 类中，为了避免代码重复，简洁好看
 */
public class Consumer extends BaseConfig {
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
