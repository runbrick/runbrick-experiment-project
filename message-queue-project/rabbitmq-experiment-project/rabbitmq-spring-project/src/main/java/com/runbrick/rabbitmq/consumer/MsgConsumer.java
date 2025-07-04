package com.runbrick.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import com.runbrick.rabbitmq.config.RabbitMQAutoConfiguration;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MsgConsumer {

    @RabbitListener(queues = RabbitMQAutoConfiguration.MSG_REGISTER_QUEUE)
    public void consume(Message message, Channel channel) {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            // 业务逻辑
            System.out.println("发送短信给：" + new String(message.getBody()) + "用户的手机");
            // ...
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            try {
                // --- 业务失败，手动NACK ---
                // deliveryTag: 消息标识
                // multiple: 是否批量
                // requeue: 是否重新放回队列。
                //          true: 消息会重新入队，可能导致死循环（毒丸消息）。
                //          false: 消息被拒绝，根据配置进入死信队列或被丢弃。
                //          *** 强烈推荐设置为 false ***
                channel.basicNack(deliveryTag, false, false);
            } catch (IOException ioException) {
                // 如果连NACK都失败，通常是连接问题，Spring AMQP会处理重连
            }
            throw new AmqpRejectAndDontRequeueException("业务处理失败，进入重试", e);

        }
    }
}
