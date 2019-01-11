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
        if (files != null){
            OkHttpManager.INSTANCE.asyncRequest(uri, json, files, new ResponseListener() {
                @Override
                public void onResp(String respons, String uri) {
                    if (isLoading)
                        getView().hideLoading();
                    getView().resultSuc(uri, respons);
                }

                @Override
                public void onErr(int errorCode, String respons, String uri) {
                    if (isLoading)
                        getView().hideLoading();
                    getView().resultFail(uri, respons);
                }
            });
        }else {
            OkHttpManager.INSTANCE.asyncRequest(uri, json, new ResponseListener() {
                @Override
                public void onResp(String respons, String uri) {
                    if (getView()==null){
                        return;
                    }
                    if (isLoading)
                        getView().hideLoading();
                    getView().resultSuc(uri, respons);
                }

                @Override
                public void onErr(int errorCode, String respons, String uri) {
                    if (getView()==null){
                        return;
                    }
                    if (isLoading)
                        getView().hideLoading();
                    getView().resultFail(uri, respons);
                }
            });
        }

    }

}

