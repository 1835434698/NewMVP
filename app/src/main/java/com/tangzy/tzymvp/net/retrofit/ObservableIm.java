package com.tangzy.tzymvp.net.retrofit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 统一默认设置运行线程
 *
 * @author Administrator
 * @date 2020/3/20
 */
public abstract class ObservableIm<T> extends Observable<T> {

    public Observable<T> builder(){
        return subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                ;
    }


}
