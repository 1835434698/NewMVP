package com.tangzy.tzymvp.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.servive.ForegroundService;

public class ServiceOActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_o);

    }

    public void onStartServicr1(View view) {
        startService(new Intent(this, ForegroundService.class));
    }

    public void onStartServicr2(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(new Intent(this, ForegroundService.class));
        }else {
            startService(new Intent(this, ForegroundService.class));
        }
    }
}
