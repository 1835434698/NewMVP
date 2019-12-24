package com.tangzy.tzymvp.databind;

import com.tangzy.themvp.databind.BaseDataBinder;
import com.tangzy.tzymvp.bean.RecycleViewBean;
import com.tangzy.tzymvp.viewbind.RecyclerViewDelegate;

/**
 * RecyclerViewDataBinder class
 *
 * @author Administrator
 * @date 2019/12/23
 */
public class RecyclerViewDataBinder implements BaseDataBinder<RecyclerViewDelegate, RecycleViewBean> {
    @Override
    public void viewBindModel(RecyclerViewDelegate viewDelegate, RecycleViewBean data) {
        viewDelegate.setDatas(data);
    }
}
