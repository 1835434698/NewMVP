package com.tangzy.pdfviewpage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import es.voghdev.pdfviewpager.library.PDFViewPager;
import es.voghdev.pdfviewpager.library.R;
import es.voghdev.pdfviewpager.library.adapter.BasePDFPagerAdapter;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;

public class PdfViewPageActivitry  extends AppCompatActivity {
    VerticalViewPager pdfViewPager;
    BasePDFPagerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewpage);

        pdfViewPager = findViewById(R.id.pdfViewPager);
        String url = this.getIntent().getStringExtra("url");
        Log.d("PdfViewPageActivitry", "url = "+url);
        adapter = new PDFPagerAdapter(this, url);
        pdfViewPager.setAdapter(adapter);



        Intent intent = new Intent(this, RecycleViewActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
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
