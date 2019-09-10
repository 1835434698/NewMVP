package com.tangzy.tzymvp.test;

import android.util.Log;

import com.tangzy.tzymvp.util.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * ${CLASS} class
 *
 * @author Administrator
 * @date 2019/7/22
 */
public class Test {
    private static final String TAG = "Test";
    private static final String HHH = "HHH";

    static {
        Logger.d(TAG, "static-1");
        Logger.d(TAG, "HHH = " + HHH);
        HHHH = "hhhhhh";
    }

    public Test() {
        Logger.d(TAG, "Test");
    }

    private static String HHHH = "HHHH";

    static {
        Logger.d(TAG, "static-1");
        Logger.d(TAG, "HHHH = " + HHHH);
        HHHH = "hhhhhh1";
        Logger.d(TAG, "HHHH = " + HHHH);

    }


    public void test() {
        String json = "{name:\"jason\",father:\"jason\",age:18}";
//name:"jason"
//age:18
//\"\\w+\" 字符串属性
        Pattern p = Pattern.compile("\\w+:(\"\\w+\"|\\d*)");
        Matcher m = p.matcher(json);
        while (m.find()) {
            String text = m.group();
            int dotPos = text.indexOf(":");
            String key = text.substring(0, dotPos);
            String value = text.substring(dotPos + 1, text.length());
            //替换字符串的开始结束的双引号
            value = value.replaceAll("^\\\"|\\\"$", "");
            Logger.d(TAG, "key = " + key);
            Logger.d(TAG, "value = " + value);
        }
        // RxJava的链式操作
        Observable.create(new ObservableOnSubscribe<Integer>() {
            // 1. 创建被观察者(Observable) & 定义需发送的事件
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        })

                .flatMap(new Function<Integer, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(Integer integer) throws Exception {
                        return null;
                    }
                })
                .subscribe(new Observer<Integer>() {
                    // 2. 创建观察者(Observer) & 定义响应事件的行为
                    // 3. 通过订阅（subscribe）连接观察者和被观察者
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }
                    // 默认最先调用复写的 onSubscribe（）

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "对Next事件" + value + "作出响应");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }

                });

    }


    /**
     * 输出 0 1 0 1 0 1 0 1...
     */
    private boolean isLock = false;
    public int kk = 0;

    public synchronized void add() {
        try {
            while (isLock) {
                wait();
            }
            Logger.d("tangzy", "test add = " + kk);
            kk++;
            isLock = true;
            notify();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void minus() {
        try {
            while (!isLock) {
                wait();
            }
            isLock = false;
            Logger.d("tangzy", "test minus = " + kk);
            kk--;
            notify();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
