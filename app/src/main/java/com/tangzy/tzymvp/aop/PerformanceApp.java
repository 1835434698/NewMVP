//package com.tangzy.tzymvp.aop;
//
//import com.tangzy.tzymvp.util.Logger;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.Signature;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.aspectj.lang.reflect.SourceLocation;
//
//import java.util.Arrays;
//
//@Aspect
//public class PerformanceApp {
//
//    /**
//     * 凡事调用到的方法均可以捕获
//     * @param joinPoint
//     */
//    @Around("call(* com.tangzy.tzymvp.MainActivity.**(..))")
//    public void getTime(ProceedingJoinPoint joinPoint){
//        Signature signature = joinPoint.getSignature();
//        String name = signature.toShortString();
//        long time = System.currentTimeMillis();
//        try {
//            joinPoint.proceed();
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//        Logger.d("tangzy-time", name+" cost "+(System.currentTimeMillis()-time));
//
//    }
//
//    /**
//     * 凡事调用到的方法均可以捕获
//     */
//    @Pointcut("call(* com.tangzy.tzymvp.MainActivity.lalalal(..))")
//    public void callWith(){
//    }
//
//    /**
//     * 凡事调用到的方法均可以捕获
//     * @param joinPoint
//     */
//    @Around("callWith()")
//    public void AroundcallWith(ProceedingJoinPoint joinPoint){
////        Signature signature = joinPoint.getSignature();
////        String name = signature.toShortString();
//        Logger.d("tangzy-time1", "args:" + Arrays.toString(joinPoint.getArgs()));
//        String[] strs = new String[joinPoint.getArgs().length];
//        for (int i = 0; i < joinPoint.getArgs().length; i++) {
//            strs[i] = joinPoint.getArgs()[i].toString();
//            Logger.d("tangzy-time1", "strs["+i+"] = " + strs[i]);
//        }
//        Object[] objects = new Object[1];
//        for (int i=0; i<strs.length; i++){
//            objects[i] = strs[i]+"_";
//        }
//        try {
//            joinPoint.proceed(objects);
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//    }
//    /**
//     * 凡事调用到的方法均可以捕获
//     * @param joinPoint
//     */
//    @AfterReturning(returning = "ret", pointcut = "callWith()")
//    public void AfterReturningcallWith(ProceedingJoinPoint joinPoint, Object ret){
////        Signature signature = joinPoint.getSignature();
////        String name = signature.toShortString();
//        Logger.d("tangzy-time1", "AfterReturningcallWith");
////        String[] strs = new String[joinPoint.getArgs().length];
////        for (int i = 0; i < joinPoint.getArgs().length; i++) {
////            strs[i] = joinPoint.getArgs()[i].toString();
////            Logger.d("tangzy-time1", "strs["+i+"] = " + strs[i]);
////        }
////
////        Object[] objects = new Object[1];
////        for (int i=0; i<strs.length; i++){
////            objects[i] = strs[i]+"_";
////        }
////        try {
////            joinPoint.proceed(objects);
////        } catch (Throwable throwable) {
////            throwable.printStackTrace();
////        }
//    }
//
//
//    /**
//     * 凡事调用到的方法均可以捕获
//     */
//    @Around("call(* android.app.Activity.onResume(..))")
//    public void onResume(ProceedingJoinPoint joinPoint){
//        Signature signature = joinPoint.getSignature();
//        String name = signature.toShortString();
//
//        Logger.d("tangzy-time", name+" threadSize = "+Thread.getAllStackTraces().size());
//    }
//
//    /**
//     * 针对所有继承 Activity 类的 onCreate 方法
//     */
//    @Pointcut("execution(* android.app.Activity+.onResume(..))")
//    public void activityOnResumePointcut() {
//
//    }
//
//    /**
//     * 针对前面 aspectDebugLogAnnotation() 或 activityOnCreatePointcut() 的配置
//     */
//    @Around("activityOnResumePointcut()")
//    public void aroundJoinAspectDebugLog(final ProceedingJoinPoint joinPoint) throws Throwable {
//        long startTimeMillis = System.currentTimeMillis();
//        joinPoint.proceed();
//        long duration = System.currentTimeMillis() - startTimeMillis;
//        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//        SourceLocation location = joinPoint.getSourceLocation();
//        String message = String.format("%s(%s:%s) [%sms]", methodSignature.getMethod().getName(), location.getFileName(), location.getLine(), duration);
//        Logger.d("tangzy-hhhhh", " message = "+message+" ；threadSize = "+Thread.getAllStackTraces().size());
//    }
//
//    /**
//     * 针对所有继承 Activity 类的 onCreate 方法
//     */
//    @Pointcut("execution(* android.view.View.OnClickListener.onClick(android.view.View))")
//    public void activityOnClickPointcut() {
//
//    }
//
//    @Around("activityOnClickPointcut()")
//    public void onClickLog(ProceedingJoinPoint joinPoint){
////        val view = joinPoint.args[0] as View
////        Log.e("tag",view.contentDescription.toString())
//        long startTimeMillis = System.currentTimeMillis();
//        try {
//            joinPoint.proceed();
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//        long duration = System.currentTimeMillis() - startTimeMillis;
//        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//        SourceLocation location = joinPoint.getSourceLocation();
//        String message = String.format("%s(%s:%s) [%sms]", methodSignature.getMethod().getName(), location.getFileName(), location.getLine(), duration);
//        Logger.d("tangzy-hhhhh", " message = "+message+" ；threadSize = "+Thread.getAllStackTraces().size());
//
//    }
//
//}
