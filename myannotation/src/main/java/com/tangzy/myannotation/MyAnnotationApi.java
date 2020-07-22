package com.tangzy.myannotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyAnnotationApi {

    public static void sayHelloAnnotation(Object pTarget) {
        String name = pTarget.getClass().getCanonicalName();
        try {
            System.out.println("name = "+name);
            Class clazz = Class.forName(name + "$$HelloWorld");
            Method method = clazz.getMethod("sayHello");
            method.invoke(null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
