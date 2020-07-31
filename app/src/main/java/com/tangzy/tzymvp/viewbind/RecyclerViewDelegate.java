package com.tangzy.tzymvp.viewbind;


import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecycylerViewLinearlayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.impl.RefreshFooterWrapper;
import com.scwang.smartrefresh.layout.impl.RefreshHeaderWrapper;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tangzy.themvp.view.BaseAppDelegate;
import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.adapter.RecycleAdapter;
import com.tangzy.tzymvp.bean.RecycleViewBean;
import com.tangzy.tzymvp.view.GridDividerItemDecoration;

import java.util.ArrayList;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * RecyclerView class
 *
 * @author Administrator
 * @date 2019/12/23
 */
public class RecyclerViewDelegate extends BaseAppDelegate {

    private RecycleAdapter adapter;
    private ArrayList<RecycleViewBean> list = new ArrayList<>();
    private RecyclerView recycleView;
    private PtrClassicFrameLayout ptrFrameLayout;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_recycle_view;
    }


    public void setDatas(RecycleViewBean data){
//        list.add(data);
//        list.add(data);
//        list.add(data);
//        list.add(data);
//        list.add(data);
//        adapter.notifyDataSetChanged();
    }
int i =0;
    @Override
    public void initWidget() {
        super.initWidget();
        setData();

        adapter = new RecycleAdapter(getActivity(), list);
        recycleView = get(R.id.recycleView);
        ptrFrameLayout = get(R.id.ptr_framelayout);
//        ptrFrameLayout.setEnableLoadMore(true);
//        ptrFrameLayout.setRefreshHeader(new RefreshHeaderWrapper(new ClassicsHeader(getActivity())));
//        ptrFrameLayout.setRefreshFooter(new RefreshFooterWrapper(new ClassicsFooter(getActivity())));
//        ptrFrameLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                i =0;
//                ptrFrameLayout.finishRefresh();
//                ptrFrameLayout.setNoMoreData(true);
//            }
//        });
//        ptrFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                i++;
//                if (i>3){
//                    ptrFrameLayout.setNoMoreData(true);
//                }else {
//                    ptrFrameLayout.setNoMoreData(false);
//                }
//                ptrFrameLayout.finishLoadMore();
//            }
//        });
//        ptrFrameLayout.getRefreshFooter();
//        ptrFrameLayout.getRefreshHeader();
//        ptrFrameLayout.autoRefresh();
//        ptrFrameLayout.setPullToRefresh(true);
//        ptrFrameLayout.setResistance(1.7f);
//        ptrFrameLayout.setRatioOfHeaderHeightToRefresh(1.2f);
//        ptrFrameLayout.setDurationToClose(200);
//        ptrFrameLayout.setDurationToCloseHeader(300);
//        // default is false
//        ptrFrameLayout.setPullToRefresh(false);
//        // default is true
//        ptrFrameLayout.setKeepHeaderWhenRefresh(true);
//
//        ptrFrameLayout.setLastUpdateTimeRelateObject(this);

        ptrFrameLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
//                getOrders();
                setData();
                ptrFrameLayout.refreshComplete();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                list.clear();
//                adapter.clearAdapter();
//                setData();
//                pageNo = 1;
//                getOrders();
                ptrFrameLayout.refreshComplete();
//                adapter.notifyDataSetChanged();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, recycleView, header);
            }
        });


        //1线性
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
////        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);//横向滚动

//        //GridLayoutManager
//        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        //生命为瀑布流的布局方式，3列，布局方向为垂直
        RecycylerViewLinearlayoutManager layoutManager = new RecycylerViewLinearlayoutManager(getActivity());

        recycleView.setLayoutManager(layoutManager);

//2 分割线
//        recycleView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));//分割线
        recycleView.addItemDecoration(new GridDividerItemDecoration(getActivity()));//九宫格分割线

        recycleView.setAdapter(adapter);
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
