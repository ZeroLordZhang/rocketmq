package com.zero.framework.rocketmqserver.send;

import com.zero.framework.rocketmqserver.RocketmqServerApplication;
import com.zero.framework.rocketmqserver.config.RocketMQProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Zero
 * @create 2019/1/2 16:50
 * @description
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RocketmqServerApplication.class)
public class SendTest {

    @Autowired
    private Send send;

    @Autowired
    private RocketMQProperties rocketMQProperties;

    @Test
    public void test (){

            send.send("I'm message -->");
//            System.out.println(rocketMQProperties.getNamesrvAddr());
//            System.out.println(rocketMQProperties.getSubscribe().get(1));
    }
}