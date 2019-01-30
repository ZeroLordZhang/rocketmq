package com.zero.framework.rocketmqserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class RocketmqServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RocketmqServerApplication.class, args);
    }

}

