package com.tangzy.pdfrecyclerview.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class OnScrollListenerRecycleView extends RecyclerView.OnScrollListener {

    private int lastDy;
    private int thePosition=0;
    
    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        lastDy = dy;
        Log.d("tagnzy", "onScrolled ; lastDy = "+lastDy);
        if (lastDy > 0){
            setPosition(((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition());
        }else if (lastDy < 0){
            setPosition(((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition());
        }else {

        }
    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        Log.d("tagnzy", "onScrollStateChanged newState = "+newState+", lastDy = "+lastDy);
        if (RecyclerView.SCROLL_STATE_IDLE == newState){
            if (lastDy>0){
                recyclerView.smoothScrollToPosition(((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition());
            }else if (lastDy < 0){
                recyclerView.smoothScrollToPosition(((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition());
            }
        }
    }

    private void setPosition(final int position) {
        if (thePosition == position){
            if (thePosition == 0){
                onAdapterChanged(thePosition);
            }
        }else {
            thePosition = position;
            onAdapterChanged(thePosition);
        }

    }

    public void onAdapterChanged(int position){}

}
