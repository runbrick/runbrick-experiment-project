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
}
