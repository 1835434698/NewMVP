package com.tangzy.tzymvp.annotation;

import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.tangzy.tzymvp.annotation.MyAnnotation.MyClassAndMethodAnnotation;
import com.tangzy.tzymvp.annotation.MyAnnotation.MyClassAndMethodAnnotation.EnumType;
import com.tangzy.tzymvp.annotation.MyAnnotation.MyClassAnnotation;
import com.tangzy.tzymvp.annotation.MyAnnotation.MyConstructorAnnotation;
import com.tangzy.tzymvp.annotation.MyAnnotation.MyFieldAnnotation;
import com.tangzy.tzymvp.annotation.MyAnnotation.MyMethodAnnotation;
/**
 * ${CLASS} class
 *
 * @author Administrator
 * @date 2019/8/21
 */

@MyClassAnnotation(desc = "The Class", uri = "com.sgl.annotation")
@MyClassAndMethodAnnotation(classType = EnumType.entity)
public class TestAnnotation {
    @MyFieldAnnotation(desc = "The Class Field", uri = "com.sgl.annotation#id")
    private String id;

    @MyConstructorAnnotation(desc = "The Class Constructor", uri = "com.sgl.annotation#constructor")
    public TestAnnotation() {
        Log.d("注解", "this is TestAnnotation（）");
    }

    public String getId() {
        return id;
    }

    @MyMethodAnnotation(desc = "The Class Method", uri = "com.sgl.annotation##setId")
    public void setId(String id) {
        Log.d("注解", "setId id = "+id);
        this.id = id;
    }

    @MyMethodAnnotation(desc = "The Class Method sayHello", uri = "com.sgl.annotation##sayHello")
    public void sayHello(String name) {
        if (name == null || name.equals("")) {
            Log.d("注解", "hello world!");
        } else {
            Log.d("注解", name + "\t:say hello world");
        }
    }

//    public static void main(String[] args) throws Exception {
//        Class<TestAnnotation> clazz = TestAnnotation.class;
//        // 获取类注解
//        MyClassAnnotation myClassAnnotation = clazz.getAnnotation(MyClassAnnotation.class);
//        Log.d("注解", myClassAnnotation.desc() + "+" + myClassAnnotation.uri());
//
//        // 获得构造方法注解
//        Constructor<TestAnnotation> constructors = clazz.getConstructor(new Class[] {});// 先获得构造方法对象
//        MyConstructorAnnotation myConstructorAnnotation = constructors.getAnnotation(MyConstructorAnnotation.class);// 拿到构造方法上面的注解实例
//        Log.d("注解", myConstructorAnnotation.desc() + "+" + myConstructorAnnotation.uri());
//
//        // 获得方法注解
//        Method method = clazz.getMethod("setId", new Class[] { String.class });// 获得方法对象
//        MyMethodAnnotation myMethodAnnotation = method.getAnnotation(MyMethodAnnotation.class);
//        Log.d("注解", myMethodAnnotation.desc() + "+" + myMethodAnnotation.uri());
//
//        // 获得字段注解
//        Field field = clazz.getDeclaredField("id");// 暴力获取private修饰的成员变量
//        MyFieldAnnotation myFieldAnnotation = field.getAnnotation(MyFieldAnnotation.class);
//        Log.d("注解", myFieldAnnotation.desc() + "+" + myFieldAnnotation.uri());
//    }
}
