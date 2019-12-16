package com.tangzy.tzymvp.annotation;

import android.util.Log;

import com.tangzy.tzymvp.annotation.MyAnnotation.MyClassAndMethodAnnotation;
import com.tangzy.tzymvp.annotation.MyAnnotation.MyClassAndMethodAnnotation.EnumType;
import com.tangzy.tzymvp.annotation.MyAnnotation.MyClassAnnotation;
import com.tangzy.tzymvp.annotation.MyAnnotation.MyMethodAnnotation;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * ${CLASS} class
 *
 * @author Administrator
 * @date 2019/8/21
 */
public class ParseAnnotation {
    /**
     * 解析方法注解
     *
     * @param clazz
     */
    public static <T> void parseMethod(Class<T> clazz) {
        try {
            T obj = clazz.newInstance();
            for (Method method : clazz.getDeclaredMethods()) {
                MyMethodAnnotation methodAnnotation = method.getAnnotation(MyMethodAnnotation.class);
                if (methodAnnotation != null) {
                         Log.d("注解", "methodAnnotation.uri() = "+methodAnnotation.uri());
                    // 通过反射调用带有此注解的方法
                    method.invoke(obj, methodAnnotation.uri());
                }
                MyClassAndMethodAnnotation myClassAndMethodAnnotation = method
                        .getAnnotation(MyClassAndMethodAnnotation.class);
                if (myClassAndMethodAnnotation != null) {
                    if (EnumType.util.equals(myClassAndMethodAnnotation.classType())) {
                        Log.d("注解", "this is a util method");
                    } else {
                        Log.d("注解", "this is a other method");
                    }
                     Log.d("注解", Arrays.toString(myClassAndMethodAnnotation.arr()));// 打印数组
                     Log.d("注解", myClassAndMethodAnnotation.color());// 输出颜色
                }
                Log.d("注解", "\t\t-----------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> void parseType(Class<T> clazz) {
        try {
            MyClassAndMethodAnnotation myClassAndMethodAnnotation = clazz
                    .getAnnotation(MyClassAndMethodAnnotation.class);
            if (myClassAndMethodAnnotation != null) {
                if (EnumType.util.equals(myClassAndMethodAnnotation.classType())) {
                    Log.d("注解", "this is a util class");
                } else {
                    Log.d("注解", "this is a other class");
                }
            }
            MyClassAnnotation myClassAnnotation = clazz.getAnnotation(MyClassAnnotation.class);
            if (myClassAnnotation != null) {
                Log.d("注解", " class uri: " + myClassAnnotation.uri());
                Log.d("注解", " class desc: " + myClassAnnotation.desc());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        parseMethod(TestAnnotation.class);
        parseType(TestAnnotation.class);
    }
}
