package com.tangzy.myjava.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import groovy.lang.GroovyClassLoader;

public class GroovyClassesTest {

    public static void testGroovyClasses() throws Exception {
        //groovy提供了一种将字符串文本代码直接转换成Java Class对象的功能
        GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
        //里面的文本是Java代码,但是我们可以看到这是一个字符串我们可以直接生成对应的Class<?>对象,而不需要我们写一个.java文件
        Class<?> clazz = groovyClassLoader.parseClass("package com.xxl.job.core.glue;\n" +
                "\n" +
                "public class Main {\n" +
                "\n" +
                "    public int age = 22;\n" +
                "    \n" +
                "    public void sayHello() {\n" +
                "        System.out.println(\"年龄是:\" + age);\n" +

                "\n" +
                "Thread thread1 = new Thread(new Runnable() {\n" +
                "            @Override\n" +
                "            public void run() {\n" +
                "        System.out.println(\"年龄是:\" + age);\n" +
                "                System.out.println(\"hhhhh\");\n" +
                "            }\n" +
                "        });\n" +
                "\n" +
                "        thread1.run();"+

                "        System.out.println(\"年龄是:\" + age);\n" +
                "getResult(1, 5);\n"+
                "    }\n" +
                "\n" +
                "public static int getResult(int a, int b){\n" +
                "        int c = a + b;\n" +
                "        System.out.println( \" c =  \"+c);\n" +
                "        return c;\n" +
                "    }\n"+
                "}\n"
        );
        Object obj = clazz.newInstance();
        Method method = clazz.getDeclaredMethod("sayHello");
        method.invoke(obj);


        Method method1 = clazz.getDeclaredMethod("getResult", int.class, int.class);
        List<Object> listValue = new ArrayList<Object>();
//        method1.getParameterTypes();//获取参数类型，按照类型添加
        listValue.add(2);
        listValue.add(5);
        Object invoke = method1.invoke(obj, listValue.toArray());
        System.out.println(invoke);

        Object val = method.getDefaultValue();
        System.out.println(val);

//        getResult(1, 5);
    }


//    public static int getResult(int a, int b){
//        int c = a + b;
//        System.out.println( " c =  "+c);
//        return c;
//    }
}
