package com.tangzy.tzymvp.servive;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.tangzy.tzymvp.util.Logger;

public class DemoIntentService extends IntentService {

    public DemoIntentService() {
        super("111");
        Logger.d("tangzy", "DemoIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Logger.d("tangzy", "onHandleIntent");
        int qqqqqq = intent.getIntExtra("qqqqqq", 0);
        Logger.d("tangzy", "qqqqqq = "+qqqqqq);

        while (qqqqqq != 0){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Logger.d("tangzy", "qqqqqq = "+qqqqqq--);

        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.d("tangzy", "onCreate");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Logger.d("tangzy", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Logger.d("tangzy", "onBind");
        return super.onBind(intent);
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        Logger.d("tangzy", "onStart");
    }

}
