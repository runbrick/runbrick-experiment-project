package com.runbrick;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Producer extends BaseConfig {
    // 这次不用定义队列名称了，这个教程里面需要定义 Exchanges
//    private final static String QUEUE_NAME = "hello";
    private final static String EXCHANGE_NAME = "publish-subscribe-log";


    public static void main(String[] args) {

        ConnectionFactory factory = getConnectionFactory();

        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            // 这个教程里面不需要定义 queueDeclare
//            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            // exchangeDeclare 的 type 类型有 direct 、 topic 、 headers 和 fanout 这几个
            // 这个教程里面需要使用 fanout（扇出）
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
            String message = "Hello World!";
            // 这个地方就不需要 QUEUE_NAME 了,将第一个教程的 routing_key 改成空字符串
//            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
