package com.tangzy.tzymvp.bean;

import com.tangzy.tzymvp.Constant;
import com.tangzy.tzymvp.bean.base.BaseRequest;

public class LoginBean  extends BaseRequest {
    private String username;
    private String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
        setUri(Constant.Api_login);
    }
}
