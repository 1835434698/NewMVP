package com.tangzy.tzymvp.view;

import android.content.Context;
import android.util.AttributeSet;

import in.srain.cube.views.ptr.PtrClassicDefaultFooter;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class PtrClassicDefaultFooterNew extends PtrClassicDefaultFooter {
    public PtrClassicDefaultFooterNew(Context context) {
        super(context);
    }

    public PtrClassicDefaultFooterNew(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PtrClassicDefaultFooterNew(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame, boolean isHeader) {
        super.onUIRefreshComplete(frame, isHeader);
    }
}
