package com.tangzy.tzymvp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.tangzy.tzymvp.R;

public class Demo2Activity extends AppCompatActivity {

    private Button btnLuyin, btnBofang;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_2);
        btnLuyin = findViewById(R.id.btn_luyin);
        btnBofang = findViewById(R.id.btn_bofang);

    }

}
