package com.tangzy.myjava.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Main {
    //定义KB的计算常量
    private static final float KB = 1024f;
    //定义MB的计算常量
    private static final float MB = 1024 * KB;
    //定义GB的计算常量
    private static final float GB = 1024 * MB;

    public static void main(String[] args) {
//        long total = 1024*7 + 532;
//        System.out.println( "total = "+total);
//        float v = total / KB;
//        System.out.println( "v = "+v);
//        float num=(float)(Math.round(v*100)/100);//这里的100就是2位小数点，如果要求精确4位就*10000然后/10000
//        System.out.println( "num = "+num);
//        BigDecimal b = new BigDecimal(v);
//        String v1 = b.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue()+"";
//        System.out.println( "v1 = "+v1);

//        HashSet<String> hashSet = new HashSet<>();
//        hashSet.add("Camera");
//        hashSet.add("唯医");
//        hashSet.add("DCIM");
//        hashSet.add("icons");
//        hashSet.add("Screenshots");
//        hashSet.add("photo");
//        hashSet.add("boxing");
//        hashSet.add("Pictures");
//        hashSet.add("EditedOnlinePhotos");
//        hashSet.add("img");
//        hashSet.add("0");
//
//        HashMap<String, String> hashma = new HashMap<>();
//
//        hashma.put("123", "1234");
//        hashma.put("223", "2234");
//        hashma.put("523", "5234");
//        hashma.put("121", "12341");
//        hashma.put("113", "12134");
//        hashma.put("1203", "1234");
//        hashma.put("1123", "1234");
//        hashma.put("1213", "1234");
//
//        for(Map.Entry<String,String> entry : hashma.entrySet()) {
//            System.out.println( "v1 = "+entry.getKey());
//        }
//
//
//        for(String map:hashSet){
//            System.out.println( "map = "+map);
//
//        }
//        System.out.println( "getCanonicalName = "+DataUtil.class.getCanonicalName());
//        System.out.println( "getName = "+DataUtil.class.getName());
//        System.out.println( "getSimpleName = "+DataUtil.class.getSimpleName());
//        System.out.println( "getPackage = "+DataUtil.class.getPackage());


        System.out.println( "------start------ ");
        try {
            GroovyClassesTest.testGroovyClasses();
        } catch (Exception e) {
            System.out.println( "------error------ "+e.getLocalizedMessage());
            e.printStackTrace();
        }
        System.out.println( "------over------ ");
    }


//    public static Map<String, byte[]> compile(String javaName, String javaSrc) {
//        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//        StandardJavaFileManager stdManager = compiler.getStandardFileManager(null, null, null);
//        try (MemoryJavaFileManager manager = new MemoryJavaFileManager(stdManager)) {
//            JavaFileObject javaFileObject = manager.makeStringSource(javaName, javaSrc);
//            JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, null, null, Arrays.asList(javaFileObject));
//            if (task.call())
//                return manager.getClassBytes();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


    public static void StringCompilerTest() throws Exception {
        // 传入String类型的代码
        String source = "import java.util.Arrays;public class Main" +
                "{" +
                "public static void main(String[] args) {" +
                "System.out.println(Arrays.toString(args));" +
                "}" +
                "}";
//        StringCompiler.run(source, "1", "2");
    }
}
