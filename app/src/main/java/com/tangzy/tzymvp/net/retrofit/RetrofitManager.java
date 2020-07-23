package com.tangzy.tzymvp.net.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * class
 *
 * @author Administrator
 * @date 2019/12/26
 */
public enum RetrofitManager {
    /**
     * 单例
     */
    INSTANCE;
//    public final String URL = "http://192.168.69.86/case_prpc/";
    public final String URL = "http://dev-api.allinmd.cn:18080/services/";

    public final int DEFAULT_MILLISECONDS = 12 * 1000;//默认超时时间
    private String URL_BASE = URL;

    public <T> T create(Class<T> service) {
        return create(service, URL_BASE);
    }
    public <T> T create(Class<T> service,String baseUrl) {
        //设置忽略证书
        HttpsUtils.SSLParams ssl = HttpsUtils.getSslSocketFactory(HttpsUtils.UnSafeTrustManager, null, null, null);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new SignInterceptor())//请求拦截器
                .addNetworkInterceptor(new VerificationInterceptor())
                .hostnameVerifier(HttpsUtils.UnSafeHostnameVerifier)
                .sslSocketFactory(ssl.sSLSocketFactory, ssl.trustManager)
                .connectTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
                .readTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
                .writeTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ResponseConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        return retrofit.create(service);
    }
}
