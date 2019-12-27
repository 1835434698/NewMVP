package com.tangzy.tzymvp.net.retrofit;

import android.util.Log;

import com.tangzy.tzymvp.Constant;
import com.tangzy.tzymvp.net.NetUtil;
import com.tangzy.tzymvp.util.Logger;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * class
 * Created by Administrator on 2017/4/24.
 * 用于签名的拦截器，所有接口将都进行签名（GET和POST），流忽略
 *
 * @author Administrator
 * @date 2019/12/26
 */
public class SignInterceptor implements Interceptor {

    private static final String TAG = "Retrofit";
    //    private BodyUtils bodyUtils;
    private SimpleDateFormat DATETIME = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);

    public SignInterceptor() {
//        bodyUtils = new BodyUtils();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Logger.d(TAG, "SignInterceptor");
        Request request = chain.request();
        Logger.d(TAG, "SignInterceptor -> request = "+request.toString());
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
}
