package com.tangzy.tzymvp.kotlin;

import android.text.TextUtils;
import android.util.SparseArray;

import androidx.annotation.Nullable;

import com.tangzy.tzymvp.util.Utils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * BaseUrl配置仓库
 * Created by xianxueliang on 17-11-14.
 */
public class BaseUrlsRepository {

    /** 包含当前所有BaseUrl所处的环境 */
    private final ConcurrentHashMap<String, Integer> envConfig;
    /** 包含当前所有BaseUrl的环境+真实地址配置 */
    private final ConcurrentHashMap<String, SparseArray<String>> baseUrlsConfig;

    private static final class INSTANCE_HOLDER {
        private static final BaseUrlsRepository INSTANCE = new BaseUrlsRepository();
    }

    private BaseUrlsRepository() {
        // single instances.
        envConfig = new ConcurrentHashMap<>();
        baseUrlsConfig = new ConcurrentHashMap<>();
    }

    public static BaseUrlsRepository instance() {
        return INSTANCE_HOLDER.INSTANCE;
    }

    /**
     * 添加指定type下指定env的baseUrl
     * 如果type+env对应的baseUrl存在,则replace
     */
    public void putConfig(String baseUrlType, int env, String baseUrl) {
        Utils.checkNotNull(baseUrlType, "baseUrlType == null");
        Utils.nullOrNil(baseUrl, "baseUrl is null or empty");
        SparseArray<String> c = configOf(baseUrlType);
        if (c == null) {
            c = new SparseArray<>();
        }
        c.put(env, baseUrl);
        //replace old element if exist
        this.baseUrlsConfig.put(baseUrlType, c);
    }

    /**
     * 获取指定type的baseUrl环境配置
     */
    @Nullable
    private SparseArray<String> configOf(String baseUrlType) {
        if (!TextUtils.isEmpty(baseUrlType) && baseUrlsConfig.containsKey(baseUrlType)) {
            return baseUrlsConfig.get(baseUrlType);
        }
        return null;
    }

    /**
     * 获取配置中指定type下,指定env对应的baseUrl
     * @return null if not exists
     */
    @Nullable
    String getBaseUrl(String baseUrlType, int env) {
        SparseArray<String> config = configOf(baseUrlType);
        if (config != null) {
            return config.get(env);
        }
        return null;
    }

    /**
     * 获取配置中指定type下,指定env{@link BaseUrlsRepository#setEnv(String, int)}对应的baseUrl
     */
    @Nullable
    String getBaseUrl(String type) {
        return getBaseUrl(type, envOf(type));
    }

    /**
     * 获取指定type的baseUrl的运行环境
     */
    int envOf(String baseUrlType) {
        if (envConfig.containsKey(baseUrlType)) {
            return envConfig.get(baseUrlType);
        }
        throw new IllegalArgumentException(Utils.format("the %s corresponding env not found !", baseUrlType));
    }

    /**
     * 配置指定type的baseUrl的运行环境
     */
    public void setEnv(String baseUrlType, int env) {
        Utils.checkNotNull(baseUrlType, "baseUrlType == null");
        envConfig.put(baseUrlType, env);
    }
}
