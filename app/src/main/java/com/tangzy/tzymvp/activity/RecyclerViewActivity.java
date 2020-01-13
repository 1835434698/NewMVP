package com.tangzy.tzymvp.activity;

import android.content.Intent;
import android.view.View;

import com.tangzy.themvp.databind.BaseDataBinder;
import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.activity.base.BaseActivity;
import com.tangzy.tzymvp.bean.RecycleViewBean;
import com.tangzy.tzymvp.databind.RecyclerViewDataBinder;
import com.tangzy.tzymvp.viewbind.RecyclerViewDelegate;

/**
 * RecyclerView class
 *
 * @author Administrator
 * @date 2019/12/23
 */
public class RecyclerViewActivity extends BaseActivity<RecyclerViewDelegate> {
    @Override
    public BaseDataBinder getDataBinder() {
        if (binder == null){
            binder = new RecyclerViewDataBinder();
        }
        return binder;
    }

    @Override
    protected Class<RecyclerViewDelegate> getDelegateClass() {
        return RecyclerViewDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        //模拟数据改变(比如也可以写在网络请求成功的时候改变数据)
        viewDelegate.get(R.id.bt_glide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                data.setName("改变了数据");
//                //通知数据发生了改变
//                notifyModelChanged(data);
                startActivity(new Intent(RecyclerViewActivity.this, GlideActivity.class));
            }
        });
    }

    @Override
    public void resultFail(String uri, String message) {
        RecycleViewBean bean = new RecycleViewBean();
        notifyModelChanged(bean);
    }

    @Override
    public void resultSuc(String uri, String result) {
        RecycleViewBean bean = new RecycleViewBean();
        notifyModelChanged(bean);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
