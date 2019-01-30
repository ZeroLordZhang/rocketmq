package com.zero.framework.rocketmqserver.controller;

import com.zero.framework.rocketmqserver.annotation.RocketMSG;
import com.zero.framework.rocketmqserver.bean.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zero
 * @create 2019/1/9 13:42
 * @description
 **/
@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping("/t/a")
    @RocketMSG(value = "add",description = "删除组织机构")
    public void testA (String msg, String num, @RequestBody User user){
        System.out.println("===============Success============= " + msg);
    }
}
