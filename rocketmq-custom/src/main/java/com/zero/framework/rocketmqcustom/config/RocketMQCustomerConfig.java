package com.zero.framework.rocketmqcustom.config;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author Zero
 * @create 2019/1/2 15:24
 * @description
 **/
@Configuration
@EnableConfigurationProperties(RocketMQProperties.class)
//@ConditionalOnProperty(prefix = "zero.rocketmq",value= "namesrvAddr")
public class RocketMQCustomerConfig {

    @Autowired
    private RocketMQProperties rocketMQProperties;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Bean
//    @ConditionalOnProperty(prefix = "zero.rocketmq",value = "consumerGroupName")
    public DefaultMQPushConsumer defaultMQPushConsumer (){
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(rocketMQProperties.getConsumerGroupName());
        consumer.setNamesrvAddr(rocketMQProperties.getNamesrvAddr());
        consumer.setInstanceName(rocketMQProperties.getConsumerInstanceName());
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //判断是否是广播模式
        if (rocketMQProperties.isConsumerBroadcasting()) {
            consumer.setMessageModel(MessageModel.BROADCASTING);
        }
        //设置一次消费消息的条数，默认为1条
        consumer.setConsumeMessageBatchMaxSize(rocketMQProperties.getConsumerBatchMaxSize());
        //获取topic和tag
        List<String> subscribeList = rocketMQProperties.getSubscribe();
        for (String sunscribe : subscribeList) {
            try {
                consumer.subscribe(sunscribe.split(":")[0], sunscribe.split(":")[1]);
            } catch (MQClientException e) {
                e.printStackTrace();
            }
        }
        // 顺序消费
        if (rocketMQProperties.isEnableOrderConsumer()){
            consumer.registerMessageListener(new MessageListenerOrderly() {
                @Override
                public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                    if (CollectionUtils.isEmpty(list)){
                        return ConsumeOrderlyStatus.SUCCESS;
                    }
                    consumeOrderlyContext.setAutoCommit(true);
                    System.err.println("Sizeeeeeeeee-------> "+list.size());
                    for (MessageExt msg : list){
                        System.err.println("print-----> "+new String(msg.getBody()));
                    }
                    applicationEventPublisher.publishEvent(new MessageEvent(list,consumer));
                    return ConsumeOrderlyStatus.SUCCESS;
                }
            });
        }else {
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    if (CollectionUtils.isEmpty(list)){
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                    for (MessageExt msg : list){
                        System.err.println("print-----> "+msg.toString());
                    }
                    applicationEventPublisher.publishEvent(new MessageEvent(list,consumer));
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
        }

        try {
            consumer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return consumer;
    }

}
