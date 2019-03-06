package com.tangzy.tzymvp;

import android.app.Application;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Constant.app = this;
    }
}
