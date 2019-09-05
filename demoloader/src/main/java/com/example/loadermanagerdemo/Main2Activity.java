package com.example.loadermanagerdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        findViewById(R.id.addClick).setOnClickListener(this);
        listView = (ListView) findViewById(R.id.list_item);
    }

    @Override
    public void onClick(View v) {

    }
}
