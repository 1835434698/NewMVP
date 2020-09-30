//package androidx.recyclerview.widget;
//
//
//import android.content.Context;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import android.util.AttributeSet;
//import android.util.Log;
//
///**
// *用于解决recyclerview频繁刷新数组越界问题
// *
// */
//public class RecycylerViewLinearlayoutManager extends LinearLayoutManager {
//    private boolean isScrollEnabled = true;
//
//    public RecycylerViewLinearlayoutManager(Context context) {
//        super(context);
//    }
//
//    public RecycylerViewLinearlayoutManager(Context context, int orientation, boolean reverseLayout) {
//        super(context, orientation, reverseLayout);
//    }
//
//    public RecycylerViewLinearlayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }
//
//    public void setScrollEnabled(boolean flag) {
//        this.isScrollEnabled = flag;
//    }
//
//    @Override
//    public boolean canScrollVertically() {
//        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
//        return isScrollEnabled && super.canScrollVertically();
//    }
//
//    @Override
//    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
//        try {
//            super.onLayoutChildren(recycler, state);
//        } catch (IndexOutOfBoundsException e) {
//            Log.d("tangzyyyyy", "onLayoutChildren  e = "+ e.getMessage());
//            e.printStackTrace();
//        }
//    }
//}
//
