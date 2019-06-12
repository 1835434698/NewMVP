package com.tangzy.tzymvp.test;

import com.tangzy.tzymvp.util.Logger;

public class BaseUser {
    public static void getin(){
        Logger.d("tangzy", "getin");
    }
    public static void getin1(){
        Logger.d("tangzy", "getin1");
    }

    BaseUser(){
        Logger.d("tangzy", "BaseUser");
    }

    //静态代码块
    static {
        Logger.d("tangzy", "静态代码块");
    }

}
