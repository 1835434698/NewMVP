package com.tangzy.tzymvp.viewbind;


import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.tangzy.themvp.view.BaseAppDelegate;
import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.adapter.RecycleAdapter;
import com.tangzy.tzymvp.bean.RecycleViewBean;
import com.tangzy.tzymvp.view.GridDividerItemDecoration;

import java.util.ArrayList;

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

    @Override
    public void initWidget() {
        super.initWidget();
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

        adapter = new RecycleAdapter(getActivity(), list);
        recycleView = get(R.id.recycleView);
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
        recycleView.addItemDecoration(new GridDividerItemDecoration(getActivity()));//九宫格分割线

        recycleView.setAdapter(adapter);
    }
}
