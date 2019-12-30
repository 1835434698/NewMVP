package com.tangzy.tzymvp.activity;

import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.bean.User;
import com.tangzy.tzymvp.databinding.ActivityDatabingdBinding;
import com.tangzy.tzymvp.util.Logger;

/**
 * class
 *
 * @author Administrator
 * @date 2019/12/30
 */
public class DataBindingActivity extends AppCompatActivity {

    private static final String TAG = "DataBindingActivity";
    ActivityDatabingdBinding binding;

    Handler handler = new Handler();

    User user = new User();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = DataBindingUtil.setContentView(this, R.layout.activity_databingd);

        user.setName("11111111");
        user.setPassword("24324234");

        binding.setUserInfo(user);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Logger.d(TAG, "postDelayed");
                user.setPassword("123456");
                binding.setUserInfo(user);
            }
        }, 10*1000);
    }


}
