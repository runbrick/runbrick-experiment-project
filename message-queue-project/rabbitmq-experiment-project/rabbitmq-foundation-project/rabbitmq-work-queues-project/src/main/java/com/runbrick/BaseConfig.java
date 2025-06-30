package com.runbrick;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

public class BaseConfig {

    public static ConnectionFactory getConnectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(45672);
        factory.setUsername("user");
        factory.setPassword("123qwe");
        factory.setVirtualHost("test01");
        return factory;
    }


    /**
     * 模拟延时处理任务
     * @param task
     * @throws InterruptedException
     */
    public static void doWork(String task) throws InterruptedException {
        for (char ch : task.toCharArray()) {
            if (ch == '.') Thread.sleep(1000);
        }
    }

    public static DeliverCallback getDeliverCallbackAutoAck() {
        return (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
            try {
                doWork(message);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                System.out.println(" [x] Done");
            }
        };
    }

    public static DeliverCallback getDeliverCallback(Channel channel) {
        return (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
            try {
                doWork(message);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                System.out.println(" [x] Done");
                // 这里需要确认消息，否则消息会重复消费
                // 这里不需要批量确认，因为一个消费者一次只能消费一个消息
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }

        };
    }

}
