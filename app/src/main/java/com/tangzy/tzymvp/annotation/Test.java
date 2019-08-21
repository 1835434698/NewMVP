package com.tangzy.tzymvp.annotation;

/**
 * ${CLASS} class
 *
 * @author Administrator
 * @date 2019/8/21
 */
@Kevin(name = "12345")
public class Test {

    public static void showKevin(Class c) {
        System.out.println(c.getName());
        boolean isExist = c.isAnnotationPresent(Kevin.class);

        if (isExist) {
            Kevin kevin = (Kevin) c.getAnnotation(Kevin.class);
            System.out.println(kevin.name());
        }
    }

    public static void main(String[] args) {
        Test.showKevin(Test.class);
    }
}
