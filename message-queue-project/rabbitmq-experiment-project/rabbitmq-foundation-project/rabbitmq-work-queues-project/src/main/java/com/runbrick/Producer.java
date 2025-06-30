package com.runbrick;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer extends BaseConfig {
    private final static String QUEUE_NAME = "work-queue";


    public static void main(String[] args) {

        ConnectionFactory factory = getConnectionFactory();

        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {

            // 这个里给队列声明一个持久化队列确保队列在 RabbitMQ 节点重启后仍然存在
            // 当 RabbitMQ 退出或崩溃时，除非明确设置，否则它将遗忘队列和消息。要确保消息不丢失，需要满足两个条件：必须将队列和消息都标记为持久化。
            // 因为你要是跟着教程走，会修改队列的配置。
            // 但你之前已经有相同的队列了这时候不会修改成功的，那么你需要先删除这个队列或者修改 QUEUE_NAME 为新的名称
            // 不然就会报错  inequivalent arg 'durable' for queue 'work-queue' in vhost 'test01': received 'true' but current is 'false',
            // 记得这里改了之后也要修改 消费者的两个类：Consumer、Consumer2
            boolean durable = true;
            channel.queueDeclare(QUEUE_NAME, durable, false, false, null);

            for (int i = 0; i < 10; i++) {
                int poll = (int) Math.floor(Math.random() * 10);
                String repeat = ".".repeat(poll);

                String message = String.format("Hello World %d%s", i, repeat);
                // 如果 durable = true 这里的 basicPublish 的 props 参数也要配置一下 MessageProperties.PERSISTENT_TEXT_PLAIN
                // 得告诉一下 RabbitMQ 这是一个持久化消息
                // MessageProperties.PERSISTENT_TEXT_PLAIN 可以自己配置，这配置里面最主要的是 deliveryMode = 2
                channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
                System.out.println(" [x] Sent '" + message + "'");
            }
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
