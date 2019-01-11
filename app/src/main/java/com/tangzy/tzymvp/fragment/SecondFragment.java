package com.tangzy.tzymvp.fragment;

import com.tangzy.themvp.databind.DataBinder;
import com.tangzy.tzymvp.databind.FragmentActivityBinder;
import com.tangzy.tzymvp.fragment.base.BaseFragment;
import com.tangzy.tzymvp.viewbind.SecondFragDelegate;

public class SecondFragment extends BaseFragment<SecondFragDelegate> {
    @Override
    protected Class<SecondFragDelegate> getDelegateClass() {
        return SecondFragDelegate.class;
    }

    @Override
    public DataBinder getDataBinder() {
        return new FragmentActivityBinder();
    }
}
