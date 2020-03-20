package com.tangzy.tzymvp.net.retrofit;


import com.tangzy.tzymvp.util.Logger;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * class
 * 用于验证的拦截器，所有接口将都进行验证
 *
 * @author Administrator
 * @date 2019/12/27
 */
public class VerificationInterceptor implements Interceptor {
    private final String TAG = "Retrofit";
    @Override
    public Response intercept(Chain chain) throws IOException {
        Logger.d(TAG, "VerificationInterceptor");
        Request request = chain.request();
        Response response = chain.proceed(request);
        //isSuccessful () ; 如果代码在[200..300]中，则返回true，这意味着请求已成功接收、理解和接受。
        if (response.isSuccessful()) {
            //返回ResponseBody
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                try {
                    //获取bodyString
                    BufferedSource source = responseBody.source();
                    source.request(Long.MAX_VALUE);
                    Buffer buffer = source.buffer();
                    Charset charset = Charset.forName("UTF-8");
                    MediaType contentType = responseBody.contentType();
                    if (contentType != null) {
                        charset = contentType.charset(charset);
                    }
                    String bodyString = buffer.clone().readString(charset);
                    //我这里是base64解码  具体情况自己定义
                    //base64解码
//                    String responseData = new String(EncodeUtils.base64Decode(bodyString));
                    String responseData = bodyString;
                    //生成新的ResponseBody
                    ResponseBody newResponseBody = ResponseBody.create(contentType, responseData.trim());
                    //response
                    response = response.newBuilder().body(newResponseBody).build();
                    Logger.d(TAG, "解密后数据: " + responseData);

                } catch (IOException e) {
                    //如果发生异常直接返回
                    e.printStackTrace();
                    return response;
                }
            } else {
                Logger.d(TAG, "onHttpResultResponse: 响应体为空");
            }
        }
        return response;
//        int onlineCacheTime = 30;//在线的时候的缓存过期时间，如果想要不缓存，直接时间设置为0
//        return response.newBuilder()
//                .header("Cache-Control", "public, max-age="+onlineCacheTime)
//                .removeHeader("Pragma")
//                .build();
    }
}
