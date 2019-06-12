package com.tangzy.tzymvp.test;

public class UserImpl implements Iuser {
    @Override
    public void eat(String s) {
        System.out.println("我要吃"+s);
    }
    @Override
    public void eat2(String s) {
        System.out.println("我要吃2"+s);
    }
}