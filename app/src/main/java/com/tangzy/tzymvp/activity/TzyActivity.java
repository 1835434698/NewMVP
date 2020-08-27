package com.tangzy.tzymvp.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;

import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.tangzy.themvp.databind.BaseDataBinder;
import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.activity.base.BaseActivity;
import com.tangzy.tzymvp.bean.DataBean;
import com.tangzy.tzymvp.bean.LoginBean;
import com.tangzy.tzymvp.bean.TzyBean;
import com.tangzy.tzymvp.databind.TzyDataBinder;
import com.tangzy.tzymvp.presenter.NetPresenter;
import com.tangzy.tzymvp.util.Logger;
import com.tangzy.tzymvp.util.PackageAndDeviceUtils;
import com.tangzy.tzymvp.viewbind.TzyDelegate;

import java.util.Arrays;
import java.util.List;

public class TzyActivity extends BaseActivity<TzyDelegate>{

    private NetPresenter netPresenter;
    private String TAG = "TzyActivity";

    @Override
    public BaseDataBinder getDataBinder() {
        if (binder == null){
            binder = new TzyDataBinder();
        }
        return binder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        colorTitle = R.color.colorPrimaryDark;
        dark = false;
        super.onCreate(savedInstanceState);
        setTitle(viewDelegate.tzyBean.getName());
        netPresenter = new NetPresenter(this);

        startHHHH();
        int pid = android.os.Process.myPid();
        Log.d("tangzypid", "TzyActivity -> pid = "+pid);
        Log.d("tangzypid", "TzyActivity -> Thread = "+Thread.currentThread().getName());

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
//        final List<String> list = new ArrayList<>();
//        list.add("1111111");
//        list.add("1111112");
//        list.add("1111113");
        //模拟数据改变(比如也可以写在网络请求成功的时候改变数据)
        viewDelegate.get(R.id.button1).setOnClickListener(v-> startHHHH());
//        List features = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
//        features.forEach(k -> System.out.println(k));



//        viewDelegate.get(R.id.button1).setOnClickListener(new NoDoubleClickListener() {
//            @Override
//            public void onNoDoubleClick(View v) {
//
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        for (int ik =0; ik<50000;ik++){
//                            Logger.d(TAG,"接收数据,当前线程" + Thread.currentThread().getName()+" ,ik = "+ik);
//                        }
//                    }
//                }).start();
//
//
//
////                Observable
////                        .create(new ObservableOnSubscribe<String>() {
////
////                    @Override
////                    public void subscribe(ObservableEmitter<String> emitter) {
////                        Logger.d(TAG,"接收数据,当前线程" + Thread.currentThread().getName()+"subscribe");
////                        for (long i =0; i<1050000;i++){
////                            emitter.onNext("i = "+i);
////                        }
////
////                    }
////                })
//////                        .just("hahahah")
//////                        .map(new Function<String, Integer>() {
//////                            @Override
//////                            public Integer apply(String s) {
//////                                return s.length();
//////                            }
//////                        })
//////                        .just(list)
//////                        .flatMap(new Function<List<String>, ObservableSource<?>>() {
//////                            @Override
//////                            public ObservableSource<?> apply(List<String> strings) throws Exception {
//////                                return Observable.fromIterable(strings);
//////                            }
//////                        })
//////                        .filter(new Predicate<Object>() {
//////                            @Override
//////                            public boolean test(Object o) {
//////                                String newStr = (String) o;
//////                                if (newStr.equals("1111112")) {
////////                                if (newStr.charAt(5) - '0' > 5) {
//////                                    return true;
//////                                }
//////                                return false;
//////                            }
//////                        })
//////                        .subscribeOn(Schedulers.io())
//////                        .observeOn(AndroidSchedulers.mainThread())
//////                        .as(AutoDispose.<Object>autoDisposable(AndroidLifecycleScopeProvider.from(TzyActivity.this)))
//////                        .subscribe(new Consumer<Object>() {
//////                            @Override
//////                            public void accept(Object s) throws Exception {
//////                        Logger.d(TAG,"接收数据,当前线程" + Thread.currentThread().getName()+s);
//////
//////                            }
//////                        })
//////                        .subscribe(new Observer<Object>() {
//////                    @Override
//////                    public void onSubscribe(Disposable d) {
//////                        Logger.d(TAG,"onSubscribe");
//////                    }
//////
//////                    @Override
//////                    public void onNext(Object aLong) {
//////                        Logger.d(TAG,"接收数据,当前线程" + Thread.currentThread().getName()+aLong);
//////                    }
//////
//////                    @Override
//////                    public void onError(Throwable e) {
//////                        Logger.d(TAG,"onError");
//////                    }
//////
//////                    @Override
//////                    public void onComplete() {
//////                        Logger.d(TAG,"onComplete");
//////                    }
//////                })
////
//////                Observable.interval(1, TimeUnit.SECONDS)
////                        .subscribeOn(Schedulers.io())
////                        .observeOn(Schedulers.io())
////                        .as(AutoDispose.<String>autoDisposable(AndroidLifecycleScopeProvider.from(TzyActivity.this)))
////                        .subscribe(new Observer<String>() {
////                            @Override
////                            public void onSubscribe(Disposable d) {
////                                Logger.d(TAG,"onSubscribe");
////                            }
////
////                            @Override
////                            public void onNext(String aLong) {
////                                Logger.d(TAG,"接收数据,当前线程" + Thread.currentThread().getName()+String.valueOf(aLong));
////                            }
////
////                            @Override
////                            public void onError(Throwable e) {
////                                Logger.d(TAG,"onError");
////                            }
////
////                            @Override
////                            public void onComplete() {
////                                Logger.d(TAG,"onComplete");
////                            }
////                        })
////                ;
//
////                checkPermission(new CheckPermListener() {
////                    @Override
////                    public void superPermission() {
////                        Logger.d("tangzy11", viewDelegate.userBean.getAge()+"");
////                        Logger.d("tangzy11", viewDelegate.userBean.getName());
////
////
////
////                        LoginBean loginBean = new LoginBean();
//////                        loginBean.setUsername("user01");
//////                        loginBean.setPassword("123456");
////                        netPresenter.request(viewDelegate.userBean, true);
////                    }
////                }, R.string.ask_again, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
////
////                viewDelegate.tzyBean.setName("哈哈哈哈");
//////                //通知数据发生了改变
////                notifyModelChanged(viewDelegate.tzyBean);
//            }
//        });
    }

    private void startHHHH() {
        Logger.d("tangzy11", "Age="+viewDelegate.userBean.getAge()+"");
        Logger.d("tangzy11", "Name="+viewDelegate.userBean.getName());
        Logger.d("tangzy11", "Age1="+viewDelegate.userBean.getAge1()+"");
        Logger.d("tangzy11", "Name1="+viewDelegate.userBean.isName1()+"");
        Logger.d("tangzy11", "Name2="+viewDelegate.userBean.getName2()+"");
        Logger.d("tangzy11", "getAge_5="+viewDelegate.userBean.getAge_5()+"");
        Logger.d("tangzy11", "getAge_="+viewDelegate.userBean.getAge_10()+"");
        List<DataBean> list = viewDelegate.userBean.getList();
        if (list == null) {
            return;
        }
        for (DataBean s: list){
            Logger.d("tangzy11", "s = "+s.getName());
        }
                checkPermission(() -> {

                    LoginBean loginBean = new LoginBean();
//                        loginBean.setUsername("user01");
//                        loginBean.setPassword("123456");
                    netPresenter.request(viewDelegate.userBean, true);
                }, R.string.ask_again, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//
//                viewDelegate.tzyBean.setName("哈哈哈哈");
////                //通知数据发生了改变
//                notifyModelChanged(viewDelegate.tzyBean);

//        new Thread(() -> {
//            for (int ik =0; ik<500000;ik++){
//                Logger.d(TAG,"接收数据,当前线程" + Thread.currentThread().getName()+" ,ik = "+ik);
//            }
//        }).start();

//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        for (int ik =0; ik<500000;ik++){
//                            Logger.d(TAG,"接收数据,当前线程" + Thread.currentThread().getName()+" ,ik = "+ik);
//                        }
//                    }
//                }).start();
    }

    @Override
    protected Class getDelegateClass() {
        return TzyDelegate.class;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        netPresenter = null;
    }


    public void getImei(View view) {
        checkPermission(() -> {
            String deviceUniqueId = PackageAndDeviceUtils.getDeviceUniqueId(TzyActivity.this);
            Logger.d("tangzy1", "deviceUniqueId = "+deviceUniqueId);

        }, R.string.ask_again, Manifest.permission.READ_PHONE_STATE);
        String android_id = Settings.System.getString(
                getContentResolver(), Settings.Secure.ANDROID_ID);
        Logger.d("tangzy1", "android_id = "+android_id);

    }

    @Override
    public void resultFail(String uri, String message) {
        TzyBean tzyBean = new TzyBean("关某在此");
        Intent intent = new Intent(this, FragmentActivity.class);
        intent.putExtra(TzyBean.class.getCanonicalName(), tzyBean);
        startActivity(intent);
    }

    @Override
    public void resultSuc(String uri, String result) {
        TzyBean tzyBean = new TzyBean("关某在此");
        Intent intent = new Intent(this, FragmentActivity.class);
        intent.putExtra(TzyBean.class.getCanonicalName(), tzyBean);
        startActivity(intent);

    }
}
