package com.tangzy.tzymvp.fragment;

import com.tangzy.themvp.databind.DataBinder;
import com.tangzy.tzymvp.databind.FragmentActivityBinder;
import com.tangzy.tzymvp.fragment.base.BaseFragment;
import com.tangzy.tzymvp.viewbind.FirstFragDelegate;

public class FirstFragment extends BaseFragment<FirstFragDelegate> {
    @Override
    protected Class<FirstFragDelegate> getDelegateClass() {
        return FirstFragDelegate.class;
    }

    @Override
    public DataBinder getDataBinder() {
        return new FragmentActivityBinder();
    }
}
