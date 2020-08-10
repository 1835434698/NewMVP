package com.tangzy.tzymvp.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tangzy.tzymvp.R;

public class Web1Activity extends AppCompatActivity {
    private static final String TAG = "WebActivity";

    private WebView mWebView;
    private WebSettings mWebSettings;
    private String fileName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_web);
        fileName = getIntent().getStringExtra("path");
        setContentView(R.layout.activity_webview);
        mWebView = findViewById(R.id.webview);

//        mWebView.setHorizontalScrollBarEnabled(false);
        // 设置支持JavaScript等
        mWebSettings = mWebView.getSettings();
//        mWebSettings.setSupportZoom(false);

        //允许使用JS
        mWebSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        mWebView.setWebViewClient(new WebViewClient());

        mWebView.loadUrl("file:///"+fileName);
        mWebView.addJavascriptInterface(new MyContact(), "android");
    }

    public void toJs(View view) {
        mWebView.evaluateJavascript("javascript:callJS()", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                Log.d(TAG, "onReceiveValue = "+value);
            }
        });
    }

    private final class MyContact {
        @JavascriptInterface
        public void goback(String index) {
            Log.d(TAG, index);
        }

        @JavascriptInterface
        public void showToast() {
            Log.d(TAG, "showToast");
        }
        @JavascriptInterface
        public void showToast(String index) {
            Log.d(TAG, "showToast ( "+index+" )");
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
