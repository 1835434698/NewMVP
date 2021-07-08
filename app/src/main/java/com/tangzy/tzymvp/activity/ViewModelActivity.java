package com.tangzy.tzymvp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.util.Logger;
import com.tangzy.tzymvp.viewmodel.SharedViewModel;

import java.util.Random;

public class ViewModelActivity extends AppCompatActivity {
    private static final String TAG = "ViewModelActivity";
    private SharedViewModel model1;
    private SharedViewModel model2;

    private Button startV;
    private Random r = new Random(1);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewmodel);
        startV = findViewById(R.id.startV);
        model1 = new ViewModelProvider(this).get(SharedViewModel.class);
        model2 = new ViewModelProvider(this).get(SharedViewModel.class);
//
//        itemSelector.setOnClickListener(item -> {
//            model.select(item);
//        });
        model2.getSelected().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Logger.d(TAG, "onChanged s = "+s);

            }
        });
        startV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ran1 = r.nextInt(100);
                Logger.d(TAG, "onClick ran1 = "+ran1);

                model1.select(ran1+"");
            }
        });
    }
}
