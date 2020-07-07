package com.tangzy.tzymvp;

import android.content.Context;
import android.os.Environment;

public class Constant {
    public static Context app;
//    public static final String url = "http://120.27.23.38:80/api/";
//    public static final String URL = "http://192.168.69.162:8080/mvc/";
    public static final String URL = "http://192.168.69.162/case_prpc/";
//    public static final String URL = "http://www.koudt.com/test/";
    public static String MESSSAGENET = "无网络";
    public static String MESSSAGE = "网络错误";
    public static String MESSSAGEJSON = "数据解析错误";
    public static int ERRORCODE = -1;
    public static String path = Environment.getExternalStorageDirectory().toString()+"/DemoApp1";

    public static final String API_LOGIN = "login";
}
