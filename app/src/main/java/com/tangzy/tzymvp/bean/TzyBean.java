package com.tangzy.tzymvp.bean;

import com.tangzy.themvp.model.IModel;

import java.io.Serializable;

public class TzyBean implements IModel, Serializable {
    private String name;

    public TzyBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
