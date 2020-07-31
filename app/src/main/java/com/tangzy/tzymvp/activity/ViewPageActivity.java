package com.tangzy.tzymvp.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.fragment.FirstFragment;
import com.tangzy.tzymvp.fragment.SecondFragment;
import com.tangzy.tzymvp.util.Logger;

import java.util.ArrayList;

public class ViewPageActivity extends AppCompatActivity {

    private SlidingTabLayout tabLayout;
    private ViewPager vp_container;
    private ArrayList<Fragment> fragmentArray = new ArrayList();
    private String[] titles = new String[]{"介绍", "交流", "交流1"};
    private FirstFragment  firstFragment = new FirstFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpage);
        init();

        tabLayout.setViewPager(vp_container, titles,this, fragmentArray);
        fragmentArray.clear();
        fragmentArray.add(firstFragment);
        fragmentArray.add(new SecondFragment());
        tabLayout.setViewPager(vp_container, titles,this, fragmentArray);
//        tabLayout.setViewPager(vp_container, titles,this, fragmentArray);
//        tabLayout.setViewPager(vp_container, titles,this, fragmentArray);
    }

    private void init() {
        tabLayout = findViewById(R.id.tabLayout);
        vp_container = findViewById(R.id.vp_container);
        fragmentArray.add(firstFragment);
        fragmentArray.add(new SecondFragment());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Logger.d("tangzyyy", "dispatchTouchEvent");
        if (ev.getAction() == MotionEvent.ACTION_DOWN){
            return super.dispatchTouchEvent(ev);
        }
        Logger.d("tangzyyy", "superDispatchGenericMotionEvent");
        if (getWindow().superDispatchGenericMotionEvent(ev)){

            return true;
        }
        Logger.d("tangzyyy", "onTouchEvent");
        return onTouchEvent(ev);
    }
}
