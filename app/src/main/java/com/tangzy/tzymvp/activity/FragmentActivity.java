package com.tangzy.tzymvp.activity;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;

import com.tangzy.themvp.databind.BaseDataBinder;
import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.activity.base.BaseActivity;
import com.tangzy.tzymvp.databind.FragmentActivityBinder;
import com.tangzy.tzymvp.fragment.FirstFragment;
import com.tangzy.tzymvp.fragment.SecondFragment;
import com.tangzy.tzymvp.listener.NoDoubleClickListener;
import com.tangzy.tzymvp.util.Logger;
import com.tangzy.tzymvp.viewbind.FragmentActivityDelegate;


public class FragmentActivity extends BaseActivity<FragmentActivityDelegate> {

private  Fragment tab0  = new FirstFragment();
private  Fragment tab1 = new SecondFragment();
    @Override
    protected Class<FragmentActivityDelegate> getDelegateClass() {
        return FragmentActivityDelegate.class;
    }

    @Override
    public BaseDataBinder getDataBinder() {
        return new FragmentActivityBinder();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleBack(View.GONE);
        setClientFragment(0);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.get(R.id.btn_one).setOnClickListener(noDoubleClickListener);
        viewDelegate.get(R.id.btn_two).setOnClickListener(noDoubleClickListener);
    }

    @Override
    public void resultFail(String uri, String message) {

    }

    @Override
    public void resultSuc(String uri, String result) {

    }

    NoDoubleClickListener noDoubleClickListener = new NoDoubleClickListener() {
        @Override
        public void onNoDoubleClick(View v) {
            switch (v.getId()){
                case R.id.btn_one:
                    setClientFragment(0);
                    break;
                case R.id.btn_two:
                    setClientFragment(1);
                    break;
                    default:
                        break;
            }
        }
    };
    private int currentIndex = -1;

    public void setClientFragment(int tab) {
        if (currentIndex == tab) {
            return;
        }
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        Fragment tab0 = fragmentManager.findFragmentByTag("tab0");
//        Fragment tab1 = fragmentManager.findFragmentByTag("tab1");
        switch (tab) {
            case 0:
//                if (tab1 != null) {
//                    fragmentTransaction.hide(tab1);
//                }
//                if (tab0 == null) {
//                    tab0 = new FirstFragment();
//                    fragmentTransaction.add(R.id.fragment_content, tab0, "tab0");
//                } else {
//                    fragmentTransaction.show(tab0);
//                }
                fragmentTransaction.replace(R.id.fragment_content, tab0);
                break;
            case 1:
//                if (tab0 != null) {
//                    fragmentTransaction.hide(tab0);
//                }
//                if (tab1 == null) {
//                    tab1 = new SecondFragment();
//                    fragmentTransaction.add(R.id.fragment_content, tab1, "tab1");
//                } else {
//                    fragmentTransaction.show(tab1);
//                }
                fragmentTransaction.replace(R.id.fragment_content, tab1);
                break;
            default:
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();
        currentIndex = tab;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://0
                Logger.d("TAG", " onTouchEvent按住");
                break;
            case MotionEvent.ACTION_UP://1
                Logger.d("TAG", " onTouchEvent抬起");
                break;
            case MotionEvent.ACTION_MOVE://2
                Logger.d("TAG", " onTouchEvent移动");
                break;
            default:
                break;
        }
//        return true;
        return super.onTouchEvent(event);
    }

    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_fragment);
////        getSupportFragmentManager().beginTransaction().replace(R.id.content, new MVPFragment())
////                .commit();
//    }
}
