package com.zero.framework.rocketmqserver.config;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Zero
 * @create 2019/1/2 14:34
 * @description
 **/
@Configuration
@EnableConfigurationProperties(RocketMQProperties.class)
//@ConditionalOnProperty(prefix = "zero.rocketmq", value= "namesrvAddr")
public class RocketMQProducerConfig {

    @Autowired
    private RocketMQProperties rocketMQProperties;

    @Bean
//    @ConditionalOnProperty(prefix = "zero.rocketmq",value="producerGroupName")
    public DefaultMQProducer defaultMQProducer (){
        DefaultMQProducer producer = new DefaultMQProducer(rocketMQProperties.getProducerGroupName());
        //如果需要同一个jvm中不同的producer往不同的mq集群发送消息，需要设置不同的instanceName
        producer.setInstanceName(rocketMQProperties.getProducerInstanceName());
        producer.setNamesrvAddr(rocketMQProperties.getNamesrvAddr());
        //如果发送消息失败，设置重试次数，默认为2次
        producer.setRetryTimesWhenSendFailed(3);
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        return producer;
    }


}
