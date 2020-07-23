package com.tangzy.pdfviewpage;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.tangzy.pdfviewpage.adapter.RecycleAdapter;

import es.voghdev.pdfviewpager.library.PDFViewPager;
import es.voghdev.pdfviewpager.library.R;
import es.voghdev.pdfviewpager.library.adapter.BasePDFPagerAdapter;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;

public class PdfViewPageActivitry  extends AppCompatActivity {
    PDFViewPager pdfViewPager;
    PDFPagerAdapter adapter;
    RecyclerView bottom;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewpage);

        pdfViewPager = (PDFViewPager) findViewById(R.id.pdfViewPager);
        String url = this.getIntent().getStringExtra("url");
        Log.d("PdfViewPageActivitry", "url = "+url);
        adapter = new PDFPagerAdapter(this, url);
        pdfViewPager.setAdapter(adapter);

        initRecycleView();
    }

    private RecycleAdapter bottomAdapter;
    private void initRecycleView() {
        bottom = findViewById(R.id.bottom);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        bottom.setLayoutManager(layoutManager);

//2 分割线
//        recycleView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));//分割线

        bottomAdapter = new RecycleAdapter(this, adapter);
        bottom.setAdapter(bottomAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (adapter != null) {
            adapter.close();
            adapter = null;
        }
    }
}
