package com.tangzy.tzymvp.util;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Administrator on 2018/1/8.
 */

public class GsonUtils {

    private static final String TAG = "GsonUtils";

    public static JSONObject toJson(Object src){
        Gson gson = new Gson();
        JSONObject object = null;
        String s = gson.toJson(src);
        try {
            object = new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
    /**
     * 由字符串得到JsonObject
     *
     * @param str
     * @return 得到的Obj 或者 null
     */
    public static JSONObject getObjFromStr(String str) {
        try{
            return new JSONObject(str);
        } catch (JSONException e){
            e.printStackTrace();
            System.out.println("get Obj From Str error: " + e);
            return null;
        }
//		return null;
    }

    private static final String DEFAULT_ERROR = "{\"retCode\":-2,\"msg\":\"JSON解析异常!\"}";

    public static String toJsonString(Object object) {
        Gson json = new Gson();

        try{
            return json.toJson(object);
        }catch(Exception e){
            Logger.e(TAG, "JSON解析异常!"+e);
            return DEFAULT_ERROR;
        }
    }


    /**
     * 将Map转化为Json
     *
     * @param map
     * @return String
     */
    public static <T> String mapToJson(Map<String, T> map) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(map);
        return jsonStr;
    }

}
