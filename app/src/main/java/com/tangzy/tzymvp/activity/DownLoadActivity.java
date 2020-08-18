package com.tangzy.tzymvp.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadLargeFileListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.connection.DefaultConnectionCountAdapter;
import com.liulishuo.filedownloader.services.ForegroundServiceConfig;
import com.liulishuo.filedownloader.stream.FileDownloadOutputStream;
import com.liulishuo.filedownloader.util.FileDownloadHelper;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import com.tangzy.tzymvp.Constant;
import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.servive.ForegroundService;
import com.tangzy.tzymvp.util.Logger;

import java.io.File;
import java.io.IOException;

public class DownLoadActivity extends AppCompatActivity {
    private static final String TAG = "DownLoadActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

    }

    public void onInit(View view) {
        FileDownloader.setupOnApplicationOnCreate(getApplication())
//                .foregroundServiceConfig(new ForegroundServiceConfig.Builder().build())
                .commit();

        //关闭内部日志打印
        FileDownloadLog.NEED_LOG = false;

    }

    public void onStartServicr(View view) {
        FileDownloader.getImpl().create("https://codeload.github.com/lingochamp/FileDownloader/zip/master")
                .setPath(Constant.path)
//                .setWifiRequired(isWifiRequired)
//                .setAutoRetryTimes(autoRetryTimes)
                .setListener(new FileDownloadLargeFileListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                        Logger.d(TAG, "paused");

                    }

                    @Override
                    protected void progress(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                        Logger.d(TAG, "paused");

                    }

                    @Override
                    protected void paused(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                        Logger.d(TAG, "paused");

                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        Logger.d(TAG, "completed");

                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        Logger.e(TAG, "error = "+e.getLocalizedMessage());

                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        Logger.d(TAG, "warn");

                    }
                })
                .start();
    }
}
