package com.tangzy.tzymvp.test;

import com.tangzy.tzymvp.util.Logger;

public class ChredUser extends BaseUser{
    public static void getin(){
        Logger.d("tangzy", "ChredUser getin");
    }
    public static void getin2(){
        Logger.d("tangzy", "ChredUser getin2");
    }

    public ChredUser(){
        Logger.d("tangzy", "ChredUser");
    }

    //静态代码块
    static {
        Logger.d("tangzy", "ChredUser 静态代码块");
    }

}
