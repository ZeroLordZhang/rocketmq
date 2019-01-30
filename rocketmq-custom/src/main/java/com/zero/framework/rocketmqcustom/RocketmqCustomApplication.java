package com.zero.framework.rocketmqcustom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication

@ComponentScan(basePackages = "com.zero.framework.*")
public class RocketmqCustomApplication {

    public static void main(String[] args) {
        SpringApplication.run(RocketmqCustomApplication.class, args);
    }

}

