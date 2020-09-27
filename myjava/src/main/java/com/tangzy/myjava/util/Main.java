package com.tangzy.myjava.util;

import java.math.BigDecimal;

public class Main {
    //定义KB的计算常量
    private static final float KB = 1024f;
    //定义MB的计算常量
    private static final float MB = 1024 * KB;
    //定义GB的计算常量
    private static final float GB = 1024 * MB;

    public static void main(String[] args) {
        long total = 1024*7 + 532;
        System.out.println( "total = "+total);
        float v = total / KB;
        System.out.println( "v = "+v);
        float num=(float)(Math.round(v*100)/100);//这里的100就是2位小数点，如果要求精确4位就*10000然后/10000
        System.out.println( "num = "+num);
        BigDecimal b = new BigDecimal(v);
        String v1 = b.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue()+"";
        System.out.println( "v1 = "+v1);

    }
}
