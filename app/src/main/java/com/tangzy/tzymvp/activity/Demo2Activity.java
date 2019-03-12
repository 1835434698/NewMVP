package com.tangzy.tzymvp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.tangzy.tzymvp.R;

public class Demo2Activity extends AppCompatActivity {

    private Button btn_luyin, btn_bofang;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_2);
        btn_luyin = findViewById(R.id.btn_luyin);
        btn_bofang = findViewById(R.id.btn_bofang);

    }

}
