package com.tangzy.tzymvp.util;

import android.os.Looper;
import android.util.ArrayMap;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public enum RxHandler {
    INSTANCE;

    private volatile ArrayMap<Integer, Disposable> disposables = new ArrayMap<>();

    /**
     * 当前线程中执行
     * @param runnable
     */
    public void postDelayed(LifecycleOwner owner, Runnable runnable, long delayMillis){
        if (Looper.myLooper() == Looper.getMainLooper()){
            postDelayedUI(owner, runnable, delayMillis);
        }else {
            postDelayedWorker(runnable, delayMillis);
        }
    }

    /**
     * 当前线程中执行
     * @param runnable
     */
    public void postDelayed(Runnable runnable, long delayMillis){
        if (Looper.myLooper() == Looper.getMainLooper()){
            postDelayedUI(runnable, delayMillis);
        }else {
            postDelayedWorker(runnable, delayMillis);
        }
    }

    /**
     * UI线程中执行
     * @param runnable
     */
    public void postDelayedUI(Runnable runnable, long delayMillis){
        Disposable subscribe = Observable.timer(delayMillis, TimeUnit.MICROSECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        runnable.run();
                        remove(runnable.hashCode());
                    }
                });
        int hashCode = runnable.hashCode();
        Log.d("hhhhhhhh", "hashCode = "+hashCode);
        disposables.put(hashCode, subscribe);

    }

    /**
     * UI线程中执行
     * @param runnable
     */
    private void postDelayedUI(LifecycleOwner owner, Runnable runnable, long delayMillis){
        Observable.timer(delayMillis, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        runnable.run();
                    }
                });
    }
    /**
     * Worker线程中执行
     * @param runnable
     */
    public void postDelayedWorker(Runnable runnable, long delayMillis){
        Disposable subscribe = Observable.timer(delayMillis, TimeUnit.MICROSECONDS)
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        runnable.run();
                        remove(runnable.hashCode());
                        Log.d("hhhhhhhh", "Thread runa2 over ");
                    }
                });
        int hashCode = runnable.hashCode();
        Log.d("hhhhhhhh", "hashCode = "+hashCode);
        disposables.put(hashCode, subscribe);
    }

    /**
     * dispose
     * @param disposable
     */
    private void dispose(Disposable disposable){
        Log.d("hhhhhhhh", "Thread runa2 isDisposed ");
        if (!disposable.isDisposed()){
            Log.d("hhhhhhhh", "Thread runa2 dispose ");
            disposable.dispose();
        }
    }

    /**
     * 取消runnable
     * @param runnable
     */
    public void removeCallbacks(Runnable runnable){
        Log.d("hhhhhhhh", "Thread runa2 removeCallbacks ");
        if (disposables.isEmpty()){
            return;
        }
        int hashCode = runnable.hashCode();
        if (disposables.containsKey(hashCode)){
            dispose(disposables.get(hashCode));
            remove(hashCode);
        }
//        if (Looper.myLooper() == Looper.getMainLooper()){
//        }else {
//            removeCallbacksWorker(runnable);
//        }
    }

    /**
     * dispose
     * @param hashcode
     */
    private void remove(int hashcode){
        Log.d("hhhhhhhh", "Thread runa2 remove ");
        if (!disposables.isEmpty() && disposables.containsKey(hashcode)){
            Log.d("hhhhhhhh", "Thread runa2 remove_C ");
            disposables.remove(hashcode);
        }
    }

    /**
     * Worker线程中执行
     * @param runnable
     */
    public void postDelayedWorker(LifecycleOwner owner, Runnable runnable, long delayMillis){
        Observable.timer(delayMillis, TimeUnit.MICROSECONDS)
                .observeOn(Schedulers.io())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        runnable.run();
                    }
                });
    }

    /**
     * 当前线程中执行
     * @param runnable
     */
    public void post( Runnable runnable){
        if (Looper.myLooper() == Looper.getMainLooper()){
            postUI(runnable);
        }else {
            postWorker(runnable);
        }
    }


    /**
     * UI线程中执行
     * @param runnable
     */
    public void postUI( Runnable runnable){
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        runnable.run();
                    }
                });
    }

    /**
     * 工作线程线程中执行
     * @param runnable
     */
    public void postWorker( Runnable runnable){
        Observable.just(1)
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        runnable.run();
                    }
                });
    }
    /**
     * 工作线程线程中执行
     * @param runnable
     */
    public void postWorker(LifecycleOwner owner, Runnable runnable){
        Observable.just(1)
                .observeOn(Schedulers.io())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        runnable.run();
                    }
                });
    }

}
