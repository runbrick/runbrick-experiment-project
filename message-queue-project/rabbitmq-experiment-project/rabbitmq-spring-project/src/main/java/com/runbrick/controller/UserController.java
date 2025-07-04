package com.runbrick.controller;

import com.runbrick.rabbitmq.config.RabbitMQAutoConfiguration;
import com.runbrick.rabbitmq.producer.MsgProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    MsgProducer msgProducer;

    /**
     * 注册
     */
    @GetMapping("/register")
    public void register(@RequestParam("username") String username) {
        // ... 执行注册逻辑
        msgProducer.sendMsg(RabbitMQAutoConfiguration.MSG_REGISTER_QUEUE, username);
    }
}
