package com.zero.framework.rocketmqserver.send;


import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.selector.SelectMessageQueueByHash;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zero
 * @create 2018/12/29 17:31
 * @description
 **/
@Component
public class Send {

    @Autowired
    private DefaultMQProducer defaultMQProducer;

    public void send (String msg){
        List<Message> msgs = new ArrayList<>();
        for (int i = 100 ; i < 104 ; i++){
            Message message = new Message();
            message.setBody((msg + i+"").getBytes());
            message.setTopic("user-topic");
            message.setTags("first");
            msgs.add(message);
        }
        for (int i = 300 ; i < 304 ; i++){
            Message message = new Message();
            message.setBody((msg + i+"").getBytes());
            message.setTopic("user-topic");
            message.setTags("second");
            msgs.add(message);
        }
        try {
            defaultMQProducer.send(msgs);
            System.out.println("send message success! ");
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
/**
 * 注意：ConsumerGroupName需要由应用来保证唯一
 */
        DefaultMQPushConsumer pushConsumer = new DefaultMQPushConsumer("user_consumer_group6");
        pushConsumer.setNamesrvAddr("10.4.68.208:9876");
        pushConsumer.setInstanceName("AA");
        pushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        try {
            /**
             * 订阅指定topic下tags分别等于TagA或TagC或TagD
             * 两个参数：第一个参数是topic第二个参数是tags
             */
            pushConsumer.subscribe("orgnization", "change");
            /**
             * 订阅指定topic下所有消息<br>
             * 注意：一个consumer对象可以订阅多个topic
             */
            //pushConsumer.subscribe("TopicTest2", "*");
            pushConsumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                                ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    System.out.println(Thread.currentThread().getName() + " Receive New Messages: " + msgs.size());
                    MessageExt messageExt = msgs.get(0);
                    if("orgnization".equals(messageExt.getTopic())){
                        // 执行TopicTest1的消费逻辑
                        if (messageExt.getTags() != null && messageExt.getTags().equals("change")) {
//                            if (messageExt.getReconsumeTimes() == 2){
//                                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//                            }
//                            try {
//                                System.err.println("retry --> "+messageExt.getReconsumeTimes()+
//                                        "position"+new String(messageExt.getBody()));
//                                System.out.println(1/0);
//                            }catch (Exception e){
//                                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
//                            }
                            // 执行TagA的消费
                            System.out.println("first---> "+new String(messageExt.getBody()));
                        }else if (messageExt.getTags() != null && messageExt.getTags().equals("second")){
                            System.out.println("second---> "+ new String(messageExt.getBody()));
                        }
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        /**
         * Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
         */
        try {
            pushConsumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        System.out.println("Consumer Started.");
    }

}
