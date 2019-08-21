package com.tangzy.tzymvp.annotation;

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
                        System.out.println("methodAnnotation.uri() = "+methodAnnotation.uri());
                    // 通过反射调用带有此注解的方法
                    method.invoke(obj, methodAnnotation.uri());
                }
                MyClassAndMethodAnnotation myClassAndMethodAnnotation = method
                        .getAnnotation(MyClassAndMethodAnnotation.class);
                if (myClassAndMethodAnnotation != null) {
                    if (EnumType.util.equals(myClassAndMethodAnnotation.classType())) {
                        System.out.println("this is a util method");
                    } else {
                        System.out.println("this is a other method");
                    }
                    System.out.println(Arrays.toString(myClassAndMethodAnnotation.arr()));// 打印数组
                    System.out.println(myClassAndMethodAnnotation.color());// 输出颜色
                }
                System.out.println("\t\t-----------------------");
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
                    System.out.println("this is a util class");
                } else {
                    System.out.println("this is a other class");
                }
            }
            MyClassAnnotation myClassAnnotation = clazz.getAnnotation(MyClassAnnotation.class);
            if (myClassAnnotation != null) {
                System.err.println(" class uri: " + myClassAnnotation.uri());
                System.err.println(" class desc: " + myClassAnnotation.desc());
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
