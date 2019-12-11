package com.tangzy.tzymvp.servive;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.tangzy.tzymvp.MainActivity;
import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.util.Logger;

import static android.app.Notification.FLAG_NO_CLEAR;
import static android.app.Notification.FLAG_ONGOING_EVENT;

public class DemoServive extends Service {
    public static final int NOTIFICATION_FLAG = 0X11;
    private MyBinder binder = new MyBinder();

    public class MyBinder extends Binder{

    }

    private int kfc = -1;
    Thread thread =new Thread(() -> {
        while (true){
            try {
                Thread.sleep(1000);
                Logger.d("tangzy", "sleep kfc = "+kfc++);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        kfc = intent.getIntExtra("kfc", -2);
        Logger.d("tangzy", "onBind kfc = "+kfc);
//        thread.start();
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.d("tangzy", "onStartCommand_init kfc = "+kfc);
        int pid = android.os.Process.myPid();
        Log.d("tangzypid", "DemoService -> pid = "+pid);
        Log.d("tangzypid", "DemoService -> Thread = "+Thread.currentThread().getName());
        if (kfc == -1){
            kfc = intent.getIntExtra("kfc", -2);
            Logger.d("tangzy", "onStartCommand kfc = "+kfc);
            thread.start();
        }
//        notifyKJ(this);

//        startNotification();
//        if (Build.VERSION.SDK_INT < 18) {
//            startForeground(NOTIFICATION_FLAG, new Notification());
//        } else {
//            startForeground(NOTIFICATION_FLAG, new Notification());
//            Intent sendIntend = new Intent(this, Demo2Servive.class);
//            startService(sendIntend);
//        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.d("tangzy", "onCreate kfc = "+kfc);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 停止前台服务--参数：表示是否移除之前的通知
        stopForeground(true);
        Logger.d("tangzy", "Service - onDestroy kfc = "+kfc);
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
        //Intent intent = new Intent();//只显示通知，无页面跳转
        builder.setAutoCancel(true);//点击后消失
        builder.setSmallIcon(R.drawable.pullup_icon_big);//设置通知栏消息标题的头像
        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);//设置通知铃声
        builder.setTicker("ticker");
        builder.setContentTitle("通知");//设置标题
        builder.setContentText("message");//设置内容
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_launcher));   //设置大图标
        //利用PendingIntent来包装我们的intent对象,使其延迟跳转
        PendingIntent intentPend = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(intentPend);
        NotificationManager manager = (NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE);
        Notification notification = builder.build();
        manager.notify(NOTIFICATION_FLAG, notification);
        // 启动前台服务
        startForeground(NOTIFICATION_FLAG, notification);// 开始前台服务
    }


}
