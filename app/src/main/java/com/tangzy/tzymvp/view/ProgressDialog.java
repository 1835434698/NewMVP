package com.tangzy.tzymvp.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import com.tangzy.tzymvp.R;


public class ProgressDialog extends Dialog {

    Context context;
    private TextView tvMessage;
    private ImageView imageView;

    private RotateAnimation refreshingAnimation;

    public ProgressDialog(Context context) {
        this(context, R.style.MyDialogStyle);
    }

    public ProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        init(context);
    }

    @Override
    public void show() {
        imageView.startAnimation(refreshingAnimation);
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        imageView.clearAnimation();
    }

    private void init(Context context) {
        setContentView(R.layout.progress_custom);
        tvMessage = findViewById(R.id.message);
        imageView = findViewById(R.id.loadingImageView);
        refreshingAnimation = (RotateAnimation) AnimationUtils.loadAnimation(context, R.anim.progress_rotating);
        // 添加匀速转动动画
        LinearInterpolator lir = new LinearInterpolator();
        refreshingAnimation.setInterpolator(lir);
        getWindow().getAttributes().gravity = Gravity.CENTER;
    }
    public ProgressDialog setMessage(String message){
        if (tvMessage != null && message != null){
            tvMessage.setText(message);
        }
        return this;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

}