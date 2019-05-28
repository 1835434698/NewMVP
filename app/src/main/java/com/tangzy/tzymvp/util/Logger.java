package com.tangzy.tzymvp.util;

import android.text.TextUtils;
import android.util.Log;

import com.tangzy.tzymvp.BuildConfig;
import com.tangzy.tzymvp.Constant;


/**
 * Created by Administrator on 2017/10/26.
 */

public class Logger {
    private static String ERROR = "error";
    private static boolean logOpen = BuildConfig.DEBUG ? true : false;

    public static void v(String tag, String msg) {
        if (logOpen) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (logOpen) {
            int maxStrLength = 2001;
            if (!TextUtils.isEmpty(tag)){
                maxStrLength = maxStrLength - tag.length();
            }
            if (msg == null){
                msg = "";
            }
            //大于4000时
            while (msg.length() > maxStrLength) {
                Log.d(tag, msg.substring(0, maxStrLength));
                msg = msg.substring(maxStrLength);
            }
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (logOpen) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (logOpen) {
            Log.w(tag, msg);
        }
    }

    public static void e(String msg) {
        e(ERROR, msg);
    }
    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }


//    //根据需要将Log存放到SD卡中
////	private static String path = Constant.path;
//    private static File file;
//    private static FileOutputStream outputStream;
//    private static String pattern = "yyyy-MM-dd HH:mm:ss";
//
//    static {
//        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            File directory = new File(Constant.logPath);
//            if (!directory.exists()) {
//                directory.mkdirs();
//            }
//
//            file = new File(new File(Constant.logPath), "logException.txt");
//            Log.i("SDCAEDTAG", Constant.logPath);
//            try {
//                outputStream = new FileOutputStream(file, true);
//            } catch (FileNotFoundException e) {
//
//            }
//        }
//    }
//
//
//    /**
//     * 将错误信息保存到SD卡中去！可选的操作！
//     * @param msg
//     */
//    public static void save2Sd(String msg) {
//        Date date = new Date();
//        String time = DateFormatUtils.format(date, pattern);
//        if (Environment.getExternalStorageState().equals(
//                Environment.MEDIA_MOUNTED)) {
//            if (outputStream != null) {
//                try {
//                    outputStream.write(time.getBytes());
//
//                    outputStream.write(msg.getBytes());
//                    outputStream.write("\r\n".getBytes());
//                    outputStream.flush();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                Log.i("SDCAEDTAG", "file is null");
//            }
//        }
//    }
}
