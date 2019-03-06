package com.tangzy.tzymvp.presenter;


import com.tangzy.tzymvp.bean.base.BaseRequest;
import com.tangzy.tzymvp.core.presenter.MvpPresenterIml;
import com.tangzy.tzymvp.core.view.NetView;
import com.tangzy.tzymvp.net.OkHttpManager;
import com.tangzy.tzymvp.net.ResponseListener;
import com.tangzy.tzymvp.util.GsonUtils;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Administrator on 2018/5/31.
 */
public class NetPresenter extends MvpPresenterIml<NetView> {
    private ResponseListener listener;
    private int count = 0;

    public NetPresenter(NetView context) {
        super(context);
    }

    public void request(BaseRequest request, HashMap<String, File> files) {
        request(GsonUtils.toJson(request), files, request.getUri(), true);
    }
    public void request(BaseRequest request) {
        request(GsonUtils.toJson(request), null, request.getUri(), true);
    }
    public void request(BaseRequest request, boolean isLoading) {
        request(GsonUtils.toJson(request), null, request.getUri(), isLoading);
    }

    public void request(JSONObject json, HashMap<String, File> files, String uri, final boolean isLoading) {
        if (isLoading)
            getView().showLoading();
        if (listener == null){
            listener = new ResponseListener() {
                @Override
                public void onResp(String respons, String uri) {
                    if (handleResult(isLoading)) return;
                    getView().resultSuc(uri, respons);
                }

                @Override
                public void onErr(int errorCode, String respons, String uri) {
                    if (handleResult(isLoading)) return;
                    getView().resultFail(uri, respons);
                }
            };
        }
        if (isLoading){
            count++;
        }
        if (files != null){
            OkHttpManager.INSTANCE.asyncRequest(uri, json, files, listener);
        }else {
            OkHttpManager.INSTANCE.asyncRequest(uri, json, listener);
        }

    }

    private boolean handleResult(boolean isLoading) {
        if (isLoading) {
            count--;
        }
        if (getView() == null) {
            OkHttpManager.INSTANCE.removeListener(listener);
            listener = null;
            return true;
        }
        if (isLoading && count == 0) {
            getView().hideLoading();
        }
        return false;
    }
}

