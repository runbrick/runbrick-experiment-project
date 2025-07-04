package com.runbrick;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitmqSpringProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqSpringProjectApplication.class, args);
    }

}
