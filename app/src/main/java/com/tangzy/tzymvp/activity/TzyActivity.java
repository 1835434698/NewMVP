package com.tangzy.tzymvp.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.tangzy.themvp.databind.DataBinder;
import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.activity.base.BaseActivity;
import com.tangzy.tzymvp.bean.LoginBean;
import com.tangzy.tzymvp.bean.TzyBean;
import com.tangzy.tzymvp.bean.UserBean;
import com.tangzy.tzymvp.databind.TzyDataBinder;
import com.tangzy.tzymvp.listener.NoDoubleClickListener;
import com.tangzy.tzymvp.presenter.NetPresenter;
import com.tangzy.tzymvp.util.Logger;
import com.tangzy.tzymvp.viewbind.TzyDelegate;

public class TzyActivity extends BaseActivity<TzyDelegate>{

    private NetPresenter netPresenter;

    @Override
    public DataBinder getDataBinder() {
        if (binder == null){
            binder = new TzyDataBinder();
        }
        return binder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        colorTitle = R.color.colorPrimaryDark;
        dark = false;
        super.onCreate(savedInstanceState);
        setTitle(viewDelegate.tzyBean.getName());
        netPresenter = new NetPresenter(this);

    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        //模拟数据改变(比如也可以写在网络请求成功的时候改变数据)
        viewDelegate.get(R.id.button1).setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                checkPermission(new CheckPermListener() {
                    @Override
                    public void superPermission() {
                        Logger.d("tangzy11", viewDelegate.userBean.getAge()+"");
                        Logger.d("tangzy11", viewDelegate.userBean.getName());

                        LoginBean loginBean = new LoginBean();
//                        loginBean.setUsername("user01");
//                        loginBean.setPassword("123456");
                        netPresenter.request(viewDelegate.userBean, true);
                    }
                }, R.string.ask_again, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE);

                viewDelegate.tzyBean.setName("哈哈哈哈");
//                //通知数据发生了改变
                notifyModelChanged(viewDelegate.tzyBean);
            }
        });
    }

    @Override
    protected Class getDelegateClass() {
        return TzyDelegate.class;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        netPresenter = null;
    }

    @Override
    public void resultFail(String uri, String message) {
        TzyBean tzyBean = new TzyBean("关某在此");
        Intent intent = new Intent(this, FragmentActivity.class);
        intent.putExtra(TzyBean.class.getCanonicalName(), tzyBean);
        startActivity(intent);
    }

    @Override
    public void resultSuc(String uri, String result) {

    }
}
