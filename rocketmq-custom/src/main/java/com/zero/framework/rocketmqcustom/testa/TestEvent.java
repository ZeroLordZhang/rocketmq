package com.zero.framework.rocketmqcustom.testa;

import org.springframework.context.ApplicationEvent;

/**
 * @author Zero
 * @create 2019/1/3 15:34
 * @description
 **/
public class TestEvent extends ApplicationEvent {
    private String addr;

    public TestEvent(String addr) {
        super(addr);
        this.addr = addr;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
