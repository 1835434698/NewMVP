package com.tangzy.tzymvp.bean.base;

public class BaseRequest {
    protected String source = "2";
    protected String uri = "";

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }
}
