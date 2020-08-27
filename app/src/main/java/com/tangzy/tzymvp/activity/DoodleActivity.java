package com.tangzy.tzymvp.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.tangzy.doodlelib.DoodleFragment;
import com.tangzy.tzymvp.R;

public class DoodleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doodle);


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment, DoodleFragment.newInstance(getIntent().getStringExtra("url")));
        fragmentTransaction.commit();
    }
}
