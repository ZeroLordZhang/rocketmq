package com.zero.framework.rocketmqcustom.listener;

import com.zero.framework.rocketmqcustom.config.MessageEvent;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Zero
 * @create 2019/1/2 16:19
 * @description
 **/
@Component
public class RocketMQListener {

    @EventListener(condition = "#messageEvent.msgs[0].topic=='user-topic'&&#messageEvent.msgs[0].tags=='sencond'")
    public void FirstListener (MessageEvent messageEvent){
        List<MessageExt> msgs = messageEvent.getMsgs();

        for (MessageExt msg : msgs){
            System.out.println("logic-------------> "+new String(msg.getBody()));
        }
    }
}
