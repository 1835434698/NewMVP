package com.tangzy.tzymvp.fragment.base;

import android.os.Bundle;
import android.view.View;

import com.tangzy.themvp.databind.DataBinder;
import com.tangzy.themvp.model.IModel;
import com.tangzy.themvp.presenter.FragmentPresenter;
import com.tangzy.themvp.view.IDelegate;

public abstract class BaseFragment <T extends IDelegate> extends
        FragmentPresenter<T> {

    protected DataBinder binder;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binder = getDataBinder();
    }

    public abstract DataBinder getDataBinder();

    public <D extends IModel> void notifyModelChanged(D data) {
        if (binder != null) {
            binder.viewBindModel(viewDelegate, data);
        }
    }

}
