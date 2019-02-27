package com.tangzy.tzymvp.viewbind;

import android.content.Intent;
import android.widget.TextView;

import com.tangzy.themvp.view.AppDelegate;
import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.bean.TzyBean;
import com.tangzy.tzymvp.bean.UserBean;

public class TzyDelegate extends AppDelegate {
    public TzyBean tzyBean;
    public UserBean userBean;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_tzy;
    }

    @Override
    public void setIntent(Intent intent) {
        super.setIntent(intent);
        tzyBean = (TzyBean) intent.getSerializableExtra(TzyBean.class.getCanonicalName());
        userBean = intent.getParcelableExtra(UserBean.class.getCanonicalName());
    }

//    public

    @Override
    public void initWidget() {
        super.initWidget();
        TextView textView = get(R.id.tv_title);
        textView.setText(tzyBean.getName());
    }

    public void setText(String text) {
        //get(id)等同于bindview(id)
        TextView textView = get(R.id.text);
        textView.setText(text);
    }
}
