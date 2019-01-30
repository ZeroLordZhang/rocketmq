package com.zero.framework.rocketmqcustom.testa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author Zero
 * @create 2019/1/3 15:39
 * @description
 **/
@Component
public class TestListener {

    @Autowired
    private Test test;

    @EventListener
    public void test (TestEvent testEvent){

        System.err.println(test.getName()+"===========  "+testEvent.getAddr());
    }
}
