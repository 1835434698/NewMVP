package com.tangzy.tzymvp.net.api;

import com.tangzy.tzymvp.net.bean.ListBean;
import com.tangzy.tzymvp.net.bean.ResultBean;
import com.tangzy.tzymvp.net.bean.TestBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * class
 *
 * @author Administrator
 * @date 2019/12/26
 */
public interface APIService {

    /**
     * 数据是 {"retCode":0,"data":"11224","msg":"123123"}
     *
     * @param paramsMap
     * @return
     */

    @FormUrlEncoded
    @POST("test/login.php")
    Observable<ResultBean> login(@FieldMap Map<String, String> paramsMap);
//    Observable<ResponseBody> login(@FieldMap Map<String, String> paramsMap);


    /**
     * 数据是 {"retCode":0,"data":[{"examid":"id0","score":"score0"},{"examid":"id1","score":"score1"},{"examid":"id2","score":"score2"},{"examid":"id3","score":"score3"},{"examid":"id4","score":"score4"}],"msg":"123123"}
     *
     * @param paramsMap
     * @return
     */
    @FormUrlEncoded
    @POST("test/getList.php")
    Observable<ListBean> getList(@FieldMap Map<String, String> paramsMap);

    /**
     * 数据是   [{"examid":"id0","score":"score0"},{"examid":"id1","score":"score1"},{"examid":"id2","score":"score2"},{"examid":"id3","score":"score3"},{"examid":"id4","score":"score4"}]
     *
     * @param paramsMap
     * @return
     */
    @FormUrlEncoded
    @POST("test/getList2.php")
    Observable<List<TestBean>> getList2(@FieldMap Map<String, String> paramsMap);

}
