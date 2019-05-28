package com.tangzy.tzymvp.activity;

import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.util.CertifiUtils;

public class WebActivity extends AppCompatActivity {

    private WebView webview;
    protected WebSettings settings;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webview = findViewById(R.id.webview);


        webview.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        settings = webview.getSettings();
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setUseWideViewPort(true);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowContentAccess(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(false);
        settings.setDatabaseEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLoadsImagesAutomatically(true);

        webview.setWebChromeClient(new WebChromeClient(){

        });
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                Log.d("tangzy", "onReceivedSslError");
//                super.onReceivedSslError(view, handler, error);
//                handler.proceed();
                CertifiUtils.INSTANCE.onCertificateOfVerification(handler, view.getUrl());
            }
        });

//        webview.loadUrl("https://m.xiangqianjinfu.com/appstatic/index.html#/info/aboutXq/informationDisclosureChild?hideHeader=1");
//        webview.loadUrl("https://app.xiangqianjinfu.com/xqAppServer/wap/info/index.html#/index");
//        webview.loadUrl("https://www.baidu.com");
        webview.loadUrl("https://192.168.70.85:8443/");
    }

}
