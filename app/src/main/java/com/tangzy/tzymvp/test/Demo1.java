package com.tangzy.tzymvp.test;

import java.util.List;

public class Demo1 {

    public <T> T getListFirst(List<T> data){
        return data.get(0);
    }
}
