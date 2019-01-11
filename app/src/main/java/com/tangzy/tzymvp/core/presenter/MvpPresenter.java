package com.tangzy.tzymvp.core.presenter;

import com.tangzy.tzymvp.core.view.MvpView;

/**
 * Created by Administrator on 2018/5/31.
 */

public interface MvpPresenter <V extends MvpView>{
    void attachView(V view);
    void detachView(boolean saveInstance);
}
