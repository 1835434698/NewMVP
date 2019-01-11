package com.tangzy.tzymvp.databind;

import com.tangzy.themvp.databind.DataBinder;
import com.tangzy.tzymvp.bean.TzyBean;
import com.tangzy.tzymvp.viewbind.TzyDelegate;

public class TzyDataBinder implements DataBinder<TzyDelegate, TzyBean> {
    @Override
    public void viewBindModel(TzyDelegate viewDelegate, TzyBean data) {
        viewDelegate.setText(data.getName());
    }
}
