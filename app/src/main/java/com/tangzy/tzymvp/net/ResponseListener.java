package com.tangzy.tzymvp.net;

/**
 * DESC
 * Created by DHQ on 2018/12/21.
 */
public interface ResponseListener {

    public void onResp(String respons, String uri);

    public void onErr(int errorCode, String respons, String uri);
}
