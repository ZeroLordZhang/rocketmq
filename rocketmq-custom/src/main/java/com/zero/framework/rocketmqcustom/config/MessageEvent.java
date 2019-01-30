package com.zero.framework.rocketmqcustom.config;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Zero
 * @create 2019/1/2 16:12
 * @description 事件监听器
 **/
public class MessageEvent extends ApplicationEvent {

    private DefaultMQPushConsumer defaultMQPushConsumer;
    private List<MessageExt> msgs;
    public MessageEvent(List<MessageExt> msgs,DefaultMQPushConsumer defaultMQPushConsumer) {
        super(msgs);
        this.defaultMQPushConsumer = defaultMQPushConsumer;
        this.msgs = msgs;
    }

    public DefaultMQPushConsumer getDefaultMQPushConsumer() {
        return defaultMQPushConsumer;
    }

    public void setDefaultMQPushConsumer(DefaultMQPushConsumer defaultMQPushConsumer) {
        this.defaultMQPushConsumer = defaultMQPushConsumer;
    }

    public List<MessageExt> getMsgs() {
        return msgs;
    }

    public void setMsgs(List<MessageExt> msgs) {
        this.msgs = msgs;
    }
}
