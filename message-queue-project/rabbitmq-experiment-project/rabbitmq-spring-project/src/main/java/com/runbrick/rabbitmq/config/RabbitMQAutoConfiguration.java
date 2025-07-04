package com.runbrick.rabbitmq.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQAutoConfiguration {

    /**
     * 创建消息转换器
     * @return
     */
    @Bean
    public MessageConverter createMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    public static final String MSG_REGISTER_QUEUE = "msg.direct.register.queue";

    public static final String MSG_REGISTER_EXCHANGE = "msg.direct.register.exchange";

    public static final String MSG_ROUTING_KEY = "msg.key";


    @Bean
    public Queue msgQueue() {
        // 和  channel.queueDeclare(QUEUE_NAME, durable, false, false, null); 一样的
        return new Queue(MSG_REGISTER_QUEUE, true);
    }

    @Bean
    public DirectExchange directExchange() {
//       和 channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT); 一样的
        // FanoutExchange、CustomExchange、DirectExchange、FanoutExchange、HeadersExchange、TopicExchange
        return new DirectExchange(MSG_REGISTER_EXCHANGE, true, false);
    }


    @Bean
    public FanoutExchange fanoutExchange() {
//       和 channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT); 一样的
        // FanoutExchange、CustomExchange、DirectExchange、FanoutExchange、HeadersExchange、TopicExchange
        return new FanoutExchange(MSG_REGISTER_EXCHANGE, true, false);
    }


    @Bean
    public Binding msgBinding() {

        return BindingBuilder.bind(msgQueue()).to(directExchange()).with(MSG_ROUTING_KEY);
    }

}
