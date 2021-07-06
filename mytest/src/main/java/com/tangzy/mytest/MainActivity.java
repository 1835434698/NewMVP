package com.tangzy.mytest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Handler handler = new Handler(){

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        String json1 = "[{\"node_id\":\"bdf3d880-0ba5-61dc-ff9e-f32e8ccd3fd0\",\"interval_type\":1,\"interval\":1,\"calc_time\":\"1\",\"content_list\":[{\"content_id\":\"cd40544d-d20f-3406-c9b1-71b17852a435\",\"content_type\":2,\"content\":{\"questionnaire_id\":1,\"questionnaire_name\":\"疼痛评估VAS量表\",\"questionnaire_type\":1,\"questionnaire_config_id\":1,\"questionnaire_url\":\"\",\"belong_type\":0}},{\"content_id\":\"24f0f8ee-eb43-e35a-47fb-cf44f032e528\",\"content_type\":1,\"content\":{\"article_id\":11,\"article_source\":\"唯医\",\"article_type\":0,\"article_title\":\"髖关节置换康复锻炼方案(术后1-3天)\",\"article_url\":\"https://m31.allinmd.cn/followUp?manualId=1581504433062\",\"article_content\":\"\",\"article_uuid\":\"\",\"wechat_appid\":\"\"}}]},{\"node_id\":\"64ee7ac5-54b8-d607-3c96-b5789dd20d82\",\"interval_type\":1,\"interval\":4,\"calc_time\":\"4\",\"content_list\":[{\"content_id\":\"e637b0aa-8f96-7c7f-23b9-d5beaa8d548b\",\"content_type\":2,\"content\":{\"questionnaire_id\":1,\"questionnaire_name\":\"疼痛评估VAS量表\",\"questionnaire_type\":1,\"questionnaire_config_id\":1,\"questionnaire_url\":\"\",\"belong_type\":0}},{\"content_id\":\"5e06cf98-d624-c58c-30d9-b7f26d548b09\",\"content_type\":1,\"content\":{\"article_id\":12,\"article_source\":\"唯医\",\"article_type\":0,\"article_title\":\"髖关节置换康复锻炼方案(术后4-7天)\",\"article_url\":\"https://m31.allinmd.cn/followUp?manualId=1581505166354\",\"article_content\":\"\",\"article_uuid\":\"\",\"wechat_appid\":\"\"}}]},{\"node_id\":\"eaa76f75-f9a0-f46a-3198-0f29b1959bfa\",\"interval_type\":1,\"interval\":7,\"calc_time\":\"7\",\"content_list\":[{\"content_id\":\"8015e9ea-1a6e-530f-042d-8a27e3dcfc8d\",\"content_type\":2,\"content\":{\"questionnaire_id\":1,\"questionnaire_name\":\"疼痛评估VAS量表\",\"questionnaire_type\":1,\"questionnaire_config_id\":1,\"questionnaire_url\":\"\",\"belong_type\":0}},{\"content_id\":\"c44464f0-5074-75bb-947a-29bc7c64777a\",\"content_type\":1,\"content\":{\"article_id\":13,\"article_source\":\"唯医\",\"article_type\":0,\"article_title\":\"髖关节置换康复锻炼方案(术后7－14天)\",\"article_url\":\"https://m31.allinmd.cn/followUp?manualId=1581508061673\",\"article_content\":\"\",\"article_uuid\":\"\",\"wechat_appid\":\"\"}}]},{\"node_id\":\"8bfca172-8090-3e67-fe67-f038fd8a0baf\",\"interval_type\":2,\"interval\":2,\"calc_time\":\"14\",\"content_list\":[{\"content_id\":\"4198db79-c30d-be17-95ff-dac41ea62dd0\",\"content_type\":3,\"content\":{\"data\":\"为了保证您能够顺利康复，请于近期回院复查!\"}},{\"content_id\":\"c727b45c-c9d5-1ec0-bfd4-c52fb652d758\",\"content_type\":1,\"content\":{\"article_id\":14,\"article_source\":\"唯医\",\"article_type\":0,\"article_title\":\"髖关节置换康复锻炼方案(术后2-4周)\",\"article_url\":\"https://m31.allinmd.cn/followUp?manualId=1581508525575\",\"article_content\":\"\",\"article_uuid\":\"\",\"wechat_appid\":\"\"}}]},{\"node_id\":\"df6cb3ce-aab7-213a-2d9c-aaacdb3d886a\",\"interval_type\":2,\"interval\":4,\"calc_time\":\"28\",\"content_list\":[{\"content_id\":\"9b53e081-92e9-83eb-6bb0-35c7d74f528b\",\"content_type\":3,\"content\":{\"data\":\"为了保证您能够顺利康复，请于近期回院复查!\"}},{\"content_id\":\"efcf6781-9c91-9043-7035-9ad3221fa54c\",\"content_type\":1,\"content\":{\"article_id\":15,\"article_source\":\"唯医\",\"article_type\":0,\"article_title\":\"髖关节置换康复锻炼方案(术后4周)\",\"article_url\":\"https://m31.allinmd.cn/followUp?manualId=1581508670360\",\"article_content\":\"\",\"article_uuid\":\"\",\"wechat_appid\":\"\"}},{\"content_id\":\"c4dd45c3-3db3-44e6-8da5-46e3870149a7\",\"content_type\":1,\"content\":{\"article_id\":1597468172553,\"article_source\":\"\",\"article_type\":0,\"article_title\":\"这是一个测试内容一2222\",\"article_url\":\"https://test-patient.allinmed.cn/patientEducation/patientDetail?id=1597468172553\",\"article_uuid\":\"v1.7\"}}]}]";
        List<FollowupNode<PatientEduArticle>> followNodes = getObject2(json1, new TypeToken<List<FollowupNode<PatientEduArticle>>>() {});

//        for (FollowupNode item : followNodes){
//            item
//        }
        List<FollowupTask<PatientEduArticle>> content_list = followNodes.get(followNodes.size() - 1).content_list;
        Object content = content_list.get(content_list.size() - 1).content;
        List<PatientEduArticle> patientEduArticles = (List<PatientEduArticle>) content;
        String json = toJSONStr(content);
//
//        PatientEduArticle article = getObject(json, new TypeToken<PatientEduArticle>() {});


//        var article = getObject(json, object : TypeToken<PatientEduArticle>() {})
    }

    private static GsonBuilder gb = new GsonBuilder();

    public static <T> T getObject(String jsonString, TypeToken<T> pojoCalss) {
        try {
            Gson g = gb.create();
            return g.fromJson(jsonString, pojoCalss.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toJSONStr(Object obj) {
        Gson g = gb.create();
        return g.toJson(obj);
    }


    public static <T> T getObject2(String jsonString, TypeToken<T> typeToken) {
        try {

            Gson gson = new GsonBuilder().registerTypeAdapter(Double.class, new JsonSerializer<Double>() {

                @Override
                public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
                    if (src == src.longValue())
                        return new JsonPrimitive(src.longValue());
                    return new JsonPrimitive(src);
                }
//                @Override
//                public JsonElement deserialize(Double json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//
//                    if (src == src.longValue())
//                        return new JsonPrimitive(src.longValue());
//                    return new JsonPrimitive(src);
//                }
//                @Override
//                public HashMap<String, String> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//                    if (json.isJsonArray()) {
//                        JsonArray array = json.getAsJsonArray();
//                        Type itemType = ((ParameterizedType) typeOfT).getActualTypeArguments()[0];
//                        List list = new ArrayList<>();
//                        for (int i = 0; i < array.size(); i++) {
//                            JsonElement element = array.get(i);
//                            Object item = context.deserialize(element, itemType);
//                            list.add(item);
//                        }
//                        return list;
//                    } else {
//                        //和接口类型不符，返回空List
//                        return Collections.EMPTY_LIST;
//                    }
//                }
            }).create();
            return gson.fromJson(jsonString, typeToken.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
//    public static <T> T getObject2(String jsonString, TypeToken<T> typeToken) {
//        try {
//
//            Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(List.class, new JsonDeserializer<List<?>>() {
//                @Override
//                public List<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//                    if (json.isJsonArray()) {
//                        JsonArray array = json.getAsJsonArray();
//                        Type itemType = ((ParameterizedType) typeOfT).getActualTypeArguments()[0];
//                        List list = new ArrayList<>();
//                        for (int i = 0; i < array.size(); i++) {
//                            JsonElement element = array.get(i);
//                            Object item = context.deserialize(element, itemType);
//                            list.add(item);
//                        }
//                        return list;
//                    } else {
//                        //和接口类型不符，返回空List
//                        return Collections.EMPTY_LIST;
//                    }
//                }
//            }).create();
//            return gson.fromJson(jsonString, typeToken.getType());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


//		[{"node_id":"bdf3d880-0ba5-61dc-ff9e-f32e8ccd3fd0","interval_type":1,"interval":1,"calc_time":"1","content_list":[{"content_id":"cd40544d-d20f-3406-c9b1-71b17852a435","content_type":2,"content":{"questionnaire_id":1,"questionnaire_name":"疼痛评估VAS量表","questionnaire_type":1,"questionnaire_config_id":1,"questionnaire_url":"","belong_type":0}},{"content_id":"24f0f8ee-eb43-e35a-47fb-cf44f032e528","content_type":1,"content":{"article_id":11,"article_source":"唯医","article_type":0,"article_title":"髖关节置换康复锻炼方案(术后1-3天)","article_url":"https://m31.allinmd.cn/followUp?manualId=1581504433062","article_content":"","article_uuid":"","wechat_appid":""}}]},{"node_id":"64ee7ac5-54b8-d607-3c96-b5789dd20d82","interval_type":1,"interval":4,"calc_time":"4","content_list":[{"content_id":"e637b0aa-8f96-7c7f-23b9-d5beaa8d548b","content_type":2,"content":{"questionnaire_id":1,"questionnaire_name":"疼痛评估VAS量表","questionnaire_type":1,"questionnaire_config_id":1,"questionnaire_url":"","belong_type":0}},{"content_id":"5e06cf98-d624-c58c-30d9-b7f26d548b09","content_type":1,"content":{"article_id":12,"article_source":"唯医","article_type":0,"article_title":"髖关节置换康复锻炼方案(术后4-7天)","article_url":"https://m31.allinmd.cn/followUp?manualId=1581505166354","article_content":"","article_uuid":"","wechat_appid":""}}]},{"node_id":"eaa76f75-f9a0-f46a-3198-0f29b1959bfa","interval_type":1,"interval":7,"calc_time":"7","content_list":[{"content_id":"8015e9ea-1a6e-530f-042d-8a27e3dcfc8d","content_type":2,"content":{"questionnaire_id":1,"questionnaire_name":"疼痛评估VAS量表","questionnaire_type":1,"questionnaire_config_id":1,"questionnaire_url":"","belong_type":0}},{"content_id":"c44464f0-5074-75bb-947a-29bc7c64777a","content_type":1,"content":{"article_id":13,"article_source":"唯医","article_type":0,"article_title":"髖关节置换康复锻炼方案(术后7－14天)","article_url":"https://m31.allinmd.cn/followUp?manualId=1581508061673","article_content":"","article_uuid":"","wechat_appid":""}}]},{"node_id":"8bfca172-8090-3e67-fe67-f038fd8a0baf","interval_type":2,"interval":2,"calc_time":"14","content_list":[{"content_id":"4198db79-c30d-be17-95ff-dac41ea62dd0","content_type":3,"content":{"data":"为了保证您能够顺利康复，请于近期回院复查!"}},{"content_id":"c727b45c-c9d5-1ec0-bfd4-c52fb652d758","content_type":1,"content":{"article_id":14,"article_source":"唯医","article_type":0,"article_title":"髖关节置换康复锻炼方案(术后2-4周)","article_url":"https://m31.allinmd.cn/followUp?manualId=1581508525575","article_content":"","article_uuid":"","wechat_appid":""}}]},{"node_id":"df6cb3ce-aab7-213a-2d9c-aaacdb3d886a","interval_type":2,"interval":4,"calc_time":"28","content_list":[{"content_id":"9b53e081-92e9-83eb-6bb0-35c7d74f528b","content_type":3,"content":{"data":"为了保证您能够顺利康复，请于近期回院复查!"}},{"content_id":"efcf6781-9c91-9043-7035-9ad3221fa54c","content_type":1,"content":{"article_id":15,"article_source":"唯医","article_type":0,"article_title":"髖关节置换康复锻炼方案(术后4周)","article_url":"https://m31.allinmd.cn/followUp?manualId=1581508670360","article_content":"","article_uuid":"","wechat_appid":""}}]}]
//            [{"node_id":"bdf3d880-0ba5-61dc-ff9e-f32e8ccd3fd0","interval_type":1,"interval":1,"calc_time":"1","content_list":[{"content_id":"cd40544d-d20f-3406-c9b1-71b17852a435","content_type":2,"content":{"questionnaire_id":1,"questionnaire_name":"疼痛评估VAS量表","questionnaire_type":1,"questionnaire_config_id":1,"questionnaire_url":"","belong_type":0}},{"content_id":"24f0f8ee-eb43-e35a-47fb-cf44f032e528","content_type":1,"content":{"article_id":11,"article_source":"唯医","article_type":0,"article_title":"髖关节置换康复锻炼方案(术后1-3天)","article_url":"https://m31.allinmd.cn/followUp?manualId=1581504433062","article_content":"","article_uuid":"","wechat_appid":""}}]},{"node_id":"64ee7ac5-54b8-d607-3c96-b5789dd20d82","interval_type":1,"interval":4,"calc_time":"4","content_list":[{"content_id":"e637b0aa-8f96-7c7f-23b9-d5beaa8d548b","content_type":2,"content":{"questionnaire_id":1,"questionnaire_name":"疼痛评估VAS量表","questionnaire_type":1,"questionnaire_config_id":1,"questionnaire_url":"","belong_type":0}},{"content_id":"5e06cf98-d624-c58c-30d9-b7f26d548b09","content_type":1,"content":{"article_id":12,"article_source":"唯医","article_type":0,"article_title":"髖关节置换康复锻炼方案(术后4-7天)","article_url":"https://m31.allinmd.cn/followUp?manualId=1581505166354","article_content":"","article_uuid":"","wechat_appid":""}}]},{"node_id":"eaa76f75-f9a0-f46a-3198-0f29b1959bfa","interval_type":1,"interval":7,"calc_time":"7","content_list":[{"content_id":"8015e9ea-1a6e-530f-042d-8a27e3dcfc8d","content_type":2,"content":{"questionnaire_id":1,"questionnaire_name":"疼痛评估VAS量表","questionnaire_type":1,"questionnaire_config_id":1,"questionnaire_url":"","belong_type":0}},{"content_id":"c44464f0-5074-75bb-947a-29bc7c64777a","content_type":1,"content":{"article_id":13,"article_source":"唯医","article_type":0,"article_title":"髖关节置换康复锻炼方案(术后7－14天)","article_url":"https://m31.allinmd.cn/followUp?manualId=1581508061673","article_content":"","article_uuid":"","wechat_appid":""}}]},{"node_id":"8bfca172-8090-3e67-fe67-f038fd8a0baf","interval_type":2,"interval":2,"calc_time":"14","content_list":[{"content_id":"4198db79-c30d-be17-95ff-dac41ea62dd0","content_type":3,"content":{"data":"为了保证您能够顺利康复，请于近期回院复查!"}},{"content_id":"c727b45c-c9d5-1ec0-bfd4-c52fb652d758","content_type":1,"content":{"article_id":14,"article_source":"唯医","article_type":0,"article_title":"髖关节置换康复锻炼方案(术后2-4周)","article_url":"https://m31.allinmd.cn/followUp?manualId=1581508525575","article_content":"","article_uuid":"","wechat_appid":""}}]},{"node_id":"df6cb3ce-aab7-213a-2d9c-aaacdb3d886a","interval_type":2,"interval":4,"calc_time":"28","content_list":[{"content_id":"9b53e081-92e9-83eb-6bb0-35c7d74f528b","content_type":3,"content":{"data":"为了保证您能够顺利康复，请于近期回院复查!"}},{"content_id":"efcf6781-9c91-9043-7035-9ad3221fa54c","content_type":1,"content":{"article_id":15,"article_source":"唯医","article_type":0,"article_title":"髖关节置换康复锻炼方案(术后4周)","article_url":"https://m31.allinmd.cn/followUp?manualId=1581508670360","article_content":"","article_uuid":"","wechat_appid":""}},{"content_id":"c4dd45c3-3db3-44e6-8da5-46e3870149a7","content_type":1,"content":{"article_id":1597468172553,"article_source":"","article_type":0,"article_title":"这是一个测试内容一2222","article_url":"https://test-patient.allinmed.cn/patientEducation/patientDetail?id=1597468172553","article_uuid":"v1.7"}}]}]





}
