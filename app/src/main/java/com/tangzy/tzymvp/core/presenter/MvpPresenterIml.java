package com.tangzy.tzymvp.core.presenter;

import com.tangzy.themvp.presenter.BaseActivityPresenter;
import com.tangzy.tzymvp.activity.base.BaseActivity;
import com.tangzy.tzymvp.core.view.MvpView;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2018/5/31.
 */

public class MvpPresenterIml<V extends MvpView> implements MvpPresenter<V>{

    public MvpPresenterIml(V context) {
        attachView(context);
        if (context != null && context instanceof BaseActivityPresenter) {
            ((BaseActivity)context).addPresenter(this);
//        }else if (context != null && context instanceof BaseFragment){
//            ((BaseFragment)context).addPresenter(this);
        }
    }

    private WeakReference<V> viewRef;

    @Override
    public void attachView(V view) {
        viewRef = new WeakReference<>(view);
    }

    @Override
    public void detachView(boolean saveInstance) {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }

    public V getView(){
        return viewRef == null ? null : viewRef.get();
    }

    public boolean isViewAttached() {
        return viewRef != null && viewRef.get() != null;
    }

}
