package com.tangzy.tzymvp.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tangzy.tzymvp.util.Logger;

/**
 * @author tzy
 * @date 2021/7/8 16:14
 * @discription
 */
public class MagnifierImageView extends  androidx.appcompat.widget.AppCompatImageView {

    private static final String TAG = "MagnifierImageView";
    private MagnifierView magnifierView;
    public MagnifierImageView(@NonNull Context context) {
        super(context);
        initUI();
    }

    public MagnifierImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initUI();
    }

    public MagnifierImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUI();
    }
    /**
     * 初始化UI
     */
    private void initUI() {
        this.setScaleType(ScaleType.FIT_CENTER);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Logger.d(TAG, "onTouch ");
                return false;
            }
        });
        magnifierView = new MagnifierView();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Logger.d(TAG, "onDraw ");
        magnifierView.onDrowMagnifier(canvas, this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Logger.d(TAG, "onTouchEvent ");

        if (event.getPointerCount() ==1 ){
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    Logger.d(TAG, "onTouchEvent Down");
                    magnifierView.setLocation(event.getX(), event.getY());
                    break;
                case MotionEvent.ACTION_MOVE:
                    Logger.d(TAG, "onTouchEvent Move");
                    magnifierView.setLocation(event.getX(), event.getY());
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    magnifierView.setLocation(-1, -1);
                    break;
            }
            invalidate();
            return true;
        }
        return super.onTouchEvent(event);
    }
}
