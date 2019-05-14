package com.tangzy.testdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.tangzy.jnilibrary.jni.JNITest;


public class MainActivity extends AppCompatActivity {

    private TextView text_view;

    static {
        System.loadLibrary("JNITest");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_view = findViewById(R.id.text_view);
        text_view.postDelayed(new Runnable() {
            @Override
            public void run() {
                text_view.setText(JNITest.getJNIName());
            }
        }, 2000);
    }
}
