package com.tangzy.tzymvp.net;

import com.tangzy.tzymvp.util.Logger;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by Administrator on 2017/10/26.
 */

public class CookieJarImpl implements CookieJar {
    private static final String TAG = "CookieJarImpl";
    private CookieStore cookieStore;

    public CookieJarImpl(CookieStore cookieStore) {
        if (cookieStore == null) {
            new IllegalArgumentException("cookieStore can not be null.");
        }
        this.cookieStore = cookieStore;
    }

    @Override
    public synchronized void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        Logger.v(TAG, "saveFromResponse ----- url = "+url);
        if (cookies != null) {
            Logger.v(TAG, "saveFromResponse ----- cookies = "+cookies);
        }
        cookieStore.add(url, cookies);
    }

    @Override
    public synchronized List<Cookie> loadForRequest(HttpUrl url) {
        Logger.v(TAG, "loadForRequest ----- url = "+url);
        Logger.v(TAG, "loadForRequest ----- cookieStore.get(url) = "+cookieStore.get(url));
        return cookieStore.get(url);
    }

    public CookieStore getCookieStore() {
        return cookieStore;
    }
}
