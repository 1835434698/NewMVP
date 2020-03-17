package com.tangzy.tzymvp.aop;

import com.tangzy.tzymvp.util.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class PerformanceApp {

    /**
     * 凡事调用到的方法均可以捕获
     * @param joinPoint
     */
    @Around("call(* com.tangzy.tzymvp.MainActivity.**(..))")
    public void getTime(ProceedingJoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        String name = signature.toShortString();
        long time = System.currentTimeMillis();
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        Logger.d("tangzy-time", name+" cost "+(System.currentTimeMillis()-time));

    }

}
