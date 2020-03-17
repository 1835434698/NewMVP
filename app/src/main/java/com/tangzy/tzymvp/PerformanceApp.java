package com.tangzy.tzymvp;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.tangzy.tzymvp.util.Logger;

import java.util.List;

public class PerformanceApp extends Application {
    private final String TAG = "Application";
    public int count = 0;
    @Override
    public void onCreate() {
        super.onCreate();
        Constant.app = this;
        initBugly();
        inithhhh();
        Logger.d(TAG, "onCreate");
        if (shouldInit()){
            SpeechUtility.createUtility(this, SpeechConstant.APPID +"=5d25bb1d");
            if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
                ARouter.openLog();     // 打印日志
                ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            }
            ARouter.init(this); // 尽可能早，推荐在Application中初始化

            registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

                @Override
                public void onActivityStopped(Activity activity) {
                    Log.v("viclee", activity + "onActivityStopped");
                    count--;
                    if (count == 0) {
                        Log.v("viclee", ">>>>>>>>>>>>>>>>>>>切到后台  lifecycle");
                    }
                }

                @Override
                public void onActivityStarted(Activity activity) {
                    Log.v("viclee", activity + "onActivityStarted");
                    if (count == 0) {
                        Log.v("viclee", ">>>>>>>>>>>>>>>>>>>切到前台  lifecycle");
                    }
                    count++;
                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                    Log.v("viclee", activity + "onActivitySaveInstanceState");
                }

                @Override
                public void onActivityResumed(Activity activity) {
                    Log.v("viclee", activity + "onActivityResumed");
                }

                @Override
                public void onActivityPaused(Activity activity) {
                    Log.v("viclee", activity + "onActivityPaused");
                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                    Log.v("viclee", activity + "onActivityDestroyed");
                }

                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    Log.v("viclee", activity + "onActivityCreated");
                }
            });
        }
    }

    private void inithhhh() {
        String jjj = "12313";
    }

    private void initBugly() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String j1jj = "12313";

            }
        }).start();
    }

    @Override
    protected void attachBaseContext(Context base) {
        Logger.d(TAG, "attachBaseContext");
        super.attachBaseContext(base);
    }


    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            Logger.d(TAG,  "my.pid -> " + myPid + ",mainProcessName -> " + mainProcessName);
            Logger.d(TAG,  "info.pid -> " + info.pid + ",info.processName -> " + info.processName);
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
