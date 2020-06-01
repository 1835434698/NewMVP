package com.tangzy.jspdf;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import java.io.File;

/**
 * Created by RickBerg on 2018/11/1 0001.
 */
public class PdfActivity extends FragmentActivity {
    private static final String TAG = PdfActivity.class.getSimpleName();
    public final static int DOWNLOAD_OK = 10000;
    public final static int DOWNLOAD_FAIL = 10001;
    private String url = "";
    private WebView mWebView;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == DOWNLOAD_OK) {
                String filePath = (String) msg.obj;
                File pdfFile = new File(filePath);
                if (pdfFile.exists()) {
                    showPdf(filePath);
                } else {
                    Toast.makeText(PdfActivity.this,"文件读取失败，请稍后再试。", Toast.LENGTH_SHORT).show();
                }
            } else if (msg.what == DOWNLOAD_FAIL) {
                Toast.makeText(PdfActivity.this,"下载文件失败，请稍后再试。", Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_pdf);
//        initTitleBar();
        setTitle("合同");
        mWebView = findViewById(R.id.webView);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.clearCache(true);
        mWebView.clearFormData();
        getCacheDir().delete();
        getData();
        loadData();
    }

    private void getData() {
       String path = Environment.getExternalStorageDirectory().toString();
//        url = this.getIntent().getStringExtra("url");
        url = path+"/Allinmd/1589177170204/download/pdfile/216e908f890d2f4bb85900ec8ceafa8f.pdf";
        String title = this.getIntent().getStringExtra("title");
        if (!TextUtils.isEmpty(title)) {
            setTitle(title);
        }
    }

    private void loadData() {
//        String name = FileUtils.convertFilenameFromUrl(url);
//        final String fileName = FileUtils.CACHE + name + ".dat";
//        final File pdfFile = new File(fileName);
        final File pdfFile = new File(url);
        if (pdfFile.exists()) {
            Message msg = mHandler.obtainMessage();
            msg.what = DOWNLOAD_OK;
            msg.obj = url;
            mHandler.sendMessage(msg);
        } else {
//            PdfDetailPage page = new PdfDetailPage(this, new BaseHttpUtils.HttpCallBack<JSONObject>() {
//                @Override
//                public void onSuccess(JSONObject data) {
//                    hideWaitDialog();
//                    JSONObject body = JsonUtils.getJSONObject(data, "responseBody");
//                    String buffer = JsonUtils.getString(body, "byteFile");
//                    byte[] bytes = Base64.decode(buffer, Base64.DEFAULT);
//                    FileUtils.saveAsFile(bytes, fileName);
//                    if (pdfFile.exists()) {
//                        Message msg = mHandler.obtainMessage();
//                        msg.what = CommUtlis.DOWNLOAD_OK;
//                        msg.obj = fileName;
//                        mHandler.sendMessage(msg);
//                    }
//                }
//
//                @Override
//                public void onFailure(int errCode, String errMsg) {
//                    hideWaitDialog();
//                    Snackbar snackbar = createSnackbar("下载文件失败，请稍后再试。");
//                    JieYueSnackbarManager.show(snackbar);
//                }
//            });
//            page.initParams(url);
//            page.post();
        }
    }

    protected void showPdf(String filePath) {
        preView("file://" + filePath);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //清空所有Cookie
        CookieSyncManager.createInstance(this);  //Create a singleton CookieSyncManager within a context
        CookieManager cookieManager = CookieManager.getInstance(); // the singleton CookieManager instance
        cookieManager.removeAllCookie();// Removes all cookies.
        CookieSyncManager.getInstance().sync(); // forces sync manager to sync now

        mWebView.setWebChromeClient(null);
        mWebView.setWebViewClient(null);
        mWebView.getSettings().setJavaScriptEnabled(false);
        mWebView.clearCache(true);
    }

    /**
     * 预览pdf
     *
     * @param pdfUrl url或者本地文件路径
     */
    private void preView(String pdfUrl) {
        //如果是sd卡需要读写权限。
        //1.只使用pdf.js渲染功能，自定义预览UI界面
//        pdfUrl = "file:///android_asset/216e908f890d2f4bb85900ec8ceafa8f.pdf";
        mWebView.loadUrl("file:///android_asset/pdfjs/web/viewer.html?file=" + pdfUrl);
        //2.使用mozilla官方demo加载在线pdf
//        mWebView.loadUrl("http://mozilla.github.io/pdf.js/web/viewer.html?file=" + pdfUrl);
        //3.pdf.js放到本地/
//        viewer.html?file=some.pdf&_=someRandomNumberOrCurrentDatetime
//        mWebView.loadUrl("file:///android_asset/pdfjs/web/viewer.html?file=" + pdfUrl + "&_=" + new Date().getTime());
//        mWebView.loadUrl("file:///android_asset/pdfjs/web/viewer.html?file=" + pdfUrl);
        //4.使用谷歌文档服务
//        mWebView.loadUrl("http://172.18.100.98:8081/fintech-appbiz/repayH5/static/pdf/pdfjs/web/viewer.html?fil=" + pdfUrl);
    }
}
