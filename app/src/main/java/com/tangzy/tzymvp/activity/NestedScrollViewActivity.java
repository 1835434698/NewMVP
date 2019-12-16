package com.tangzy.tzymvp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;
import com.tangzy.tzymvp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * NestedScrollView class
 *
 * @author Administrator
 * @date 2019/12/16
 */
public class NestedScrollViewActivity extends AppCompatActivity {
    List<String> mData;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nestedscrollview);
        initData(1);
        initView();

    }
    private void initData(int pager) {
        mData = new ArrayList<>();
        for (int i = 1; i < 50; i++) {
            mData.add("pager" + pager + " 第" + i + "个item");
        }
    }

    private void initView() {
        //设置ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ico_title_back);
        setSupportActionBar(toolbar);//替换系统的actionBar

        //设置TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        for (int i = 1; i < 20; i++) {
            tabLayout.addTab(tabLayout.newTab().setText("TAB" + i));
        }
        //TabLayout的切换监听
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                initData(tab.getPosition() + 1);
                setScrollViewContent();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        setScrollViewContent();
    }

    /**
     * 刷新ScrollView的内容
     */
    private void setScrollViewContent() {
        //NestedScrollView下的LinearLayout
        LinearLayout layout = (LinearLayout) findViewById(R.id.ll_sc_content);
        layout.removeAllViews();
        for (int i = 0; i < mData.size(); i++) {
            View view = View.inflate(NestedScrollViewActivity.this, R.layout.item_layout, null);
            ((TextView) view.findViewById(R.id.tv_info)).setText(mData.get(i));
            //动态添加 子View
            layout.addView(view, i);
        }
    }

}
