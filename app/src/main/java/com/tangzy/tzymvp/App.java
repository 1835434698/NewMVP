package com.tangzy.tzymvp;

import android.app.Application;
import android.content.Context;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.tangzy.tzymvp.util.Logger;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Constant.app = this;
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=5d25bb1d");
    }

    @Override
    protected void attachBaseContext(Context base) {
        Logger.d("tangzy", "attachBaseContext");
        super.attachBaseContext(base);
    }
}
