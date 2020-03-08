package com.tangzy.tzymvp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.servive.DemoIntentService;

public class YuanChengActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yuancheng);

    }

    public void backResult(View view) {
        Intent intent = new Intent();
        intent.putExtra("hhhhhh", "9999999");
        setResult(Activity.RESULT_OK, intent);
        finish();

    }

    public void startIntentService(View view) {
        Intent intent = new Intent(this, DemoIntentService.class);
        intent.putExtra("qqqqqq", 40);
        startService(intent);
    }
}
