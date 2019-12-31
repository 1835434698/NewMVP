package com.tangzy.tzymvp.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

/**
 * class
 *
 * @author Administrator
 * @date 2019/12/30
 */
public class User extends BaseObservable {
    private String name;
    private String password;

    @Bindable
    public String getName() {
        return name;
    }
    @Bindable
    public void setName(String name) {
        this.name = name;
        notifyChange();
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    @Bindable
    public void setPassword(String password) {
        this.password = password;
        notifyChange();//通知ui改变
    }
}
