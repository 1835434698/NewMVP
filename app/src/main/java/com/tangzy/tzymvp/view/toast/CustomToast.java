package com.tangzy.tzymvp.view.toast;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tangzy.tzymvp.util.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class CustomToast {

    private static Toast mToast;
    private static Context mContext;
    private static TextView mTextView;
    private static long duration;
    private static CustomToast mDkToast;
    private static final int SHOW = 1;
    private static final int HIDE = 0;
    private static Object mTN;
    private static Method mShow;
    private static Method mHide;
    private static Field mViewFeild;
    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

                case SHOW:
                    handler.sendEmptyMessageDelayed(HIDE, duration);
                    break;

                case HIDE:
                    hide();
                    break;
            }
        }
    };

    public static CustomToast makeDkToast(Context context, String msg, long dur) {

        mDkToast = new CustomToast();
        mContext = context;
        duration = dur;
        mToast = new Toast(mContext);
        mTextView = new TextView(mContext);
        mTextView.setText(msg);
        mTextView.setTextSize(18);
        mTextView.setTextColor(Color.RED);
        mToast.setView(mTextView);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        reflectToast();
        return mDkToast;
    }

    public static void reflectToast() {
        Field field = null;
        try {
            field = mToast.getClass().getDeclaredField("mTN");
            field.setAccessible(true);
            mTN = field.get(mToast);
            mShow = mTN.getClass().getDeclaredMethod("show");
            mHide = mTN.getClass().getDeclaredMethod("hide");
            mViewFeild = mTN.getClass().getDeclaredField("mNextView");
            mViewFeild.setAccessible(true);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        }

    }

    public static void show() {

        try {
            //android4.0以上就要以下处理
            if (Build.VERSION.SDK_INT > 14) {
//                Field mNextViewField = mTN.getClass().getDeclaredField("mNextView");
//                mNextViewField.setAccessible(true);
//                LayoutInflater inflate = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                View v = mToast.getView();
//                mNextViewField.set(mTN, v);
//                Method method = mTN.getClass().getDeclaredMethod("show", null);
//                method.invoke(mTN, null);
            }
//            mShow.invoke(mTN, null);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e("hhhhhhh", "error = "+e.getMessage());
        }

        handler.sendEmptyMessage(SHOW);
    }

    private static void hide() {
//        try {
//            mHide.invoke(mTN, null);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
    }
}
