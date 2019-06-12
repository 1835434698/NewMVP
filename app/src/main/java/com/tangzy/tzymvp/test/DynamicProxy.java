package com.tangzy.tzymvp.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynamicProxy implements InvocationHandler {
    private Object object;//用于接收具体实现类的实例对象
    //使用带参数的构造器来传递具体实现类的对象
        public DynamicProxy(Object obj){
        this.object = obj;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args)throws Throwable {
    System.out.println("前置内容");
    method.invoke(object, args);
    System.out.println("后置内容");
    return null;
    }
}
