package com.tangzy.tzymvp.servive;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.tangzy.tzymvp.util.Logger;

public class Demo3Service extends Service {
    private static String TAG = "Demo3Service";

    Handler handler = new Handler();
    private boolean isFirst = true;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.d(TAG, "onStartCommand");
        if (isFirst){
            isFirst = false;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Logger.d(TAG, "postDelayed");
                }
            },10*1000);
        }else {
            stopSelf();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d(TAG, "onDestroy");
    }
}
