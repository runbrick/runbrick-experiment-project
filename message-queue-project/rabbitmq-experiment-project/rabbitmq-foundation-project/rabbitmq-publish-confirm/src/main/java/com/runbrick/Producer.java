package com.runbrick;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.*;
import java.util.function.BooleanSupplier;

public class Producer {
    private final static String QUEUE_NAME    = "publish-confirm";
    private final static int    MESSAGE_COUNT = 10;

    public static void main(String[] args) {


        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(45672);
        factory.setUsername("user");
        factory.setPassword("123qwe");
        factory.setVirtualHost("test01");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            // 准备一个倒数闩锁，计数为要发送的消息数量
            final CountDownLatch latch = new CountDownLatch(MESSAGE_COUNT);
            // 开启发布确认模式
            channel.confirmSelect();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            // ConfirmCallback ackCallback, ConfirmCallback nackCallback
            channel.addConfirmListener(
                    (deliveryTag, multiple) -> {
                        System.out.println(multiple);
                        System.out.println("ack" + deliveryTag);
                        latch.countDown();
                    },
                    (deliveryTag, multiple) -> {
                        System.out.println(multiple);
                        System.out.println("nack" + deliveryTag);
                        latch.countDown();
                    }
            );

            System.out.println("开始发送 " + MESSAGE_COUNT + " 条消息...");

            for (int i = 0; i < MESSAGE_COUNT; i++) {
                String message = "Hello World! line:" + i;
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                System.out.println(" [x] Sent '" + message + "'");
            }

            boolean allConfirmed = latch.await(60, TimeUnit.SECONDS); // 等待最多60秒

            if (allConfirmed) {
                System.out.println("所有消息都已成功收到确认！");
            } else {
                System.err.println("超时！不是所有消息都收到了确认。");
            }

        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
