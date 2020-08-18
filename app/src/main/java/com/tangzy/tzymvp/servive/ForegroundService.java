package com.tangzy.tzymvp.servive;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.tangzy.tzymvp.R;

public class ForegroundService extends Service {
    private static final String TAG = "Service";
    public static final int NOTICE_ID = 100;
    public static final String CHANNEL_ID_STRING = "nyd001";


    private boolean isFirst = true;
    private static int max = 5;
    public Notification notification;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "ForegroundService---->onCreate");
        //如果API大于18，需要弹出一个可见通知
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                if (notification == null){
                    NotificationManager notificationManager = (NotificationManager) getApplication().getSystemService(Context.NOTIFICATION_SERVICE);
                    NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID_STRING, "ForegroundService", NotificationManager.IMPORTANCE_HIGH);
                    notificationManager.createNotificationChannel(mChannel);
                    notification = new Notification.Builder(getApplicationContext(), CHANNEL_ID_STRING).build();
                }
            } else {
                if (notification == null){
                    Notification.Builder builder = new Notification.Builder(this);
                    builder.setSmallIcon(R.mipmap.ic_launcher);
                    builder.setContentTitle("KeepAppAlive");
                    builder.setContentText("ForegroundService is runing...");
                    notification = builder.build();
                }
            }
//// 如果觉得常驻通知栏体验不好
//// 可以通过启动CancelNoticeService，将通知移除，oom_adj值不变
//            Intent intent = new Intent(this, CancelNoticeService.class);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                startForegroundService(intent);
//            } else {
//                startService(intent);
//            }

            startForeground(NOTICE_ID, notification);
        } else {
            startForeground(NOTICE_ID, new Notification());
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "ForegroundService---->onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy(); // 如果Service被杀死，干掉通知
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            NotificationManager mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            mManager.cancel(NOTICE_ID);
        }
        Log.d(TAG, "ForegroundService---->onDestroy，前台service被杀死");
//// 重启自己
//        Intent intent = new Intent(getApplicationContext(), ForegroundService.class);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            startForegroundService(intent);
//        } else {
//            startService(intent);
//        }
    }

}
