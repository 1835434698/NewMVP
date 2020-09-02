package com.tangzy.tzymvp.activity;

import android.os.Build;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.utils.TextUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.bean.Info;
import com.tangzy.tzymvp.bean.InfoTab;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


public class GoogleGuvaActivity extends AppCompatActivity {
    private static final String TAG = "GoogleGuvaActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_guava);


    }

    public void onInit(View view) {
        Object respone = "[{\"name\":\"张三1\",\"age\":\"19\"},{\"name\":\"张三2\",\"age\":\"19\"},{\"name\":\"张三3\",\"age\":\"19\"},{\"name\":\"张三4\",\"age\":\"19\"}]";

        Map<String, Object> m = new ArrayMap<>();
        m.put("respone", respone);

        List<Info> infos = parseArray((String) respone, Info.class);
        InfoTab infoTab = new InfoTab();
        infoTab.setSex("男");
        infoTab.setName("张三5");
        infoTab.setAge(22);
        infos.add(infoTab);
        infos.add(infoTab);
        Log.d(TAG, "size = " + infos.size());

        Object r = getObject(m, "respone", null);

        List<String> lists = new ArrayList<>();
        lists.add("zhangsan");
        lists.add("lisi");
        lists.add("wangwu");
        lists.add("null");
//        lists.add(null);
//        lists.add(null);

        if (infos instanceof List) {
//            List list = (List) r;
            Log.d(TAG, "start1111");
            Collection<String> transform = Collections2.transform(infos, new Function<Object, String>() {

                
                @Override
                public String apply( Object input) {
                    if (input instanceof Info) {
                        Info adMap = (Info) input;
//                        String name = getMapString(adMap, "name");
                        String name = adMap.getName();
//                        String age = getMapString(adMap, "age");
                        String age = adMap.getAge() + "";
                        Info info = new Info();
                        info.setName(name);
                        info.setAge(Integer.parseInt(age));
                        return name;
                    }
                    return null;
                }
            });
            for (String string:transform){
                Log.d(TAG, "transform = " + string);
            }

            Collection<String> transform1 = Collections2.transform(infos, new Function<Object, String>() {
                
                @Override
                public String apply( Object input) {
                    if (input instanceof Info) {
                        Info adMap = (Info) input;
//                        String name = getMapString(adMap, "name");
                        String name = adMap.getName();
//                        String age = getMapString(adMap, "age");
                        String age = adMap.getAge() + "";
                        Info info = new Info();
                        info.setName(name);
                        info.setAge(Integer.parseInt(age));
                        return name;
                    }
                    return null;
                }
            });
            for (String string:transform1) {
                Log.d(TAG, "transform1 = " + string);
            }

//            infos.add(null);
//            infos.add(null);
            Collection<Info> filter = Collections2.filter(infos, Predicates.notNull());
            Collection<String> filter2 = Collections2.filter(lists, Predicates.equalTo(lists.get(1)));
            Collection<String> filter3 = Collections2.filter(lists, Predicates.equalTo("lists.get(3)"));
            Collection<String> filter8 = Collections2.filter(lists, Predicates.equalTo("wangwu"));
//            Collection<String> filter4 = Collections2.filter(lists, Predicates.equalTo(lists.get(4)));
            Collection<Info> filter10 = Collections2.filter(infos, Predicates.equalTo(infos.get(1)));
//            Collection<Info> filter11 = Collections2.filter(infos, Predicates.equalTo(infos.get(4)));
            Log.d(TAG, "filter = " + filter);

            Collection<Info> filter1 = Collections2.filter(infos, Predicates.notNull());
            Collection<String> filter5 = Collections2.filter(lists, Predicates.equalTo(lists.get(1)));
            Collection<String> filter6 = Collections2.filter(lists, Predicates.equalTo("lists.get(3)"));
            Collection<String> filter9 = Collections2.filter(lists, Predicates.equalTo("wangwu"));
//            Collection<String> filter7 = Collections2.filter(lists, Predicates.equalTo(lists.get(4)));
            Collection<Info> filter12 = Collections2.filter(infos, Predicates.equalTo(infos.get(1)));
//            Collection<Info> filter13 = Collections2.filter(infos, Predicates.equalTo(infos.get(4)));
            Log.d(TAG, "filter1 = " + filter1);


            Collection<String> wangwu = Collections2.filter(lists, input -> input.equals("wangwu"));
            Collection<String> filter14 = Collections2.filter(lists, input -> input.equals("list.get(3)"));
            Collection<String> aNull = Collections2.filter(lists, input -> input.equals("null"));
            Collection<Info> aNull2 = Collections2.filter(infos, input -> input.getName().equals(infos.get(1).getName()));
            if (aNull != null) {
                Log.d(TAG, "aNull = " + aNull);
            }

            Collection<String> wangwu1 = Collections2.filter(lists, input -> input.equals("wangwu"));
            Collection<String> filter141 = Collections2.filter(lists, input -> input.equals("list.get(3)"));
            Collection<String> aNull1 = Collections2.filter(lists, input -> input.equals("null"));
            Collection<Info> aNull12 = Collections2.filter(infos, input -> input.getName().equals(infos.get(1).getName()));
            if (aNull != null) {
                Log.d(TAG, "aNull1 = " + aNull1);
            }

            Collection<Info> filter4 = Collections2.filter(infos, Predicates.instanceOf(InfoTab.class));

            Collection<Info> filter41 = Collections2.filter(infos, Predicates.instanceOf(InfoTab.class));

            Log.d(TAG, "filter4 = " + filter4);
            ArrayList<String> transform_L = Lists.newArrayList(transform);
            ArrayList<String> transform1_L = Lists.newArrayList(transform1);
            ArrayList<String> transform2_L = Lists.newArrayList(transform);
            ArrayList<String> transform21_L = Lists.newArrayList(transform1);
            Log.d(TAG, "transform21_L = " + transform21_L);
            ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
            infoTab = new InfoTab();
            infoTab.setSex("男");
            infoTab.setName("张三5");
            infoTab.setAge(22);
            concurrentHashMap.put(infoTab, "111");
            infoTab = new InfoTab();
            infoTab.setSex("男");
            infoTab.setName("张三6");
            infoTab.setAge(22);
            concurrentHashMap.put(infoTab, "222");
            Map hhhhhh = Maps.filterKeys(concurrentHashMap, (Predicate<InfoTab>) input -> input.getName().equals("张三6"));
            Map hhhhh2 = Maps.filterKeys(concurrentHashMap, (Predicate<InfoTab>) input -> input.getName().equals("张三4"));


            Map hhhhhh1 = Maps.filterKeys(concurrentHashMap, (Predicate<InfoTab>) input -> input.getName().equals("张三6"));
            Map hhhhh21 = Maps.filterKeys(concurrentHashMap, (Predicate<InfoTab>) input -> input.getName().equals("张三4"));
            Log.d(TAG, "hhhhhh = " + hhhhhh);


            try {
                JSONArray jsonArray = new JSONArray((String) respone);
                JSONObject innerJsonObject = jsonArray.optJSONObject(0);

                ImmutableMap<String, String> ele = Maps.toMap(innerJsonObject.keys(), innerJsonObject::optString);
//                sourceDataList.add(ele);

                ImmutableMap<String, String> ele1 = Maps.toMap(innerJsonObject.keys(), innerJsonObject::optString);
                Log.d(TAG, "ele = " + ele);

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }






















    public <T> List<T> parseArray(String response, Class<T> object) {
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
