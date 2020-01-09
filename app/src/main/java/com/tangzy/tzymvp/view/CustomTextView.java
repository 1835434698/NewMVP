package com.tangzy.tzymvp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * class
 *
 * @author Administrator
 * @date 2020/1/3
 */
public class CustomTextView extends TextView {
    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d("TangzyCu", "TextView:dispatchTouchEvent");
        return super.dispatchTouchEvent(event);
//        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("TangzyCu", "TextView:onTouchEvent");
        boolean isUser = true;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
//                isUser = false;
                break;
            case MotionEvent.ACTION_MOVE:
//                isUser = true;
                break;
            case MotionEvent.ACTION_UP:
//                isUser = true;
                break;
        }
        return super.onTouchEvent(event);
//        return isUser;
    }

    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 100;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        Log.d("TangzyCu", "TextView:onMeasure");
        int width = getProperSize(DEFAULT_WIDTH, widthMeasureSpec);
        int height = getProperSize(DEFAULT_HEIGHT, heightMeasureSpec);
//        Log.d("TangzyCu", "TextView:onMeasure- widthMeasureSpec = "+widthMeasureSpec+",heightMeasureSpec = "+heightMeasureSpec);
        Log.d("TangzyCu", "TextView:onMeasure- width = "+width+",height = "+height);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.d("TangzyCu", "TextView:onLayout");
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("TangzyCu", "TextView:onDraw");
        super.onDraw(canvas);
    }
    private int getProperSize(int defaultSize, int measureSpec) {
        int result;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
//        Log.d("TangzyCu", "TextView:mode = "+mode);

        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = defaultSize;
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }

        return result;
    }

}
