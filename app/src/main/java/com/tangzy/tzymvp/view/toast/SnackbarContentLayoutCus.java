package com.tangzy.tzymvp.view.toast;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.view.ViewCompat;

import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.util.Logger;

/**
 * Created by RickBerg on 2018/5/9 0009.
 */

public class SnackbarContentLayoutCus extends LinearLayout implements
        BaseTransienTopBarCus.ContentViewCallback{
    private TextView mMessageView;
    private TextView mTitleView;

    private int mMaxWidth;
    private int mMaxInlineActionWidth;

    public SnackbarContentLayoutCus(Context context) {
        this(context, null);
    }

    public SnackbarContentLayoutCus(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SnackbarLayout);
        mMaxWidth = a.getDimensionPixelSize(R.styleable.SnackbarLayout_android_maxWidth, -1);
        mMaxInlineActionWidth = a.getDimensionPixelSize(
                R.styleable.SnackbarLayout_maxActionInlineWidth, -1);
        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mMessageView = findViewById(R.id.snackbar_text);
        mTitleView = findViewById(R.id.snackbar_title);
    }

    public TextView getMessageView() {
        return mMessageView;
    }

    public TextView getTitleView() {
        return mTitleView;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//        if (mMaxWidth > 0 && getMeasuredWidth() > mMaxWidth) {
//            widthMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxWidth, MeasureSpec.EXACTLY);
//            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        }
//
//        final int multiLineVPadding = getResources().getDimensionPixelSize(
//                R.dimen.design_snackbar_padding_vertical_2lines);
//        final int singleLineVPadding = getResources().getDimensionPixelSize(
//                R.dimen.design_snackbar_padding_vertical);
//        final boolean isMultiLine = mMessageView.getLayout().getLineCount() > 1;
//
//        boolean remeasure = false;
//        if (isMultiLine && mMaxInlineActionWidth > 0
//                && mTitleView.getMeasuredWidth() > mMaxInlineActionWidth) {
//            if (updateViewsWithinLayout(VERTICAL, multiLineVPadding,
//                    multiLineVPadding - singleLineVPadding)) {
//                remeasure = true;
//            }
//        } else {
//            final int messagePadding = isMultiLine ? multiLineVPadding : singleLineVPadding;
//            if (updateViewsWithinLayout(HORIZONTAL, messagePadding, messagePadding)) {
//                remeasure = true;
//            }
//        }
//
//        if (remeasure) {
//            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        }
    }

    private boolean updateViewsWithinLayout(final int orientation,
                                            final int messagePadTop, final int messagePadBottom) {
        boolean changed = false;
        if (orientation != getOrientation()) {
            setOrientation(orientation);
            changed = true;
        }
        if (mMessageView.getPaddingTop() != messagePadTop
                || mMessageView.getPaddingBottom() != messagePadBottom) {
            updateTopBottomPadding(mMessageView, messagePadTop, messagePadBottom);
            changed = true;
        }
        return changed;
    }

    private static void updateTopBottomPadding(View view, int topPadding, int bottomPadding) {
        if (ViewCompat.isPaddingRelative(view)) {
            ViewCompat.setPaddingRelative(view,
                    ViewCompat.getPaddingStart(view), topPadding,
                    ViewCompat.getPaddingEnd(view), bottomPadding);
        } else {
            view.setPadding(view.getPaddingLeft(), topPadding,
                    view.getPaddingRight(), bottomPadding);
        }
    }

    @Override
    public void animateContentIn(int delay, int duration) {
//        mMessageView.setAlpha(0f);
//        mMessageView.animate().alpha(1f).setDuration(duration)
//                .setStartDelay(delay).start();
        Logger.d("hhhhhhh", "animateContentIn ; delay = "+delay+", duration = "+duration);

        if (mTitleView.getVisibility() == VISIBLE) {
            mTitleView.setAlpha(0f);
            mTitleView.animate().alpha(1f).setDuration(duration)
                    .setStartDelay(delay).start();
        }
    }

    @Override
    public void animateContentOut(int delay, int duration) {
//        mMessageView.setAlpha(1f);
//        mMessageView.animate().alpha(0f).setDuration(duration)
//                .setStartDelay(delay).start();
        Logger.d("hhhhhhh", "animateContentOut ; delay = "+delay+", duration = "+duration);

        if (mTitleView.getVisibility() == VISIBLE) {
            mTitleView.setAlpha(1f);
            mTitleView.animate().alpha(0f).setDuration(duration)
                    .setStartDelay(delay).start();
        }
    }
}
