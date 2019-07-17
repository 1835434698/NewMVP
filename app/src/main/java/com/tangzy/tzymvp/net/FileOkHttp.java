package com.tangzy.tzymvp.net;

import com.tangzy.tzymvp.Constant;

import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import cn.com.okhttp.filelibrary.helper.ProgressHelper;
import cn.com.okhttp.filelibrary.listener.impl.UIProgressListener;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by tangzy on 2016/8/11.
 */
public class FileOkHttp {


    private static String url;
    public static void fileDown(String uri, Map<String, String> httpParams, final UIProgressListener uiProgressResponseListener, final Callback callback ){
        url = Constant.URL+uri;
        try {
            StringBuilder sb = new StringBuilder();
            if (httpParams != null && !httpParams.isEmpty()) {
                Set<String> strings = httpParams.keySet();
                for (String key : strings) {
                    sb.append(key);
                    if (httpParams.get(key) != null){
                        sb.append("=");
                        sb.append(URLEncoder.encode(httpParams.get(key), "UTF-8"));
                    }
                    sb.append("&");
                }
                if(url.indexOf("?") >= 0){
                    if(sb.length() > 0){
                        url = url + "&" + sb.substring(0, sb.length() - 1);
                    }
                } else if(sb.length() > 0){
                    url = url + "?" + sb.substring(0, sb.length() - 1);
                }
            }
        }catch (Exception e){

        }

        //构造请求
        final Request request1 = new Request.Builder().url(url).build();
        //包装Response使其支持进度回调
        ProgressHelper.addProgressResponseListener(client1, uiProgressResponseListener).newCall(request1).enqueue(callback);
    }

    private static final OkHttpClient client1 = new OkHttpClient.Builder()
            .connectTimeout(1000, TimeUnit.MINUTES)
            .readTimeout(1000, TimeUnit.MINUTES)
            .writeTimeout(1000, TimeUnit.MINUTES)
            .build();



}
