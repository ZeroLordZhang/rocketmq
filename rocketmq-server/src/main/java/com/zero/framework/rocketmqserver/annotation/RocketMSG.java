package com.zero.framework.rocketmqserver.annotation;

import org.apache.rocketmq.client.producer.DefaultMQProducer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Zero
 * @create 2019/1/9 13:30
 * @description
 **/
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface RocketMSG {
    String value() default "";
    String description() default "";
}
