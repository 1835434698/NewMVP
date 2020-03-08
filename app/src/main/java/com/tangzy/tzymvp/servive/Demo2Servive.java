package com.tangzy.tzymvp.servive;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.tangzy.tzymvp.MainActivity;
import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.util.Logger;

import static android.app.Notification.FLAG_NO_CLEAR;
import static android.app.Notification.FLAG_ONGOING_EVENT;

public class Demo2Servive extends Service {
    private MyBinder binder = new MyBinder();

    public class MyBinder extends Binder{

    }

    private int kfc = 1;
    Thread thread =new Thread(() -> {
        while (kfc != 0){
            try {
                Thread.sleep(1000);
                Logger.d("tangzy", "sleep kfc = "+kfc--);

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
        thread.start();
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.d("tangzy", "onStartCommand_init kfc = "+kfc);
//        if (kfc == -1){
//            kfc = intent.getIntExtra("kfc", -2);
//            Logger.d("tangzy", "onStartCommand kfc = "+kfc);
//            thread.start();
//        }
//        startNotification();
//        startForeground(DemoServive.NOTIFICATION_FLAG, new Notification());
//        stopForeground(true);
//        stopSelf();
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
        Logger.d("tangzy", "Service - onDestroy kfc = "+kfc);
    }

    public void startNotification(){

        // 在Android进行通知处理，首先需要重系统哪里获得通知管理器NotificationManager，它是一个系统Service。
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // 设置点击通知跳转的Intent
        Intent nfIntent = new Intent(this, MainActivity.class);
        // 设置 延迟Intent
        // 最后一个参数可以为PendingIntent.FLAG_CANCEL_CURRENT 或者 PendingIntent.FLAG_UPDATE_CURRENT
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, nfIntent, 0);

        //构建一个Notification构造器
        Notification.Builder builder = new Notification.Builder(this.getApplicationContext());

        builder.setContentIntent(pendingIntent)   // 设置点击跳转界面
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
                        R.mipmap.ic_launcher)) // 设置下拉列表中的图标(大图标)
                .setTicker("您有一个notification")// statusBar上的提示
                .setContentTitle("这是标题") // 设置下拉列表里的标题
                .setSmallIcon(R.mipmap.ic_launcher) // 设置状态栏内的小图标24X24
                .setContentText("这是内容") // 设置详细内容
                .setContentIntent(pendingIntent) // 设置点击跳转的界面
                .setWhen(System.currentTimeMillis()); // 设置该通知发生的时间
//				.setDefaults(Notification.DEFAULT_VIBRATE) //默认震动方式
//                .setPriority(Notification.PRIORITY_HIGH);   //优先级高

        Notification notification = builder.build(); // 获取构建好的Notification

        notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音
        notification.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。
        notification.flags |= FLAG_ONGOING_EVENT; //将此通知放到通知栏的"Ongoing"即"正在运行"组中
        notification.flags |= FLAG_NO_CLEAR; //表明在点击了通知栏中的"清除通知"后，此通知不清除，常与FLAG_ONGOING_EVENT一起使用


        manager.notify(NOTIFICATION_FLAG, notification);
        // 启动前台服务
        // 参数一：唯一的通知标识；参数二：通知消息。
        startForeground(NOTIFICATION_FLAG, notification);// 开始前台服务
    }

    private static final int NOTIFICATION_FLAG =0X11;

}
