package com.runbrick.rabbitmq.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// 标记为一个通用的组件
@Component
public class MsgProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(String routingKey, String msg) {
        rabbitTemplate.convertAndSend(routingKey, msg);
    }
}
