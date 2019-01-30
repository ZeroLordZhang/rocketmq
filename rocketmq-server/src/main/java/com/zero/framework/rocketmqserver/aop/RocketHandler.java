package com.zero.framework.rocketmqserver.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zero.framework.rocketmqserver.annotation.RocketMSG;
import com.zero.framework.rocketmqserver.send.Send;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author Zero
 * @create 2019/1/9 13:27
 * @description
 **/
@Component
@Aspect
public class RocketHandler extends AbstractAroundAopHandler {

    @Autowired
    private Send send;

    @Pointcut("execution(* com.zero.framework.rocketmqserver.controller.*Controller*.*(..))")
    @Override
    protected void aroundPointCut() {

    }

    @Override
    protected Object doAroundHandle(ProceedingJoinPoint jp) throws Throwable {
        MethodSignature ms = (MethodSignature)jp.getSignature();
        Method method = jp.getTarget().getClass().getMethod(ms.getName(), ms.getParameterTypes());
        Object[] args = jp.getArgs();
        Object res = jp.proceed(args);
        RocketMSG rocketMSG = method.getAnnotation(RocketMSG.class);
        if (rocketMSG != null){
            StringBuffer sb = new StringBuffer();
            for (int i = 0;i<args.length;i++){
                if (!(args[i] instanceof String)){
                    String a = JSON.toJSONString(args[i]);
                    sb.append(a);
                }
            }
            System.out.println(rocketMSG.description() +" "+sb.toString());
//            send.send("I'm Message");
        }
        return res;
    }
}
