package com.tangzy.tzymvp.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WebActivity extends AppCompatActivity {
    private static final String TAG = "WebActivity";

    private WebView mWebView;
    private WebSettings mWebSettings;
    private String fileName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_web);
        fileName = getIntent().getStringExtra("path");
        mWebView = new WebView(this);
        setContentView(mWebView);

        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.getSettings().setSupportZoom(false);
        // 设置支持JavaScript等
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new MyWebViewClient());

        mWebView.loadUrl("file:///"+fileName);
        mWebView.addJavascriptInterface(new MyContact(), "ui");
    }

    private final class MyContact {
        @JavascriptInterface
        public void goback(String index) {
            Log.d("11111111", index);
        }

        @JavascriptInterface
        public void goback() {
//            goback();
            finish();
        }

        @JavascriptInterface
        public void getPictuer() {
//            dsdsfa
        }


    }

    class MyWebViewClient extends WebViewClient {
        public MyWebViewClient() {
            super();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        @Override
        public void onPageCommitVisible(WebView view, String url) {
            super.onPageCommitVisible(view, url);
        }
    }

}
