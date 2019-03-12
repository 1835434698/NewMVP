package com.tangzy.tzymvp;

import android.app.Application;
import android.content.Context;

import com.tangzy.tzymvp.util.Logger;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Constant.app = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        Logger.d("tangzy", "attachBaseContext");
        super.attachBaseContext(base);
    }
}
