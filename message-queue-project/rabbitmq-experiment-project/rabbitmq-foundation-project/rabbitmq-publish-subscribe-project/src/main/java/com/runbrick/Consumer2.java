package com.runbrick;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Consumer2 extends BaseConfig {
    //    private final static String QUEUE_NAME = "hello";
    private final static String EXCHANGE_NAME = "publish-subscribe-log";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = getConnectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

//        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        // 创建一个随机的队列名称
        // 生成队列的名称类似 amq.gen-MqMWK6-HQ7yZb0vhLGcMxQ 这种
        String QUEUE_NAME = channel.queueDeclare().getQueue();
        // 需要绑定这个生成的 QUEUE_NAME 到 EXCHANGE 上
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");
        channel.basicConsume(QUEUE_NAME, true, (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
        }, consumerTag -> {});
    }
}
