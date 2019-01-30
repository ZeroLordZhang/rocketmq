package com.zero.framework.rocketmqserver.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

/**
 * @author Zero
 * @create 2019/1/9 13:19
 * @description
 **/
public abstract class AbstractAroundAopHandler {

    protected abstract void aroundPointCut();

    @Around("aroundPointCut()")
    public final Object doAround(ProceedingJoinPoint jp) throws Exception {
        Object res = null;
        try {
            res = doAroundHandle(jp);
        } catch (Throwable e) {
            if (e instanceof Exception) {
                throw (Exception) e;
            } else {
                throw new Exception(e);
            }
        }
        return res;
    }

    /**
     * 做具体业务操作
     *
     * @param jp
     * @return
     */
    protected abstract Object doAroundHandle(ProceedingJoinPoint jp) throws Throwable;
}
