package com.tangzy.tzymvp.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.servive.DemoServive;
import com.tangzy.tzymvp.util.Logger;
import com.tangzy.tzymvp.view.MyView;

public class DemoActivity extends AppCompatActivity {

    private MyView myview;
    private int kfc=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_demo);

        myview = findViewById(R.id.myview);
        myview.setOnClickListener(v -> {
            Logger.d("tangzy", "Click");
            Intent intent = new Intent(this, DemoServive.class);
            intent.putExtra("kfc", kfc);
            kfc++;
            startService(intent);
            ServiceConnection con= new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    Logger.d("tangzy", "onServiceConnected");

                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    Logger.d("tangzy", "onServiceDisconnected");

                }
            };
//            bindService(intent, con, Context.BIND_AUTO_CREATE);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.d("tangzy", "onDestroy");
    }
}
