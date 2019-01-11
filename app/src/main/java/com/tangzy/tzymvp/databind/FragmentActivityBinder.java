package com.tangzy.tzymvp.databind;

import com.tangzy.themvp.databind.DataBinder;
import com.tangzy.tzymvp.bean.TzyBean;
import com.tangzy.tzymvp.viewbind.FragmentActivityDelegate;
import com.tangzy.tzymvp.viewbind.TzyDelegate;

public class FragmentActivityBinder implements DataBinder<FragmentActivityDelegate, TzyBean> {
    @Override
    public void viewBindModel(FragmentActivityDelegate viewDelegate, TzyBean data) {
//        viewDelegate.setText(data.getName());
    }
}
