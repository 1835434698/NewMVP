package com.tangzy.myjava.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Equation {

    private double basicLength = 80;

//    public static Point getIntersection(Point a, Point b, Point c, Point d){
//
//        double k1 = ((double)a.y-(double)b.y)/((double)a.x - (double)b.x);
//        System.out.println("intersection k1 = "+k1 );
//        double b1 = ((double)a.x*(double)b.y - (double)a.y*(double)b.x)/((double)a.x - (double)b.x );
//        System.out.println( "intersection b1 = "+b1 );
//
//        double k2 = ((double)c.y - (double)d.y)/((double)c.x - (double)d.x);
//        System.out.println( "intersection k2 = "+k2 );
//        double v1 = (double) c.y * (double) d.x;
//        double v2 = (double) c.x * (double) d.y;
//
//        double v3 = (double) d.x - (double) c.x ;
//        System.out.println( "intersection v1 = "+v1 );
//        System.out.println( "intersection v2 = "+v2 );
//        System.out.println( "intersection v3 = "+v3 );
//        double b2 = (v1 - v2)/ v3;
//        System.out.println( "intersection b2 = "+b2 );
//        int x = (int) ((b1 - b2) / (k2 - k1));
//        System.out.println( "intersection x = "+x );
//        int y = (int) (k1*x+b1);
//        System.out.println( "intersection y = "+y );
//        return new Point(x, y);
//    }


    public static boolean isBetween(Point a, Point b, Point point){
        if (point.x >= a.x && point.x <= b.x){
            return true;
        }
        HashMap<String, String> hashSet = new HashMap();

        // 遍历map中的值
        for (String value : hashSet.values()) {
            System.out.println("key = " + value);
        }

        return false;
    }

    /**
     * 设置长边在前
     * 三点必须在一条直线上。
     * @param a
     * @param b
     * @param point
     * @return
     */
    public static void setLongEdge(Point a, Point b, Point point){
        if (Math.abs(point.x - a.x) < Math.abs(point.x - b.x)){
            System.out.println( "intersection setLongEdge " );
            Point c = new Point();
            c.x = a.x;
            c.y = a.y;
            a.x=b.x;
            a.y=b.y;
            b.x = c.x;
            b.y = c.y;
            c=null;
        }
    }


    /**
     * 求三点所成角度。
     * @param a
     * @param b
     * @param c
     * @return
     */
    public static int getCurrentAngle1(Point a, Point b, Point c) {
        if (a == null || b == null || c == null) {
            return 0;
        }
        Point AB = new Point(
                b.x - a.x,
                b.y - a.y
        );
        Point CB = new Point(
                b.x - c.x,
                b.y - c.y
        );
        double dot = AB.x * CB.x + AB.y * CB.y;
        double cross = AB.x * CB.y - AB.y * CB.x;
        double alpha = Math.atan2(cross, dot);
        int xxx = (int) Math.floor(alpha * 180.0 / Math.PI + 0.5);
        return xxx;
    }
    /**
     * 求四点连线的交点。
     * @param a
     * @param b
     * @param c
     * @param d
     * @return
     */
    public static Point getIntersection(Point a, Point b, Point c, Point d){
        double k1 = getEquationK(a, b);
        double b1 = getEquationB(a, b);

        double k2 = getEquationK(c, d);
        double b2 = getEquationB(c, d);

        if ((k1 == Double.POSITIVE_INFINITY || k1 == Double.NEGATIVE_INFINITY) && (k2 == Double.POSITIVE_INFINITY || k2 == Double.NEGATIVE_INFINITY) ){
            return new Point(0,0);
        }else if (k1 == Double.POSITIVE_INFINITY || k1 == Double.NEGATIVE_INFINITY){
            double y = (k2 * a.x + b2);
            return new Point(a.x, y);
        }else if (k2 == Double.POSITIVE_INFINITY || k2 == Double.NEGATIVE_INFINITY){
            double y = (k1 * c.x + b1);
            return new Point( c.x, y);
        }else {
            double x = ((b1 - b2) / (k2 - k1));
            double y = (k1*x+b1);
            return new Point( x, y);
        }
    }
    public static double getEquationB(Point a, Point b) {
        return ((double) a.y * (double) b.x - (double) a.x * (double) b.y) / ((double) b.x - (double) a.x);
    }

    public static double getEquationK(Point a, Point b) {
        return ((double) a.y - (double) b.y) / ((double) a.x - (double) b.x);
    }

    public static double getCurrentAngle(Point a, Point b, Point c){
        Point AB = new Point(b.x -a.x, b.y - a.y);
        Point CB = new Point(b.x -c.x, b.y - c.y);

        double dot = (AB.x * CB.x + AB.y * CB.y);
        double cross = (AB.x * CB.y - AB.y * CB.x);
        double alpha = Math.atan2(cross, dot);
        int xxx = (int) Math.floor(alpha * 180.0 / Math.PI + 0.5);
        return xxx;

    }


}

