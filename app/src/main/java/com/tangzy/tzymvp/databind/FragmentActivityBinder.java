package com.tangzy.tzymvp.databind;

import com.tangzy.themvp.databind.BaseDataBinder;
import com.tangzy.tzymvp.bean.TzyBean;
import com.tangzy.tzymvp.viewbind.FragmentActivityDelegate;

public class FragmentActivityBinder implements BaseDataBinder<FragmentActivityDelegate, TzyBean> {
    @Override
    public void viewBindModel(FragmentActivityDelegate viewDelegate, TzyBean data) {
//        viewDelegate.setText(data.getName());
    }
}
