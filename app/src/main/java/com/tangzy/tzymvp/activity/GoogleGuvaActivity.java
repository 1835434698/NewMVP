package com.tangzy.tzymvp.activity;

import android.os.Build;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.utils.MapUtils;
import com.alibaba.android.arouter.utils.TextUtils;
import com.alibaba.fastjson.JSON;
//import com.google.common.base.Function;
//import com.google.common.collect.Collections2;
import com.google.gson.JsonObject;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadLargeFileListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import com.tangzy.tzymvp.Constant;
import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.bean.Info;
import com.tangzy.tzymvp.bean.TzyBean;
import com.tangzy.tzymvp.util.Logger;

//import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GoogleGuvaActivity extends AppCompatActivity {
    private static final String TAG = "DownLoadActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_guava);


    }

    public void onInit(View view) {
        Object respone = "[{\"name\":\"张三\",\"age\":\"19\"},{\"name\":\"张三\",\"age\":\"19\"},{\"name\":\"张三\",\"age\":\"19\"},{\"name\":\"张三\",\"age\":\"19\"}]";

        Map<String, Object> m = new ArrayMap<>();
        m.put("respone", respone);

        List<Info> infos = parseArray((String) respone, Info.class);
        Log.d(TAG, "size = "+infos.size());

        Object r = getObject(m, "respone", null);

//        if (respone instanceof List){
//            List list = (List) r;
//            Collection<Info> transform = Collections2.transform(list, new Function<Object, Info>() {
//
//                @NullableDecl
//                @Override
//                public Info apply(@NullableDecl Object input) {
//                    if (input instanceof Map) {
//                        Map adMap = (Map) input;
//                        String name = getMapString(adMap, "name");
//                        String age = getMapString(adMap, "age");
//                        Info info = new Info();
//                        info.setName(name);
//                        info.setAge(Integer.parseInt(age));
//                        return info;
//                    }
//                    return null;
//                }
//            });
//            Log.d(TAG, "transform = "+transform);
//
//        }

    }

    public <T> List<T> parseArray(String response, Class<T> object){
        List<T> modelList = JSON.parseArray(response, object);
        return modelList;
    }


    public static String getMapString(Map paramMap, String key) {
        String result = "";
        if (paramMap != null && !TextUtils.isEmpty(key)) {
            result = paramMap.get(key) != null ? paramMap.get(key).toString() : "";
            result = replaceEscape(result, "\n\r");
        }
        return result;
    }

    public static String replaceEscape(String str, String escapeReg) {
        if (str != null) {
            String str1 = "\n";
            if (escapeReg.contains(str1)) {
                str = str.replace("\\n", "\n");
            }
            String str2 = "\r";
            if (escapeReg.contains(str2)) {
                str = str.replace("\\r", "\r");
            }
            String str3 = "<br>";
            if (escapeReg.contains(str3)) {
                str = str.replace("<br>", "");
            }
            String str4 = "\b";
            if (escapeReg.contains(str4)) {
                str = str.replace("\\b", "\b");
            }
            String str5 = "\t";
            if (escapeReg.contains(str5)) {
                str = str.replace("\\t", "\t");
            }
            String str6 = "\\";
            if (escapeReg.contains(str6)) {
                str = str.replace("\\", "");
            }
        }
        return str;
    }

    public static <K, V> V getObject(@Nullable Map<? super K, V> map, @Nullable K key,
                                     @Nullable V defaultValue) {
        V answer = null;
        if (isNotEmpty(map)) {
            try {
        /*
            throw NPT if the specified key is null and this map does not permit null keys
            e.g: Hashtable does not permit null keys;
                 HashMap permits null keys

            throw CCE if the key is of an inappropriate type for this map
            e.g: TreeMap
         */
                answer = map.get(key);
            } catch (NullPointerException | ClassCastException ex) {
                //ignore
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //Java 8 syntax
            return Optional.ofNullable(answer).orElse(defaultValue);
        } else {
            //Non java 8 syntax
            if (answer != null) {
                return answer;
            }
            return defaultValue;
        }
    }

    public static boolean isNotEmpty(@Nullable Map<?, ?> m) {
        return !isEmpty(m);
    }

    public static boolean isEmpty(@Nullable Map<?, ?> m) {
        return m == null || m.isEmpty();
    }



}
