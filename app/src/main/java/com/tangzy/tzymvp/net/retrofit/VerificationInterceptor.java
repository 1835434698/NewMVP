package com.tangzy.tzymvp.net.retrofit;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.tangzy.tzymvp.util.Logger;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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

    /**
     * Object转成String
     *
     * @param value object对象
     * @return  返回值
     */
    public static String convertToString(Object value) {
        return convertToString(value, "");
    }
    /**
     * Object转成String
     *
     * @param value
     * @return
     */
    public static String convertToString(Object value, String defaultStr) {
        if (value == null || "".equals(value.toString().trim()) || "null".equals(value.toString().trim())) {
            return defaultStr;
        }
        try {
            return value.toString();
        } catch (Exception e) {
            return defaultStr;
        }
    }

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

//
//                    Gson gson = new GsonBuilder()
////                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
//                            .registerTypeAdapter(
//                                    new TypeToken<Map<String, Object>>(){}.getType(),
//                                    new JsonDeserializer<Map<String, Object>>() {
//                                        @Override
//                                        public Map<String, Object> deserialize(
//                                                JsonElement json, Type typeOfT,
//                                                JsonDeserializationContext context) throws JsonParseException {
//                                            Logger.i("Retrofit", "deserialize -> src");
//
//                                            Map<String, Object> treeMap = new TreeMap<>();
//                                            JsonObject jsonObject = json.getAsJsonObject();
//                                            Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
//                                            for (Map.Entry<String, JsonElement> entry : entrySet) {
//                                                treeMap.put(entry.getKey(), entry.getValue());
//                                            }
//                                            return treeMap;
//                                        }
//                                    })
//                            .create();//使用 gson coverter，统一日期请求格式
//
//                    HashMap hashMap = gson.fromJson(bodyString, HashMap.class);
//
//                    String  responseData = gson.toJson(hashMap);

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
