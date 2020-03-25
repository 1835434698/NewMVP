package com.tangzy.tzymvp.aop;

import com.tangzy.tzymvp.util.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;

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

    /**
     * 凡事调用到的方法均可以捕获
     */
    @Pointcut("call(* com.tangzy.tzymvp.MainActivity.lalalal(..))")
    public void callWith(){
    }

    /**
     * 凡事调用到的方法均可以捕获
     * @param joinPoint
     */
    @Around("callWith()")
    public void AroundcallWith(ProceedingJoinPoint joinPoint){
//        Signature signature = joinPoint.getSignature();
//        String name = signature.toShortString();
        Logger.d("tangzy-time1", "args:" + Arrays.toString(joinPoint.getArgs()));
        String[] strs = new String[joinPoint.getArgs().length];
        for (int i = 0; i < joinPoint.getArgs().length; i++) {
            strs[i] = joinPoint.getArgs()[i].toString();
            Logger.d("tangzy-time1", "strs["+i+"] = " + strs[i]);
        }
        Object[] objects = new Object[1];
        for (int i=0; i<strs.length; i++){
            objects[i] = strs[i]+"_";
        }
        try {
            joinPoint.proceed(objects);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
    /**
     * 凡事调用到的方法均可以捕获
     * @param joinPoint
     */
    @AfterReturning(returning = "ret", pointcut = "callWith()")
    public void AfterReturningcallWith(ProceedingJoinPoint joinPoint, Object ret){
//        Signature signature = joinPoint.getSignature();
//        String name = signature.toShortString();
        Logger.d("tangzy-time1", "AfterReturningcallWith");
//        String[] strs = new String[joinPoint.getArgs().length];
//        for (int i = 0; i < joinPoint.getArgs().length; i++) {
//            strs[i] = joinPoint.getArgs()[i].toString();
//            Logger.d("tangzy-time1", "strs["+i+"] = " + strs[i]);
//        }
//
//        Object[] objects = new Object[1];
//        for (int i=0; i<strs.length; i++){
//            objects[i] = strs[i]+"_";
//        }
//        try {
//            joinPoint.proceed(objects);
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
    }

}
