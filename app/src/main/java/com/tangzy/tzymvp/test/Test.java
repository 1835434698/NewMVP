package com.tangzy.tzymvp.test;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.tangzy.tzymvp.MainActivity;
import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.util.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * ${CLASS} class
 *
 * @author Administrator
 * @date 2019/7/22
 */
public class Test {
    private static final String TAG = "Test";
    private static final String HHH = "HHH";

    static {
        Logger.d(TAG, "static-1");
        Logger.d(TAG, "HHH = " + HHH);
        HHHH = "hhhhhh";
    }

    public Test() {
        Logger.d(TAG, "Test");
    }

    private static String HHHH = "HHHH";

    static {
        Logger.d(TAG, "static-1");
        Logger.d(TAG, "HHHH = " + HHHH);
        HHHH = "hhhhhh1";
        Logger.d(TAG, "HHHH = " + HHHH);

    }


    public void test() {
        String json = "{name:\"jason\",father:\"jason\",age:18}";
//name:"jason"
//age:18
//\"\\w+\" 字符串属性
        Pattern p = Pattern.compile("\\w+:(\"\\w+\"|\\d*)");
        Matcher m = p.matcher(json);
        while (m.find()) {
            String text = m.group();
            int dotPos = text.indexOf(":");
            String key = text.substring(0, dotPos);
            String value = text.substring(dotPos + 1, text.length());
            //替换字符串的开始结束的双引号
            value = value.replaceAll("^\\\"|\\\"$", "");
            Logger.d(TAG, "key = " + key);
            Logger.d(TAG, "value = " + value);
        }
        // RxJava的链式操作
        Observable.create(new ObservableOnSubscribe<Integer>() {
            // 1. 创建被观察者(Observable) & 定义需发送的事件
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        })

                .flatMap(new Function<Integer, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(Integer integer) throws Exception {
                        return null;
                    }
                })
                .subscribe(new Observer<Integer>() {
                    // 2. 创建观察者(Observer) & 定义响应事件的行为
                    // 3. 通过订阅（subscribe）连接观察者和被观察者
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }
                    // 默认最先调用复写的 onSubscribe（）

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "对Next事件" + value + "作出响应");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }

                });

    }


    /**
     * 输出 0 1 0 1 0 1 0 1...
     */
    private boolean isLock = false;
    public int kk = 0;

    public synchronized void add() {
        try {
            while (isLock) {
                wait();
            }
            Logger.d("tangzy", "test add = " + kk);
            kk++;
            isLock = true;
            notify();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void minus() {
        try {
            while (!isLock) {
                wait();
            }
            isLock = false;
            Logger.d("tangzy", "test minus = " + kk);
            kk--;
            notify();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void notifyKJ(Context context) {
        NotificationCompat.Builder builder;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = null;
            channel = new NotificationChannel("1",
                    "Channel1", NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true); //是否在桌面icon右上角展示小红点
            channel.setLightColor(Color.GREEN); //小红点颜色
            channel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
            notificationManager.createNotificationChannel(channel);
            builder = new NotificationCompat.Builder(context, channel.getId());
        }else {
            builder = new NotificationCompat.Builder(context, null);
        }
        Intent intent = null;
        intent = new Intent(context, MainActivity.class);//将要跳转的界面
//        intent.putExtra("orderNo", jPushBean.getOrderNo());
//        }else {
//            intent = new Intent(context, LoginActivity.class);//将要跳转的界面
//        }
        //Intent intent = new Intent();//只显示通知，无页面跳转
        builder.setAutoCancel(true);//点击后消失
        builder.setSmallIcon(R.mipmap.ic_launcher);//设置通知栏消息标题的头像
        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);//设置通知铃声
        builder.setTicker("ticker");
        builder.setContentTitle("通知");
        builder.setContentText("message");
        //利用PendingIntent来包装我们的intent对象,使其延迟跳转
        PendingIntent intentPend = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(intentPend);
        NotificationManager manager = (NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());



    }


}
