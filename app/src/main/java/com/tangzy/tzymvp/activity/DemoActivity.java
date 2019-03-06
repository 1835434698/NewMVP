package com.tangzy.tzymvp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tangzy.tzymvp.util.Logger;

public class DemoActivity extends AppCompatActivity {

    private final static int MESSAGECODE = 1;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Logger.d("mmmmmmmm", "handler " + msg.what);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        /**
//         * 匿名内部类：Runnable导致内存泄漏
//         */
////        new Thread(new Runnable() {
////            @Override
////            public void run() {
////                try {
////                    //模拟耗时操作
////                    Thread.sleep(15000);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
////            }
////        }).start();
//            /**
//             * 匿名内部类：Runnable导致内存泄漏
//             * 优化版
//             */
////        new Thread(new MyRunnable()).start();
//        /**
//         *  成员内部类：Handler
//         * 泄漏版：
//         */
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                handler.sendEmptyMessage(MESSAGECODE);
//                try {
//                    Thread.sleep(18000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                handler.sendEmptyMessage(MESSAGECODE);
//            }
//        }).start();


    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        /**
//         *  成员内部类：Handler
//         * 优化版
//         */
////        handler.removeCallbacksAndMessages(null);
//    }

    private static class MyRunnable implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
