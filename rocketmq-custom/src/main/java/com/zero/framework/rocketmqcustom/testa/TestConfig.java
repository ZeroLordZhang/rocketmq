package com.zero.framework.rocketmqcustom.testa;

import com.zero.framework.rocketmqcustom.config.RocketMQProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Zero
 * @create 2019/1/3 15:30
 * @description
 **/
@Configuration
public class TestConfig {

    @Autowired
    private RocketMQProperties rocketMQProperties;


    @Bean
    public Test test (){
        Test test = new Test();
        if (rocketMQProperties.getNamesrvAddr().equals("192.168.10.132:9876")){
            test.setName(rocketMQProperties.getNamesrvAddr());
            System.out.println("Success!");
        }
        return test;
    }
}
