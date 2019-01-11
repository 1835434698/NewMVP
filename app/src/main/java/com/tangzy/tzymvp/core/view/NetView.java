package com.tangzy.tzymvp.core.view;

/**
 * Created by Administrator on 2018/5/31.
 */
public interface NetView extends MvpView {
    void resultFail(String uri, String message);
    void resultSuc(String uri, String result);
}
