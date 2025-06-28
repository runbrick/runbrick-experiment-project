package com.runbrick.event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BootEventProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootEventProjectApplication.class, args);
    }

}
