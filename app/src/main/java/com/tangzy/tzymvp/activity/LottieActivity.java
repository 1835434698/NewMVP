package com.tangzy.tzymvp.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.tangzy.tzymvp.R;

public class LottieActivity extends AppCompatActivity {
    private LottieAnimationView mLottieAnimationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie);
        mLottieAnimationView = findViewById(R.id.animation_view);

        mLottieAnimationView.setAnimation("loading.json");
        mLottieAnimationView.loop(true);

    }

    public void toStart(View view) {

        mLottieAnimationView.playAnimation();

    }

    public void toStop(View view) {
//        mLottieAnimationView.pauseAnimation();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mLottieAnimationView.cancelAnimation();
            }
        }).start();
    }
}
