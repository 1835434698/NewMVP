package com.tangzy.pdfrecyclerview;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tangzy.pdfrecyclerview.adapter.LinearLayoutManagerRecycleview;
import com.tangzy.pdfrecyclerview.adapter.OnScrollListenerRecycleView;
import com.tangzy.pdfrecyclerview.adapter.PDFRecycleAdapter;
import com.tangzy.pdfrecyclerview.R;

public class RecycleViewActivity extends AppCompatActivity {
    private PDFRecycleAdapter adapter;
    private RecyclerView recycleView;
    private TextView tvNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recycleview);
        String url = this.getIntent().getStringExtra("url");
        recycleView = findViewById(R.id.recycleView);
        tvNum = findViewById(R.id.tvNum);
        adapter = new PDFRecycleAdapter(this, url);
        //1线性
        LinearLayoutManagerRecycleview layoutManager = new LinearLayoutManagerRecycleview(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);//

        recycleView.setLayoutManager(layoutManager);

        recycleView.setAdapter(adapter);
        recycleView.addOnScrollListener(new OnScrollListenerRecycleView(){
            @Override
            public void onAdapterChanged(int position) {
                super.onAdapterChanged(position);
                setTextCount(position);
            }
        });
        setTextCount(0);
    }

    private void setTextCount(int lastVisibleItemPosition) {
        Log.d("tagnzy", "lastVisibleItemPosition========================= "+(lastVisibleItemPosition+1)+"/"+adapter.getItemCount());
        tvNum.setText((lastVisibleItemPosition+1)+"/"+adapter.getItemCount());
    }

}
