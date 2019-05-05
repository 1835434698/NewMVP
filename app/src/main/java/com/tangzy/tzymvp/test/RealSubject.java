package com.tangzy.tzymvp.test;

import com.tangzy.tzymvp.util.Logger;

public class RealSubject implements Subject{
    @Override
    public String operation() {
        Logger.d("tangzy:", "operation () ");
        return "operation by subject";
    }

    @Override
    public String operation2() {
        return "operation by subject2";
    }
}
