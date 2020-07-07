package com.tangzy.tzymvp.view.toast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.tangzy.tzymvp.R;


public class FloatWindowView extends FrameLayout {
    public static int viewWidth;
    public static int viewHeight;

    public FloatWindowView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.float_window_view, this);
        View view = findViewById(R.id.root_window_layout);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;
    }
}
