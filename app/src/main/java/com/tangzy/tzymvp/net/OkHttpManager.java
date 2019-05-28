package com.tangzy.tzymvp.net;


import android.os.Environment;
import android.os.Handler;
import android.os.Looper;

import com.tangzy.tzymvp.Constant;
import com.tangzy.tzymvp.net.helper.ProgressHelper;
import com.tangzy.tzymvp.net.listener.impl.UIProgressListener;
import com.tangzy.tzymvp.util.GsonUtils;
import com.tangzy.tzymvp.util.Logger;
import com.tangzy.tzymvp.util.Toasts;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;
import okio.BufferedSink;

/**
 * Created by Administrator on 2017/10/26.
 */

public enum OkHttpManager {
    INSTANCE;

    private final String TAG = "OkHttpManager";
    private String url;
    private OkHttpClient okHttpClient;

    private PersistentCookieStore cookieStore;
    private CookieJarImpl cookieJarImpl;
    private Handler mDeliverHandler;

    private Map<Integer, ResponseListener> map = new HashMap<>();
    private static final String CONTENT_TYPE = "text/html;charset=utf-8; charset=utf-8";

    OkHttpManager() {
//        okHttpClient = new OkHttpClient();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (cookieStore == null) {
            cookieStore = new PersistentCookieStore(Constant.app);
        }
        cookieJarImpl = new CookieJarImpl(cookieStore);
        builder.connectTimeout(10, TimeUnit.SECONDS).readTimeout(20, TimeUnit.SECONDS).cookieJar(cookieJarImpl)
                .addInterceptor(new Interceptor() {
                    @Override
            public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    if (!NetUtil.checkNetType(Constant.app)) {
                        int offlineCacheTime = 60;//离线的时候的缓存的过期时间
                        request = request.newBuilder()
//                        .cacheControl(new CacheControl
//                                .Builder()
//                                .maxStale(60,TimeUnit.SECONDS)
//                                .onlyIfCached()
//                                .build()
//                        ) 两种方式结果是一样的，写法不同
                                .header("Cache-Control", "public, only-if-cached, max-stale=" + offlineCacheTime)
                                .build();
                    }
                    return chain.proceed(request);
            }
        }).addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                int onlineCacheTime = 30;//在线的时候的缓存过期时间，如果想要不缓存，直接时间设置为0
                return response.newBuilder()
                        .header("Cache-Control", "public, max-age="+onlineCacheTime)
                        .removeHeader("Pragma")
                        .build();
            }
        }).cache(new Cache(new File(Environment.getExternalStorageDirectory() + "/okttpcaches"), 1024 * 1024 * 20));
//                .build();
        okHttpClient = builder.build();

    }

    public void asyncRequest(final String uri, final JSONObject httpParams, final HashMap<String, File> files, final ResponseListener listener) {
        asyncRequest(uri, httpParams, files, listener, true, false);
    }
    public void asyncRequest(final String uri, final JSONObject httpParams, final ResponseListener listener) {
        asyncRequest(uri, httpParams, listener, true);
    }

    public void asyncRequest(final String uri, final JSONObject httpParams, final ResponseListener listener, final boolean isPost) {
        asyncRequest(uri, httpParams, null, listener, isPost, false);
    }

    public void asyncRequest(final String uri, final JSONObject httpParams, final HashMap<String, File> files, final ResponseListener listener, final boolean isPost, final boolean isEnc) {
        if (!NetUtil.checkNetType(Constant.app)) {
            Toasts.showToastLong(Constant.MESSSAGENET);
            listener.onErr(Constant.ERRORCODE, Constant.MESSSAGENET, uri);
            return;
        }
        mDeliverHandler = new Handler(Looper.getMainLooper());
        map.put(uri.hashCode(), listener);
        getResult(uri, httpParams, files, isPost, isEnc);
    }

    private void getResult(final String uri, JSONObject httpParams, final HashMap<String, File> files, boolean isPost, boolean isEnc) {
        final ResponseListener listener = map.get(uri.hashCode());
        map.remove(uri.hashCode());
        if (listener == null) {
            return;
        }
        url = Constant.url + uri;
        if (cookieStore == null) {
            cookieStore = new PersistentCookieStore(Constant.app);
        }
        //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
        Request request = null;
        Logger.d(TAG, "onReq = " + httpParams);
        Logger.d(TAG, "url = " + url);
        try {
            if (isPost) {
                if (files != null){
                    request = getMultipartBody(httpParams, files, url);
                }else {
                    request = httpPost(httpParams, url, isEnc);
                }
            } else {
                request = httpGet(httpParams, url, isEnc);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Logger.e(TAG, uri + "error = " + e.getMessage());
            if (listener != null) {
                listener.onErr(Constant.ERRORCODE, e.getMessage(), uri);
            }
        }
//        okHttpClient.newCall(request).execute();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mDeliverHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (listener != null) {
                            listener.onErr(Constant.ERRORCODE, e.getMessage(), uri);
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String strResult = response.body().string();
                Logger.d(TAG, uri + "response = " + strResult);
                mDeliverHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        handleResult(strResult, listener, uri);
                    }
                });
            }
        });
    }

    private Request getMultipartBody(JSONObject httpParams, HashMap<String, File> files, String url) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        Iterator<Map.Entry<String, File>> iterator = files.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, File> entry = iterator.next();
            builder.addFormDataPart(entry.getKey(), entry.getValue().getName(), RequestBody.create(MediaType.parse("image/png"), entry.getValue()));
            Logger.d(TAG, url + "response = {"+ entry.getKey()+":"+ entry.getValue().getName()+"}");
        }
        if (httpParams != null && httpParams.length() > 0) {
            Iterator<String> sIterator = httpParams.keys();
            String key;
            String value;
            while (sIterator.hasNext()) {
                // 获得key
                key = sIterator.next();
                if (!"uri".equals(key)){
                    // 根据key获得value, value也可以是JSONObject,JSONArray,使用对应的参数接收即可
                    value = httpParams.optString(key);
                    builder.addFormDataPart(key, value);
                }
            }
        }
        return new Request.Builder().url(url).post(builder.build()).build();
    }

    private void handleResult(final String strResult, final ResponseListener listener, final String uri) {
        try {
//            Gson gson = new Gson();
//            BaseBean jsonObject = gson.fromJson(strResult, BaseBean.class);
            JSONObject jsonObject = new JSONObject(strResult);


            if ("ok".equals(jsonObject.optString("status"))) {
                if (listener != null) {
                    listener.onResp(strResult, uri);
                }
            } else {
                String msg = jsonObject.optString("msg");
                Toasts.showToastShort(msg);
                if (listener != null) {
                    listener.onErr(1, msg, uri);
                }
            }
        } catch (Exception e) {
            Toasts.showToastLong(Constant.MESSSAGEJSON);
            if (listener != null) {
                listener.onErr(Constant.ERRORCODE, Constant.MESSSAGEJSON, uri);
            }
        }

    }

    private Request httpPost(JSONObject httpParams, String url, boolean isEnc) throws UnsupportedEncodingException {
        Request request;
        if (isEnc) {
            FormBody.Builder builder = new FormBody.Builder();
            String aes = "";
            if (httpParams != null && httpParams.length() > 0) {
                aes = httpParams.toString();
//                aes = AES.encryptAES(httpParams.toString(), SA.AESK);
//                Logger.d(TAG, "onReq aes = "+aes);
                builder.add("key", aes);
            }
            Request.Builder builder1 = new Request.Builder().url(url);
            request = builder1.post(builder.build()).build();
        } else {
            FormBody.Builder builder = new FormBody.Builder();
            if (httpParams != null && httpParams.length() > 0) {
                Iterator<String> sIterator = httpParams.keys();
                String key;
                String value;
                while (sIterator.hasNext()) {
                    // 获得key
                    key = sIterator.next();
                    if (!"uri".equals(key)){
                        // 根据key获得value, value也可以是JSONObject,JSONArray,使用对应的参数接收即可
                        value = httpParams.optString(key);
                        builder.add(key, value);
                    }
                }
            }
            request = new Request.Builder().url(url).post(builder.build()).build();
        }
        return request;
    }

    private static String getParamJson(Map map) {
        if (map == null) {
            map = new LinkedHashMap<>();
        }
        String paramJson = GsonUtils.toJsonString(map);
        return paramJson;
    }

    private Request httpGet(JSONObject httpParams, String url, boolean isEnc) throws UnsupportedEncodingException {
        Request request = null;
        StringBuilder sb = new StringBuilder();
        if (isEnc) {
            String aes = "";
            if (httpParams != null && httpParams.length() > 0) {
                aes = httpParams.toString();
//                aes = AES.encryptAES(httpParams.toString(), SA.AESK);
                Logger.d(TAG, "onReq aes = " + aes);
                sb.append("key");
                sb.append("=");
                sb.append(URLEncoder.encode(aes, "UTF-8"));
            }
            if (url.indexOf("?") >= 0) {
                if (sb.length() > 0) {
                    url = url + "&" + sb.substring(0, sb.length() - 1);
                }
            } else if (sb.length() > 0) {
                url = url + "?" + sb.substring(0, sb.length() - 1);
            }
            Request.Builder builder1 = new Request.Builder().url(url);
            request = builder1.build();
        } else {
            if (httpParams != null && httpParams.length() > 0) {
                Iterator<String> sIterator = httpParams.keys();
                String key;
                String value;
                while (sIterator.hasNext()) {
                    // 获得key
                    key = sIterator.next();
                    if (!"uri".equals(key)) {
                        // 根据key获得value, value也可以是JSONObject,JSONArray,使用对应的参数接收即可
                        value = httpParams.optString(key);
                        sb.append(key);
                        sb.append("=");
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                }
                if (url.indexOf("?") >= 0) {
                    if (sb.length() > 0) {
                        url = url + "&" + sb.substring(0, sb.length() - 1);
                    }
                } else if (sb.length() > 0) {
                    url = url + "?" + sb.substring(0, sb.length() - 1);
                }
                request = new Request.Builder().url(url).build();
            }
        }
        return request;
    }


    /**
     * Returns a new request body that transmits {@code content}.
     */
    public RequestBody create(final MediaType contentType, final byte[] content) {
        return create(contentType, content, 0, content.length);
    }

    /**
     * Returns a new request body that transmits {@code content}.
     */
    public RequestBody create(final MediaType contentType, final byte[] content,
                              final int offset, final int byteCount) {
        if (content == null) {
            throw new NullPointerException("content == null");
        }
        Util.checkOffsetAndCount(content.length, offset, byteCount);
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return contentType;
            }

            @Override
            public long contentLength() {
                return byteCount;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.write(content, offset, byteCount);
            }
        };
    }

    public void fileDown(String url, Map<String, String> httpParams, final UIProgressListener uiProgressResponseListener, final Callback callback) {
//        String url = Constant.url + uri;
        try {
            StringBuilder sb = new StringBuilder();
            if (httpParams != null && !httpParams.isEmpty()) {
                Set<String> strings = httpParams.keySet();
                for (String key : strings) {
                    sb.append(key);
                    if (httpParams.get(key) != null) {
                        sb.append("=");
                        sb.append(URLEncoder.encode(httpParams.get(key), "UTF-8"));
                    }
                    sb.append("&");
                }
                if (url.indexOf("?") >= 0) {
                    if (sb.length() > 0) {
                        url = url + "&" + sb.substring(0, sb.length() - 1);
                    }
                } else if (sb.length() > 0) {
                    url = url + "?" + sb.substring(0, sb.length() - 1);
                }
            }
        } catch (Exception e) {

        }

        //构造请求
        final Request request1 = new Request.Builder().url(url).build();
        //包装Response使其支持进度回调
        ProgressHelper.addProgressResponseListener(okHttpClient, uiProgressResponseListener).newCall(request1).enqueue(callback);
    }

    public void removeListener(ResponseListener listener) {
        if (map.containsValue(listener)){
            Collection<ResponseListener> values = map.values();
            values.remove(listener);
        }

    }
}
