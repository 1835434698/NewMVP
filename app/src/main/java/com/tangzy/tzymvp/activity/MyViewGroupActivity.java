package com.tangzy.tzymvp.activity;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.view.MyView;
import com.tangzy.tzymvp.view.MyViewGroup;

public class MyViewGroupActivity extends AppCompatActivity {

    private MyViewGroup myviewGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_viewgroup);
        myviewGroup = findViewById(R.id.myviewGroup);
        myviewGroup.autoRefresh();
    }
}
