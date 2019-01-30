package com.zero.framework.rocketmqcustom.testa;

import com.zero.framework.rocketmqcustom.RocketmqCustomApplication;
import com.zero.framework.rocketmqcustom.config.MessageEvent;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @author Zero
 * @create 2019/1/3 16:25
 * @description
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RocketmqCustomApplication.class)
public class TestEventTest {


    @Autowired
    private ApplicationContext applicationEventPublisher = null;
    @Autowired
    private com.zero.framework.rocketmqcustom.testa.Test test;

    @Test
    public void test (){
        applicationEventPublisher.publishEvent(new TestEvent("成都"));
        System.err.println("test ====" +test.getName());
    }
}