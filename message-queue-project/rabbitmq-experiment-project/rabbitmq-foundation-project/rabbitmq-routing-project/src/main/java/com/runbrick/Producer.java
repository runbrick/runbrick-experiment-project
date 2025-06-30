package com.runbrick;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.TimeoutException;

public class Producer extends BaseConfig {
    private final static String EXCHANGE_NAME = "routing-log";


    public static void main(String[] args) {

        ConnectionFactory factory = getConnectionFactory();

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            // exchangeDeclare 的 type 类型有 direct 、 topic 、 headers 和 fanout 这几个
            // 这个教程里面需要使用 direct（直连）
            // 需要对 routing_key 进行匹配分别不同的队列进行匹配
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);


            String[] routingKeys = new String[]{"error", "info", "warning"};
            for (int i = 0; i < 10; i++) {
                String routingKey = routingKeys[(int) (Math.random() * routingKeys.length)];
                String message = String.format("[%s],%s", routingKey, new Date());
                channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes(StandardCharsets.UTF_8));
                System.out.println(" [x] Sent '" + message + "'");
            }
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
