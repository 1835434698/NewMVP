package com.tangzy.tzymvp.util;

import android.widget.Toast;

import com.tangzy.tzymvp.Constant;


/**
 * Created by tangzy on 2016/8/10.
 */
public class Toasts {

    private static Toast toast = null;

    public static void showToastLong(String msg) {
        showToast(msg, Toast.LENGTH_LONG);
    }

    public static void showToastShort(String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }
//    private static void showToast(String msg, int time){
//        Toast.makeText(Constant.app, msg, time).show();
//    }

    private static void showToast(String msg, int time) {
        if (toast == null) {
            toast = Toast.makeText(Constant.app, msg, time);
        } else {
            toast.setText(msg);
            toast.setDuration(time);
        }
        toast.show();
    }
}
