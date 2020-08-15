package com.tangzy.tzymvp.util;

import android.os.Looper;

import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public enum  RxAndroidUtil {
    INSTANCE;

    public void postDelayed( Runnable runnable, long delayMillis){
        if (Looper.myLooper() == Looper.getMainLooper()){
            Observable.timer(delayMillis, TimeUnit.MICROSECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            runnable.run();
                        }
                    });
        }else {
            Observable.timer(delayMillis, TimeUnit.MICROSECONDS)
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            runnable.run();
                        }
                    });
        }
    }

    public void post( Runnable runnable){
        if (Looper.myLooper() == Looper.getMainLooper()){
            Observable.just(1)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Integer>() {
                @Override
                public void accept(Integer integer) throws Exception {
                    runnable.run();
                }
            });
        }else {
            Observable.just(1)
                    .subscribe(new Consumer<Integer>() {
                @Override
                public void accept(Integer integer) throws Exception {
                    runnable.run();
                }
            });
        }

    }

}
