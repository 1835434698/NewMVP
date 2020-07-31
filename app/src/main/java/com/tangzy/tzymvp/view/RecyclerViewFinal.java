package com.tangzy.tzymvp.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import static com.tangzy.tzymvp.BaseUrlManager.init;

public class RecyclerViewFinal extends RecyclerView {
    public RecyclerViewFinal(@NonNull Context context) {
        super(context);
    }

    public RecyclerViewFinal(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewFinal(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

}
