package com.tangzy.tzymvp.annotation;

import android.util.Log;

public class TestClass {

    public static void showKevin(Class c) {
        Log.d("注解", "c.name() = "+c.getName());
        boolean isExist = c.isAnnotationPresent(Kevin.class);

        if (isExist) {
            Kevin kevin = (Kevin) c.getAnnotation(Kevin.class);
            Log.d("注解", "kevin.name() = "+kevin.name());
        }
    }

    public static void main(String[] args) {
        showKevin(Test.class);
    }
}
