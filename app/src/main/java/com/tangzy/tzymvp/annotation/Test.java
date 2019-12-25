package com.tangzy.tzymvp.annotation;

import android.util.Log;

/**
 * ${CLASS} class
 *
 * @author Administrator
 * @date 2019/8/21
 */
@Kevin(name = "12345")
public class Test {

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
