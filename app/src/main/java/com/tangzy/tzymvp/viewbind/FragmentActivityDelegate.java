package com.tangzy.tzymvp.viewbind;

import android.content.Intent;
import android.widget.TextView;

import com.tangzy.themvp.view.BaseAppDelegate;
import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.bean.TzyBean;

public class FragmentActivityDelegate extends BaseAppDelegate {
    public TzyBean tzyBean;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_fragment;
    }

    @Override
    public void setIntent(Intent intent) {
        super.setIntent(intent);
        tzyBean = (TzyBean) intent.getSerializableExtra(TzyBean.class.getCanonicalName());
    }

    @Override
    public void initWidget() {
        super.initWidget();
        TextView textView = get(R.id.tv_title);
        textView.setText(tzyBean.getName());
    }

//    public void setText(String text) {
//        //get(id)等同于bindview(id)
//        TextView textView = get(R.id.text);
//        textView.setText(text);
//    }
}
