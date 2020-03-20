package com.tangzy.tzymvp.net.retrofit;

import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * class
 *
 * @author Administrator
 * @date 2020/3/20
 */
public class ObserverIm<T> implements Observer<T> {
    private final String TAG = "MainActivity";

    @Override
    public void onSubscribe(Disposable d) {
        Log.d(TAG, "onSubscribe:");
    }

    @Override
    public void onNext(T t) {
        Log.d(TAG, "onNext:");
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "onError:" + e.getMessage());
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete:");
    }
}
