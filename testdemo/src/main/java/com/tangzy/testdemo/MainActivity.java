package com.tangzy.testdemo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.tangzy.jnilibrary.jni.JNITest;


public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text_view);
        textView.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setText(JNITest.getJNIName());
            }
        }, 2000);
    }
}
