package com.tangzy.tzymvp.net.retrofit;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * class
 *
 * @author Administrator
 * @date 2019/12/26
 */
public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private static final String TAG = "Retrofit";
    private final Gson gson;
    private Type type;


    GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        Log.d(TAG, "GsonResponseBodyConverter");
        try {
            //success表示成功返回，继续用本来的Model类解析
            return gson.fromJson(response, type);
        } finally {
        }
    }
}
