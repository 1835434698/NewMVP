package com.tangzy.tzymvp.core.view;



/**
 * Created by Administrator on 2018/5/31.
 * 所有View基类
 */

public interface MvpView {

//    Context obtainContext();
//
//    Activity obtainActivity();

    /**
     * only show loading view
     */
    void showLoading(String waitMessage);
    void showLoading();
    void hideLoading();

}
