package com.tangzy.tzymvp.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.impl.RefreshFooterWrapper;
import com.scwang.smartrefresh.layout.impl.RefreshHeaderWrapper;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.adapter.RecycleAdapter;
import com.tangzy.tzymvp.bean.RecycleViewBean;
import com.tangzy.tzymvp.view.GridDividerItemDecoration;

import java.util.ArrayList;

public class SmartRefreshLayoutActivity extends AppCompatActivity {
    private RecycleAdapter adapter;
    private ArrayList<RecycleViewBean> list = new ArrayList<>();
    private RecyclerView recycleView;
    private SmartRefreshLayout ptrFrameLayout;
    private View vLine;
    private View vLine2;

    int i =0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_smartrefresh);
        init();


    }

    private void init() {

        setData();
        vLine = findViewById(R.id.vLine);
        vLine2 = findViewById(R.id.vLine2);

        adapter = new RecycleAdapter(this, list);
        recycleView = findViewById(R.id.recycleView);
        ptrFrameLayout = findViewById(R.id.ptr_framelayout);
//        ptrFrameLayout.setEnableLoadMore(false);
        ptrFrameLayout.setRefreshHeader(new RefreshHeaderWrapper(new ClassicsHeader(this)));
        ptrFrameLayout.setRefreshFooter(new RefreshFooterWrapper(new ClassicsFooter(this)));
        ptrFrameLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                list.clear();
//                setData();

                i =0;
                ptrFrameLayout.setNoMoreData(false);
                ptrFrameLayout.finishRefresh();
            }
        });
        ptrFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                i++;
                if (i>3){
                    ptrFrameLayout.setNoMoreData(true);
                }else {
                    ptrFrameLayout.setNoMoreData(false);
                }
                ptrFrameLayout.finishLoadMore();
            }
        });
        ptrFrameLayout.getRefreshFooter();
        ptrFrameLayout.getRefreshHeader();


        //1线性
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
////        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);//横向滚动

        //        //GridLayoutManager
//        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        //生命为瀑布流的布局方式，3列，布局方向为垂直
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        recycleView.setLayoutManager(layoutManager);

//2 分割线
//        recycleView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));//分割线
        recycleView.addItemDecoration(new GridDividerItemDecoration(this));//九宫格分割线

        recycleView.setAdapter(adapter);

        vLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recycleView.setLayoutManager(new LinearLayoutManager(SmartRefreshLayoutActivity.this));

            }
        });
        vLine2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recycleView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

            }
        });
    }

    private void setData() {
        RecycleViewBean data = new RecycleViewBean();
//        for (int i=0;i<7;i++){
//            data.setName("姓名：No"+i);
//            list.add(data);
//        }

        data.setName("姓名：No");
        data.setImg(R.drawable.image1);
        list.add(data);
        data = new RecycleViewBean();
        data.setImg(R.drawable.image2);
        list.add(data);
        data = new RecycleViewBean();
        data.setImg(R.drawable.image2);
        list.add(data);
        data = new RecycleViewBean();
        data.setImg(R.drawable.image3);
        list.add(data);
        data = new RecycleViewBean();
        data.setImg(R.drawable.image4);
        list.add(data);
        data = new RecycleViewBean();
        data.setImg(R.drawable.image5);
        list.add(data);
        data = new RecycleViewBean();
        data.setImg(R.drawable.image6);
        list.add(data);
        data = new RecycleViewBean();
        data.setImg(R.drawable.image7);
        list.add(data);
    }

}
