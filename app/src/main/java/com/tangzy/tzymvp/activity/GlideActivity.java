package com.tangzy.tzymvp.activity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tangzy.tzymvp.R;

/**
 * class
 *
 * @author Administrator
 * @date 2020/1/10
 */
public class GlideActivity extends AppCompatActivity {

    private String imageUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1578637408263&di=1785affcfe1bff505fe55bdf23206596&imgtype=0&src=http%3A%2F%2Fimage.coolapk.com%2Fpicture%2F2016%2F1125%2F684014_1480088602_0362.png.m.jpg";

    private ImageView imageView1;
    private ImageView imageView2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        init();
    }

    private void init() {
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        Glide.with(this).load(imageUrl)
                .apply(new RequestOptions().placeholder(R.drawable.ic_launcher))
                .into(imageView1);
        Glide.with(this).load(imageUrl).into(imageView2);
    }
}
