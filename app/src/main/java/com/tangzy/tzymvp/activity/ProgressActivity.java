package com.tangzy.tzymvp.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.view.CircularProgressView;

public class ProgressActivity extends AppCompatActivity {
    private CircularProgressView progress_circular;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        init();
    }

    private void init() {
        progress_circular = findViewById(R.id.progress_circular);
        progress_circular.setProgress(50);
    }
}
