package com.tangzy.myjava.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtil {

    private static final String TAG = "DataUtil";

    public static void main(String[] args) {
        System.out.println("ok");
//
//        Date date = strToDate("2019121");
//        System.out.println( "data = "+date);
        String s = getDateString("*******");
        System.out.println( "s = "+s);

        String strDate = "102052";
        float f = Float.valueOf(strDate);

//        Date date1 = strToTime(f+"");
//        System.out.println( "data1 = "+date1);
        String s1 = getTimeString("****");
        System.out.println( "s1 = "+s1);

//        String patientSex = "F";
//
//        if ("m".equals(patientSex.toLowerCase())){
//             System.out.println( "m");
//
//        }else if ("f".equals(patientSex.toLowerCase())){
//
//            System.out.println( "f");
//        }

    }

//    1.6 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
    public static Date strToTime(String strDate) {

        SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");

        ParsePosition pos = new ParsePosition(0);

        Date strtodate = formatter.parse(strDate, pos);

        return strtodate;
    }
    public static String dateTimeStr(Date dateDate) {

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

        String dateString = formatter.format(dateDate);

        return dateString;
    }

    public static String getTimeString(String strDate) {
        String date = null;
        try {
            date = dateTimeStr(strToTime(strDate));

        }catch (Exception e){

        }
        return date;
    }

    public static String getDateString(String strDate) {
        String date = null;
        try {
            date = dateToStr(strToDate(strDate));

        }catch (Exception e){

        }
        return date;
    }


    //    1.8 将短时间格式时间转换为字符串 yyyy-MM-dd
    public static String dateToStr(Date dateDate) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        String dateString = formatter.format(dateDate);

        return dateString;
    }



//1.9 将短时间格式字符串转换为时间 yyyy-MM-dd
    public static Date strToDate(String strDate) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

        ParsePosition pos = new ParsePosition(0);

        Date strtodate = formatter.parse(strDate, pos);

        return strtodate;
    }


//
////1.5 获取时间 小时:分;秒 HH:mm:ss
//    public static String getTimeShort() {
//
//        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
//
//        Date currentTime = new Date();
//
//        String dateString = formatter.format(currentTime);
//
//        return dateString;
//
//    }

}
