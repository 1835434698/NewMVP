package com.tangzy.tzymvp.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
