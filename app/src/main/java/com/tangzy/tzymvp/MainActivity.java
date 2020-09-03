package com.tangzy.tzymvp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.impl.WorkDatabase;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mingyuechunqiu.recordermanager.data.bean.RecordVideoRequestOption;
import com.mingyuechunqiu.recordermanager.data.bean.RecordVideoResultInfo;
import com.mingyuechunqiu.recordermanager.feature.record.RecorderManagerFactory;
import com.tangzy.jspdf.PdfActivity;
import com.tangzy.myannotation.CustomAnnotation;
import com.tangzy.myannotation.MyAnnotationApi;
import com.tangzy.navigation.NavigationLibActivity;
import com.tangzy.pdfrecyclerview.RecycleViewActivity;
import com.tangzy.pdfrecyclerview.adapter.LinearLayoutManagerRecycleview;
import com.tangzy.pdfrenderer.RecycleViewActivity1;
import com.tangzy.tzymvp.activity.AiduActivity;
import com.tangzy.tzymvp.activity.DataBindingActivity;
import com.tangzy.tzymvp.activity.DemoActivity;
import com.tangzy.tzymvp.activity.DoodleActivity;
import com.tangzy.tzymvp.activity.DownLoadActivity;
import com.tangzy.tzymvp.activity.GoogleGuvaActivity;
import com.tangzy.tzymvp.activity.IatDemo;
import com.tangzy.tzymvp.activity.ListenerActivity;
import com.tangzy.tzymvp.activity.LottieActivity;
import com.tangzy.tzymvp.activity.MyViewGroupActivity;
import com.tangzy.tzymvp.activity.NestedScrollViewActivity;
import com.tangzy.tzymvp.activity.RecyclerViewActivity;
import com.tangzy.tzymvp.activity.ServiceOActivity;
import com.tangzy.tzymvp.activity.ShowWaveActivity;
import com.tangzy.tzymvp.activity.SmartRefreshLayoutActivity;
import com.tangzy.tzymvp.activity.TestActivity;
import com.tangzy.tzymvp.activity.TwoActivity;
import com.tangzy.tzymvp.activity.TzyActivity;
import com.tangzy.tzymvp.activity.ViewPageActivity;
import com.tangzy.tzymvp.activity.WebActivity;
import com.tangzy.tzymvp.activity.YuanChengActivity;
import com.tangzy.tzymvp.adapter.MainRecycleAdapter;
import com.tangzy.tzymvp.annotation.ParseAnnotation;
import com.tangzy.tzymvp.annotation.TestClass;
import com.tangzy.tzymvp.bean.DataBean;
import com.tangzy.tzymvp.bean.Info;
import com.tangzy.tzymvp.bean.MainBean;
import com.tangzy.tzymvp.bean.TzyBean;
import com.tangzy.tzymvp.bean.UserBean;
import com.tangzy.tzymvp.entity.EventBusBaseEntity;
import com.tangzy.tzymvp.hook.HookSetOnClickListenerHelper;
import com.tangzy.tzymvp.net.api.APIService;
import com.tangzy.tzymvp.net.bean.ListBean;
import com.tangzy.tzymvp.net.bean.ResultBean;
import com.tangzy.tzymvp.net.bean.TestBean;
import com.tangzy.tzymvp.net.retrofit.ObserverIm;
import com.tangzy.tzymvp.net.retrofit.RetrofitManager;
import com.tangzy.tzymvp.permission.EasyPermissions;
import com.tangzy.tzymvp.servive.Demo3Service;
import com.tangzy.tzymvp.servive.DemoIntentService;
import com.tangzy.tzymvp.servive.DemoServive;
import com.tangzy.tzymvp.servive.SingASongService;
import com.tangzy.tzymvp.test.ChredUser;
import com.tangzy.tzymvp.test.DynamicProxy;
import com.tangzy.tzymvp.test.Iuser;
import com.tangzy.tzymvp.test.UserImpl;
import com.tangzy.tzymvp.util.Base64Encode;
import com.tangzy.tzymvp.util.FileType;
import com.tangzy.tzymvp.util.FileUtils;
import com.tangzy.tzymvp.util.Logger;
import com.tangzy.tzymvp.util.MD5;
import com.tangzy.tzymvp.util.OnyWayLinkedList;
import com.tangzy.tzymvp.util.RsaUtils;
import com.tangzy.tzymvp.util.RxHandler;
import com.tangzy.tzymvp.util.ThreadPoolUtil;
import com.tangzy.tzymvp.util.Utils;
import com.tangzy.tzymvp.view.CustomDialogFragment;
import com.tangzy.tzymvp.view.toast.SnackbarCus;
import com.tangzy.tzymvp.view.toast.SnackbarManagerCus;
import com.tangzy.video.VideoLibActivity;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.text.translate.UnicodeUnescaper;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

import static com.mingyuechunqiu.recordermanager.data.constants.Constants.EXTRA_RECORD_VIDEO_RESULT_INFO;

@CustomAnnotation
@Route(path = "/test/MainActivity")
public class MainActivity extends AppCompatActivity implements MainRecycleAdapter.OnItemClickListener{

    private final String TAG = "MainActivity";

    public View mSnackbarAnchor;
    private int m =0;

    @Autowired(name = "key1")
    long key1;
    @Autowired(name = "key3")
    String key3;
    //    long key4;
    @Autowired(name = "key4")
    DataBean key4;
//    long key4;

    com.tangzy.tzymvp.test.Test test = new com.tangzy.tzymvp.test.Test();

    String filePath = Environment.getExternalStorageDirectory().getPath() + "/aaaaa/123456.mp4";
    String SDCARD_PATH = Environment.getExternalStorageDirectory().getPath() + "/aaaaa/";
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    Handler handler1 = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Logger.d(TAG, "handler1");
            return false;
        }
    });

    private ImageView iv_gif;

    private RecyclerView recycleView;
    private MainRecycleAdapter adapter;
    private ArrayList<MainBean> lists = new ArrayList<>();


    private void setPath(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        try {
            if (file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            file.createNewFile();

        } catch (IOException e) {
            e.printStackTrace();
            Logger.d(TAG, "error = "+e.getMessage());
        }
    }

    @SuppressLint("AutoDispose")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPath(Constant.path+"ttt.txt");
        ARouter.getInstance().inject(this);//添加在onCreate（）
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        mSnackbarAnchor = findViewById(R.id.base_root);
        handler.sendEmptyMessage(0);
        LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>();

        try {
            Logger.d("tangzy", "key1 = " + key1 + ".key3 = " + key3 + ".key4 = " + key4.getName());
        }catch (Exception e){

        }
        init();
        initNet();
        initData();

        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.this.startActivity(new Intent());
            }
        }, 500);

        int i = 0;
        for(int z = 5; i < z; ++i) {
            Logger.d("tangzy", " i = " + i);
        }
        int j = 0;
        for(int z = 5; j < z; j++) {
            Logger.d("tangzy", " j = " + j);
        }


        Observable.just(1)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("hhhhhhhhhh", "onSubscribe");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d("hhhhhhhhhh2", "onNext name = "+Thread.currentThread().getName());
//                        try {
//                            po = converter.convert(params);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("hhhhhhhhhh", "onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("hhhhhhhhhh2", "onComplete name = "+Thread.currentThread().getName());
                    }
                });

        Observable.just(1)
                .subscribeOn(Schedulers.single())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("hhhhhhhhhh", "onSubscribe");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d("hhhhhhhhhh", "onNext name = "+Thread.currentThread().getName());
//                        try {
//                            po = converter.convert(params);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("hhhhhhhhhh", "onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("hhhhhhhhhh", "onComplete");
                    }
                });

        Observable.just(1)
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("hhhhhhhhhh", "onSubscribe");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d("hhhhhhhhhh", "onNext name = "+Thread.currentThread().getName());
//                        try {
//                            po = converter.convert(params);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("hhhhhhhhhh", "onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("hhhhhhhhhh", "onComplete");
                        for(int i =0; i<10000;i++){
//                            try {
//                                Thread.sleep(100);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
                            final int j = m++;
                            Observable.just(1)
                                    .subscribeOn(Schedulers.single())
                                    .subscribe(new Observer<Integer>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {
                                            Log.d("hhhhhhhhhh", "onSubscribe");
                                        }

                                        @Override
                                        public void onNext(Integer integer) {
                                            Log.d("hhhhhhhhhh1", "onNext name 1= "+Thread.currentThread().getName()+";_i = "+j);
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            Log.d("hhhhhhhhhh", "onError");
                                        }

                                        @Override
                                        public void onComplete() {
                                            Log.d("hhhhhhhhhh", "onComplete");
                                        }
                                    });

                        }
                    }
                });
        MyAnnotationApi.sayHelloAnnotation(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        ThreadPoolUtil.execute(new Runnable() {
            @Override
            public void run() {
                Log.d("tangzyyy", "ThreadName1 = "+Thread.currentThread().getName());
            }
        });
        ThreadPoolUtil.execute(new Runnable() {
            @Override
            public void run() {
                Log.d("tangzyyy", "ThreadName2 = "+Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        ThreadPoolUtil.execute(new Runnable() {
            @Override
            public void run() {
                Log.d("tangzyyy", "ThreadName3 = "+Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        ThreadPoolUtil.execute(new Runnable() {
            @Override
            public void run() {
                Log.d("tangzyyy", "ThreadName4 = "+Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        ThreadPoolUtil.execute(new Runnable() {
            @Override
            public void run() {
                Log.d("tangzyyy", "ThreadName5 = "+Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        ThreadPoolUtil.execute(new Runnable() {
            @Override
            public void run() {
                Log.d("tangzyyy", "ThreadName6 = "+Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        ThreadPoolUtil.execute(new Runnable() {
            @Override
            public void run() {
                Log.d("tangzyyy", "ThreadName7 = "+Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        hhhhhh();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void hhhhhh() {
        String fsa ="sda;fs";
    }

    private void initData() {
        //        startActivity(new Intent(this, Demo2Activity.class));
//
//        RealSubject realSubject = new RealSubject();
//
//
//        Subject subject = (Subject) Proxy.newProxyInstance(
//                realSubject.getClass().getClassLoader(),
//                realSubject.getClass().getInterfaces(),
//                new InvocationHandlerForTest(realSubject));
//        Logger.d(TAG, "operation = "+subject.operation());
//        Logger.d(TAG, "operation2");
//        subject.operation2();

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                test.add();
            }).start();

        }
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                test.minus();
            }).start();
        }
        mApp = getApplication();
        int id = 0;
        MainBean mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "示例1:最简单的实现";
        lists.add(mainBean);

        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "toWebView";
        lists.add(mainBean);

        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "示例3:在fragment中的实现";
        lists.add(mainBean);

        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "分离音频";
        lists.add(mainBean);

        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "示例5:Toolbar与Menu";
        lists.add(mainBean);

        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "service";
        lists.add(mainBean);

        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "涂鸦";
        lists.add(mainBean);

        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "视频录制";
        lists.add(mainBean);

        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "提取图片";
        lists.add(mainBean);

        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "SufaceView";
        lists.add(mainBean);

        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "注解测试";
        lists.add(mainBean);

        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "Loader";
        lists.add(mainBean);

        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "Notification";
        lists.add(mainBean);

        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "RSA";
        lists.add(mainBean);

        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "线程池";
        lists.add(mainBean);

        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "SnackBar";
        lists.add(mainBean);

        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "Toast";
        lists.add(mainBean);

        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "DialogFragment";
        lists.add(mainBean);

        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "NestedScrollView";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "leetCode";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "Proxy";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "生产消费者";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "重入锁";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "Hook";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "泛型";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "链表";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "RecyclerView上拉下拉1";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "smartRefresh";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "RxJava";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "newActivity";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "Retrofit";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "DataBinding";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "Run And Start";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "算法";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "跨进程intent";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "保活service";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "WorkManager";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "hook修改参数";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "sendCallBack";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "桌面小部件";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "kotlin";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "serviceHandler";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "自定义view内部handler";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "循环";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "停止";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "监听";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "jspdf";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "android原生pdf";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "pdfViewPage";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "自定义SnackBar";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "动态还在sd卡class";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "ViewPageActivity";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "MedialLib";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "Navigation";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "LottieAnimation";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "RxAndroidUtil";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "Service8";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "三方下载lib";
        lists.add(mainBean);
        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "google guava";
        lists.add(mainBean);

        mainBean = new MainBean();
        mainBean.id = id++;
        mainBean.name = "EventBus";
        lists.add(mainBean);


        adapter.notifyDataSetChanged();
    }

    private void initNet() {
        Glide.with(this).load(R.drawable.qqqqq)
//                .listener(new RequestListener<Drawable>() {
//            @Override
//            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                return false;
//            }
//
//            @Override
//            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                if (resource instanceof GifDrawable) {
//                    //加载一次
//                    ((GifDrawable)resource).setLoopCount(1);
//                }
//                return false;
//            }
//        })
                .into(iv_gif);
    }

    private void init() {
        iv_gif = findViewById(R.id.iv_gif);
        recycleView = findViewById(R.id.recycleView);

        adapter = new MainRecycleAdapter(this, lists);
        //1线性
        LinearLayoutManagerRecycleview layoutManager = new LinearLayoutManagerRecycleview(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);//

        recycleView.setLayoutManager(layoutManager);

        recycleView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(int position) {
        switch (position){
            case 0:
                button1();
                break;
            case 1:
                button2();
                break;
            case 2:
                startActivity(new Intent(this, TwoActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, AiduActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, IatDemo.class));
                break;
            case 5:
                button6();
                break;
            case 6:
                onDoodle();
                break;
            case 7:
                videoRecord();
                break;
            case 8:
                getPicture();
                break;
            case 9:
                toSufaceView();
                break;
            case 10:
                toAnnotation();
                break;
            case 11:
                loader();
                break;
            case 12:
                notification();
                break;
            case 13:
                RSA();
                break;
            case 14:
                threadPool();
                break;
            case 15:
                snackBar(recycleView);
                break;
            case 16:
                Toast();
                break;
            case 17:
                DialogFragment();
                break;
            case 18:
                nestedScrollView();
                break;
            case 19:
                leetCode();
                break;
            case 20:
                proxy();
                break;
            case 21:
                producerConsumer();
                break;
            case 22:
                reentrantLock();
                break;
            case 23:
                hook(recycleView);
                break;
            case 24:
                genericity();
                break;
            case 25:
                wayLinkedList();
                break;
            case 26:
                recyclerView();
                break;
            case 27:
                smartRefreshLayout();
                break;
            case 28:
                RxJava();
                break;
            case 29:
                newActivity();
                break;
            case 30:
                Retrofit();
                break;
            case 31:
                dataBinding();
                break;
            case 32:
                RunAndStart();
                break;
            case 33:
                suanfa();
                break;
            case 34:
                yuanchengIntent();
                break;
            case 35:
                startServiceC();
                break;
            case 36:
                workManager();
                break;
            case 37:
                hookAlert();
                break;
            case 38:
                sendCallBack();
                break;
            case 39:
                MyAppWidgetProvider();
                break;
            case 40:
                kotlinTest();
                break;
            case 41:
                serviceHandler();
                break;
            case 42:
                viewHandler();
                break;
            case 43:
                xunhuan();
                break;
            case 44:
                tingzhixunhuan();
                break;
            case 45:
                listenerTest();
                break;
            case 46:
                jspdf();
                break;
            case 47:
                pdfRendererBasic();
                break;
            case 48:
                pdfViewPage();
                break;
            case 49:
                customSnackBar();
                break;
            case 50:
                loaderSDClass();
                break;
            case 51:
                toViewPageActivity();
                break;
            case 52:
                toMedialLib();
                break;
            case 53:
                toNavigation();
                break;
            case 54:
                toLottieAnimation();
                break;
            case 55:
                onRxAndroidUtil();
                break;
            case 56:
                onService8();
                break;
            case 57:
                onDownLoad3();
                break;
            case 58:
                onGoogleGuava();
                break;
            case 59:
                onEventBus();
                break;
        }

    }
    @Subscribe
    public void updateBaseInfo(@NotNull EventBusBaseEntity<Object>  eventBusBaseEntity) {
        Logger.i(TAG, "接受EventBus  -downloadStateCount = " + eventBusBaseEntity);

    }

    private void onEventBus() {
        EventBusBaseEntity eventBusBaseEntity = new EventBusBaseEntity();
        eventBusBaseEntity.data = new Object();
        eventBusBaseEntity.type = 1;
        EventBus.getDefault().post(eventBusBaseEntity);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void videoRecord() {
        FileUtils.makesureFileExist(filePath);
        RecordVideoRequestOption option = new RecordVideoRequestOption();
        option.setMaxDuration(20);
        option.setFilePath(filePath);
        option.setmCameraType(1);
        RecorderManagerFactory.getRecordVideoRequest().startRecordVideo(this, 0, option);
    }

    public void getPicture() {
        Utils.muxerImg(filePath, SDCARD_PATH);
    }

    public void toSufaceView() {
        startActivity(new Intent(this, ShowWaveActivity.class));
    }

    /**
     * 注解
     * 注解就是给类起了一个别名。
     *
     * @param view
     */
    public void toAnnotation() {
        /**
         * 直接调用
         */
        TestClass.main(null);
        /**
         * 遍历获取
         * 通过获取注解方法获取
         */
        ParseAnnotation.main(null);

        /**
         * 注解枚举
         */
        FileType fileType = new FileType(FileType.TYPE_MUSIC);
//        FileType fileType = new FileType("affs");

        Logger.d(TAG, "fileType = "+fileType.fileType);

    }

    public void loader() {
        ARouter.getInstance().build("/demoLoader/mainActivity")
                .navigation();
    }

    public void notification() {
        test.notifyKJ(this);
    }

    public void RSA() {
        RsaUtils.main();

//        String publicKey = "fhasflajkdsfalksff";
////        String publicKey = "fhasflajkdsfalksfQ";
//        byte[] a = RsaUtils.getA(publicKey);
//        Logger.d("tangzy1", Rsa2Utils.getA(a));
//        Logger.d("tangzy1", a);
    }

    private static final String SUCCESS = "success";

    public void threadPool() {
//        ExecutorService service = Executors.newFixedThreadPool(4);//全部核心线程
//        Executors.newCachedThreadPool()//全部非核心线程
//        Executors.newScheduledThreadPool(4);//有数量固定的核心线程，且有数量无限多的非核心线程
//        Executors.newSingleThreadExecutor();//内部只有一个核心线程

//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
//        for (int i = 0; i < 10; i++) {
//            threadPoolExecutor.execute(new Runnable() {
//                @Override
//                public void run() {
//                    Log.d("tangzy", "execute");
//
//                }
//            });
//
//        }
//        Future<Object> submit = threadPoolExecutor.submit(new Callable<Object>() {
//            @Override
//            public Object call() throws Exception {
//                Log.d("tangzy", "call");
//                Thread.sleep(5000);
//
//                return SUCCESS;
//            }
//        });
//        try {
//            String s = (String) submit.get();
//            if (SUCCESS.equals(s)) {
//                String name = Thread.currentThread().getName();
//                Log.d("tangzy", "经过返回值比较，submit方法执行任务成功    thread name: " + name);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
        int i = 0;
        while (i< 2500){
            Log.e(TAG," i = "+i);

            Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                    emitter.onNext("连载1");
                    Thread.sleep(1000);
                    emitter.onNext("连载2");
                    emitter.onNext("连载3");
                    emitter.onNext("2");
//                emitter.onComplete();
                }
            })
                    .subscribeOn(Schedulers.from(ThreadPoolUtil.getExecutor()))
                    .observeOn(AndroidSchedulers.mainThread())
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            Log.e(TAG,"onSubscribe");
                        }

                        @Override
                        public void onNext(String value) {
                            if ("2".equals(value)){
                                onComplete();
                                return;
                            }
                            Log.e(TAG,"onNext:"+value);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG,"onError="+e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                            Log.e(TAG,"onComplete()");
                        }
                    });
            i++;
        }

    }

    public void snackBar(View view) {
        Snackbar.make(view, "1234556", Snackbar.LENGTH_SHORT).show();
    }

    public static Toast toast;
    public static Application mApp;

    public static void showToast(String content) {
        Logger.d("tangzy", "showToast");
        if (toast == null) {
            Logger.d("tangzy", "toast == null");
            toast = Toast.makeText(mApp, content, Toast.LENGTH_SHORT);
        } else {
            Logger.d("tangzy", "setText");
            toast.setText(content);
        }
        toast.show();
    }

    public void Toast() {
        showToast("1213234");
    }

    public void DialogFragment() {
        CustomDialogFragment.showDialog(this);
    }

    public void nestedScrollView() {
        startActivity(new Intent(this, NestedScrollViewActivity.class));
    }

    public void proxy() {
        Iuser user = new UserImpl();
        InvocationHandler h = new DynamicProxy(user);
        Iuser proxy = (Iuser) Proxy.newProxyInstance(Iuser.class.getClassLoader(), new Class[]{Iuser.class}, h);
        proxy.eat("苹果");
        proxy.eat2("juzi");
        proxy.eat2("123", "456");
    }


    private void button6() {
        Intent intent;
        intent = new Intent(this, DemoServive.class);
        intent.putExtra("kfc", 10);
//            intent.putExtra("kfc", kfc);
//                startService(intent);
        bindService(intent, con, Context.BIND_AUTO_CREATE);
        startActivity(new Intent(this, DemoActivity.class));

        ChredUser chredUser = new ChredUser();
    }

    private void button2() {
        Intent intent1 = new Intent(this, WebActivity.class);
        intent1.putExtra("path", "android_asset/index.html");
        startActivity(intent1);
    }

    private void button1() {

        int pid = android.os.Process.myPid();
        Log.d("tangzypid", "mainActivity -> pid = " + pid);
        Log.d("tangzypid", "mainActivity -> Thread = " + Thread.currentThread().getName());
        TzyBean tzyBean = new TzyBean("关某在此");
        UserBean userBean = new UserBean();
        userBean.setAge(15);
        userBean.setName("I am zhangsan");
//                userBean.setAge1(156);
        List<DataBean> list = new ArrayList<>();
        DataBean dataBean = new DataBean();
        dataBean.setName("wang ma zi");
        list.add(dataBean);
//                list.add(1258);
//                list.add(1259);
//                list.add(1260);
//                list.add(1263);
//                list.add(1268);
//                list.add(1269);
        userBean.setName1(true);
        userBean.setName2(false);
        Byte fff = new Byte("123");
        userBean.setAge_10(fff);


        userBean.setList(list);
        Intent intent = new Intent(this, TzyActivity.class);
        intent.putExtra(TzyBean.class.getCanonicalName(), tzyBean);
        intent.putExtra(UserBean.class.getCanonicalName(), userBean);
        startActivity(intent);
    }

    ServiceConnection con= new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Logger.d("tangzy", "onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Logger.d("tangzy", "onServiceDisconnected");
        }
    };

    public void leetCode() {
//        int nums[] = {2,7,11,15};
//        int target = 18;
//        int i1 = nums.length;
//        for (int i = 0; i < i1-1; i++){
//            for (int k = i+1;k < i1; k ++){
//                if (nums[i]+nums[k] == target){
//                    Log.d(TAG, "i = "+i+", k = "+k);
//                    return;
//                }
//            }
//        }
        int asdfghjkasvb = lengthOfLongestSubstring("asdfghjkasvb");

        Log.d(TAG, "asdfghjkasvb = " + asdfghjkasvb);
    }

    public int lengthOfLongestSubstring(String s) {
        int n = s.length();
        int ans = 0;
        for (int i = 0; i < n; i++)
            for (int j = i + 1; j <= n; j++)
                if (allUnique(s, i, j)) ans = Math.max(ans, j - i);
        return ans;
    }

    public boolean allUnique(String s, int start, int end) {
        Set<Character> set = new HashSet<>();
        for (int i = start; i < end; i++) {
            Character ch = s.charAt(i);
            if (set.contains(ch)) return false;
            set.add(ch);
        }
        return true;
    }


    private static Integer count = 0;
    private static final Integer FULL = 10;
    private static String LOCK = "lock";
    public void producerConsumer() {
        new Thread(new Producer()).start();
        new Thread(new Consumer_()).start();
        new Thread(new Producer()).start();
        new Thread(new Consumer_()).start();
        new Thread(new Producer()).start();
        new Thread(new Consumer_()).start();
        new Thread(new Producer()).start();
        new Thread(new Consumer_()).start();

    }

    public void hook(View view) {
        HookSetOnClickListenerHelper.hook(this, view);

    }

    public void genericity() {
        ArrayList arrayList = new ArrayList();

        arrayList.add("hahaha");
        arrayList.add(123);
        arrayList.add("fdsfalsf");
        arrayList.get(0);
        String respone = "[{\"name\":\"张三\",\"age\":\"19\"},{\"name\":\"张三\",\"age\":\"19\"},{\"name\":\"张三\",\"age\":\"19\"},{\"name\":\"张三\",\"age\":\"19\"}]";

        List<Info> infos = parseArray(respone, Info.class);
        Log.d(TAG, "size = "+infos.size());

    }

    public <T> List<T> parseArray(String response, Class<T> object){
        List<T> modelList = JSON.parseArray(response, object);
        return modelList;
    }

    public void wayLinkedList() {
        OnyWayLinkedList<String> onyWayLinkedList = new OnyWayLinkedList<>();
        for (int i=0;i<10;i++){
            onyWayLinkedList.add("i = "+i);
        }
        for (int i = 0; i<onyWayLinkedList.size(); i++) {
            Log.d(TAG, onyWayLinkedList.get(i));
        }
        onyWayLinkedList.reverse();
        for (int i = 0; i<onyWayLinkedList.size(); i++) {
            Log.d(TAG, onyWayLinkedList.get(i));
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }




    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d("hhh", "down");
                return true;
            case MotionEvent.ACTION_UP:
                Log.d("hhh", "up");
                return true;
            case MotionEvent.ACTION_MOVE:
                return true;
            case MotionEvent.ACTION_CANCEL:
                Log.d("hhh", "cancle");
                return true;
        }
        return super.onTouchEvent(event);
    }

    public void reentrantLock() {
        //线程一
        new Thread(new Runnable() {
            @Override
            public void run() {
                serviceC();
                serviceA();
            }
        }).start();

        //线程二
        new Thread(new Runnable() {
            @Override
            public void run() {
                serviceD();
                serviceB();

            }
        }).start();

    }

    public void recyclerView() {
        startActivity(new Intent(this, RecyclerViewActivity.class));
    }

    public void smartRefreshLayout() {
        startActivity(new Intent(this, SmartRefreshLayoutActivity.class));
    }

    public void toLottieAnimation() {
        startActivity(new Intent(this, LottieActivity.class));
    }

    public void onRxAndroidUtil() {
        Logger.d("hhhhhhhh", "Handler 1runa " +Thread.currentThread().getName());
        RxHandler.INSTANCE.post(new Runnable() {
            @Override
            public void run() {
                Logger.d("hhhhhhhh", "runa " +Thread.currentThread().getName());

            }
        });
        Logger.d("hhhhhhhh", "Handler 2runa " +Thread.currentThread().getName());
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Logger.d("hhhhhhhh", "Handler runa " +Thread.currentThread().getName());

            }
        });
        Logger.d("hhhhhhhh", "Handler 3runa " +Thread.currentThread().getName());
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                RxHandler.INSTANCE.post(new Runnable() {
                    @Override
                    public void run() {
                        Logger.d("hhhhhhhh", "Thread runa " +Thread.currentThread().getName());

                    }
                });
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        Logger.d("hhhhhhhh", "Thread Handler runa " +Thread.currentThread().getName());

                    }
                });
                Looper.loop();
            }
        }).start();
    }

    public void onService8() {
        startActivity(new Intent(this, ServiceOActivity.class));
    }

    public void onDownLoad3() {
        startActivity(new Intent(this, DownLoadActivity.class));
    }

    public void onGoogleGuava() {
        startActivity(new Intent(this, GoogleGuvaActivity.class));
    }

    public void onDoodle() {
        startActivity(new Intent(this, DoodleActivity.class));
    }


    private interface ProgressListener{
        void onProgress(int progress);
    }

    private void getCurrentProgress(ProgressListener listener){
        Observable.just(1)
                .subscribeOn(Schedulers.io())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(new AbstractObserver<Integer>() {
                    @Override
                    public void onNext(Integer integer) {
                        Logger.d("hhhhhhhhhhh", "onNext Thread name = "+Thread.currentThread().getName());
                        int position =  1110;//当前进度  单位是毫秒
                        Logger.d("hhhhhhhhhhh", "onNext position = "+position);
                        if (listener!=null){
                            Logger.d("hhhhhhhhhhh", "onNext Thread name = "+Thread.currentThread().getName());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                            Logger.d("hhhhhhhhhhh", "runOnUiThread in Thread name = "+Thread.currentThread().getName());
                                            listener.onProgress(position);
                                }
                            });
                        }
                    }
                });
    }


    String keyScr= "allin1123abc";
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    public void RxJava() {
        for (int i =0; i<10;i++){
            getCurrentProgress(new ProgressListener() {
                @Override
                public void onProgress(int progress) {
                    Logger.d("hhhhhhhhhhh", "onProgress ; onProgress = "+progress+" in Thread name = "+Thread.currentThread().getName());

                }
            });
        }

        String bodyString = "{\"responseStatus\":true,\"responsePk\":1505888492588,\"responseData\":{\"apiToken\":\"ZkKnIBpCjufVBaasoxaELXnogSJwVjYM\",\"isBindApple\":false,\"customerUnite\":{\"sortType\":0,\"id\":50084,\"customerId\":1505888492588,\"nickname\":\"还回家\",\"mobile\":\"12312312328\",\"uniteTimeMobile\":\"2017-09-20 14:26:05\",\"email\":\"lll@sina.com\",\"uniteTimeEmail\":\"2018-11-22 18:29:18\",\"uniteNameQq\":\"\",\"uniteFlagQq\":0,\"uniteTimeQq\":\"\",\"uniteIdSina\":0,\"uniteNameSina\":\"\",\"uniteFlagSina\":0,\"uniteTimeSina\":\"\",\"uniteNameBaidu\":\"\",\"uniteFlagBaidu\":0,\"uniteTimeBaidu\":\"\",\"uniteNameWeixin\":\"\",\"uniteFlagWeixin\":0,\"uniteTimeWeixin\":\"\",\"uniteIdCaos\":0,\"uniteNameCaos\":\"\",\"uniteFlagCaos\":0,\"uniteTimeCaos\":\"\",\"passwd\":\"e9ef17c93fb0d1464fb6c06d9189058e\",\"customerRole\":11,\"isCheckEmail\":0,\"isCheckMobile\":1,\"isValid\":1,\"uniteNameZhgk\":\"\",\"initPasswd\":\"\",\"sendSiteId\":19,\"createTime\":\"2017-09-20 14:27:50.0\",\"cancelStatus\":\"0\",\"areasExpertise\":\"\"},\"logoUrl\":\"http://img05.allinmd.cn/public1/2019/01/10/XrLVNFyjKdQOTJmQKhEQwndLpwWHmkLX_c_p_100_100.jpg\"},\"baseData\":\"eyJyZXNwb25zZURhdGEiOiJ7YXBpVG9rZW49WmtLbklCcENqdWZWQmFhc294YUVMWG5vZ1NKd1ZqWU0sIGlzQmluZEFwcGxlPWZhbHNlLCBjdXN0b21lclVuaXRlPWNuLmFsbGlubWQubWVkaWNhbC5jdXN0b21lci5jbGllbnQubW9kZWwuZHRvLkN1c3RvbWVyVW5pdGVANWU2NDYyMTMsIGxvZ29Vcmw9aHR0cDovL2ltZzA1LmFsbGlubWQuY24vcHVibGljMS8yMDE5LzAxLzEwL1hyTFZORnlqS2RRT1RKbVFLaEVRd25kTHB3V0hta0xYX2NfcF8xMDBfMTAwLmpwZ30iLCJyZXNwb25zZVN0YXR1cyI6dHJ1ZSwicmVzcG9uc2VQayI6MTUwNTg4ODQ5MjU4OH0=\",\"apiSign\":\"121fd08728ed80b98b895a6c5d10c9fd\"}";
        String base64 = "eyJyZXNwb25zZURhdGEiOiJ7YXBpVG9rZW49WmtLbklCcENqdWZWQmFhc294YUVMWG5vZ1NKd1ZqWU0sIGlzQmluZEFwcGxlPWZhbHNlLCBjdXN0b21lclVuaXRlPWNuLmFsbGlubWQubWVkaWNhbC5jdXN0b21lci5jbGllbnQubW9kZWwuZHRvLkN1c3RvbWVyVW5pdGVANTgyOTZkNjAsIGxvZ29Vcmw9aHR0cDovL2ltZzA1LmFsbGlubWQuY24vcHVibGljMS8yMDE5LzAxLzEwL1hyTFZORnlqS2RRT1RKbVFLaEVRd25kTHB3V0hta0xYX2NfcF8xMDBfMTAwLmpwZ30iLCJyZXNwb25zZVN0YXR1cyI6dHJ1ZSwicmVzcG9uc2VQayI6MTUwNTg4ODQ5MjU4OH0=";

        String md5 = MD5.stringToMD5(base64);
        Logger.d("jjjjjjjjj", "md51 = "+ md5);




        try {
            org.json.JSONObject jsonObject = new JSONObject(bodyString);
            if (jsonObject != null){

                boolean responseStatus =jsonObject.optBoolean("responseStatus");
                String responseCode = jsonObject.optString("responseCode");
                String responseMessage = jsonObject.optString("responseMessage");
                String responseData = jsonObject.optString("responseData");
                String responsePk = jsonObject.optString("responsePk", "0");
                String baseData = jsonObject.optString("baseData");
                String apiSign = jsonObject.optString("apiSign");

                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("responseData", responseData);
                jsonObject1.put("responseStatus", responseStatus);
                jsonObject1.put("responsePk", responsePk);


                Logger.d("jjjjjjjjj", " jsonObject1.toString() = "+jsonObject1.toString());
                String encrypt = Base64Encode.encrypt(jsonObject1.toString());
                Logger.d("jjjjjjjjj", " encrypt = "+encrypt);
                String decrypt = Base64Encode.decrypt(encrypt);
                Logger.d("jjjjjjjjj", " decrypt = "+decrypt);
                String baseData1 = Base64Encode.decrypt(baseData);
                Logger.d("jjjjjjjjj", " baseData = "+baseData);
                Logger.d("jjjjjjjjj", " baseData1 = "+baseData1);


                Logger.d("jjjjjjjjj", " apiSign = "+apiSign);
                String s = MD5.stringToMD5(baseData);

                Logger.d("jjjjjjjjj", " md5s = "+s);
                Logger.d("jjjjjjjjj", " apiSign = "+apiSign);

                String escaped = StringEscapeUtils.escapeJava(responseData);
                String greekChars = new UnicodeUnescaper().translate(escaped);
                Logger.d("jjjjjjjjj", " greekChars = "+greekChars);

                Map map = new ArrayMap();

                StringBuffer dataBuffer = new StringBuffer();
                if (!TextUtils.isEmpty(responseCode)){
//                                    dataBuffer.append( "responseCode=").append(responseCode);
                    map.put("responseCode", responseCode);
                }
                if (!TextUtils.isEmpty(greekChars)){
//                                    if (dataBuffer.length() > 0){
//                                        dataBuffer.append( "&");
//                                    }
//                                    dataBuffer.append( "responseData=").append(greekChars);
                    map.put("responseData", greekChars);
                }
                if (!TextUtils.isEmpty(responseMessage)){
//                                    if (dataBuffer.length() > 0){
//                                        dataBuffer.append( "&");
//                                    }
//                                    dataBuffer.append( "responseMessage=").append(responseMessage);
                    map.put("responseMessage", responseMessage);
                }
//                                if (dataBuffer.length() > 0){
//                                    dataBuffer.append( "&");
//                                }
//                                dataBuffer.append( "responsePk=").append(responsePk);
                map.put("responsePk", responsePk);
//
//                                if (dataBuffer.length() > 0){
//                                    dataBuffer.append( "&");
//                                }
//                                dataBuffer.append( "responseStatus=").append(responseStatus).append(keyScr);
                map.put("responseStatus", responseStatus);

//                Logger.d("jjjjjjjjj", "data  = "+dataBuffer.toString());
//                String s = genApiSign(map, keyScr);
//                String md5 = MD5.stringToMD5(dataBuffer.toString());
//                Logger.d("jjjjjjjjj", "md5  = "+md5);

            }


        } catch (JSONException e) {
            e.printStackTrace();
            Logger.e("jjjjjjjjj", "e = "+e.getMessage());
        }





        



//        {"name":"张三","id":123}
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        map.put("id", 123);

        String s = JSON.toJSONString(map);
        Logger.d("jjjjjjjjj111", "s  = "+map.toString());
        Logger.d("jjjjjjjjj111", "s  = "+s);
//        Logger.d("jjjjjjjjj", "s1 = "+ JSONObject.toJSONString(map));
//        Logger.d("jjjjjjjjj", "s2 = "+ JSONObject.toJSON(map));
//        Logger.d("jjjjjjjjj", "s3 = "+ new JSONObject(map).toJSONString());
//        Logger.d("jjjjjjjjj", "s4 = "+ new JSONObject(map).toString());
//        s = "responseCode=0&responseData={\"name\":\"张三\",\"id\":123}&responseMessage=message&responsePk=0&responseStatus=trueallin1123abc";

//        String aaa="{\"name\":\"张三\",\"id\":123}";
        String escaped = StringEscapeUtils.escapeJava(map.toString());
        Logger.d("jjjjjjjjj", "escaped = "+ escaped);
        String greekChars = new UnicodeUnescaper().translate(escaped);
        Logger.d("jjjjjjjjj", "greekChars = "+ greekChars);

        String escapeds = StringEscapeUtils.escapeJava(s);
        Logger.d("jjjjjjjjj", "escapeds = "+ escapeds);
        String greekCharss = new UnicodeUnescaper().translate(escapeds);
        Logger.d("jjjjjjjjj", "greekCharss = "+ greekCharss);


        String data = "responseCode=0&responseData="+greekChars+"&responseMessage=message&responsePk=0&responseStatus=trueallin1123abc";
        Logger.d("jjjjjjjjj", "data  = "+data);
         md5 = MD5.stringToMD5(data);
        Logger.d("jjjjjjjjj", "md5 = "+ md5);


        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("连载1");
                emitter.onNext("连载2");
                emitter.onNext("连载3");
                emitter.onNext("2");
//                emitter.onComplete();
            }
        })
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG,"onSubscribe");
                    }

                    @Override
                    public void onNext(String value) {
                        if ("2".equals(value)){
                            onComplete();
                            return;
                        }
                        Log.e(TAG,"onNext:"+value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG,"onError="+e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG,"onComplete()");
                    }
                });

        //map替换类型
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        })
//                .flatMap()
                .map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "this is result " + integer;
            }
        })
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "accept : " + s +"\n" );
            }
        });

        //zip
        Observable.zip(getStringObservable(), getInterObservable(), new BiFunction<String, Integer, String>() {

            @Override
            public String apply(String s, Integer integer) throws Exception {
                return s+integer;
            }
        })
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "zip : accept : " + s + "\n");
            }
        });

        //concat 两个发射器连接成一个发射器
        Observable.concat(Observable.just(1, 2, 3, 4), Observable.just("hello", 5, 6))
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object serializable) throws Exception {
                        Log.e(TAG, "concat : "+ serializable + "\n" );
                    }
                });
        //flatMap
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onNext(4);
                e.onNext(5);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                Log.e(TAG, "flatMap : apply : " + integer + "\n");
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + integer);
                }
                int delayTime = (int) (1 + Math.random() * 100);

                return Observable.fromIterable(list).delay(delayTime, TimeUnit.MICROSECONDS);
//                return Observable.fromIterable(list);
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object s) throws Exception {
                        Log.e(TAG, "flatMap : accept : " + s + "\n");

                    }
                });

        //concatMap
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onNext(4);
                e.onNext(5);
            }
        }).concatMap(new Function<Integer, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
//                Log.e(TAG, "concatMap : apply : " + integer + "\n");
                for (int i = 0; i < 3; i++) {
                    Log.e(TAG, "concatMap : apply : i = "+i +" integer = " + integer + "\n");
                    list.add("I am value " + integer);
                }
                int delayTime = (int) (1 + Math.random() * 10);
                return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Log.e(TAG, "concatMap : accept : " + o + "\n");
                    }
                });

//去重
        Observable.just(1, 2, 3, 1, 2, 34, 45, 55)
                .distinct()
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "distinct : " + integer + "\n");
                    }
                });
        Observable.just(new String("1"), new String("2"), new String("3"), new String("1"), new String("2"), new String("34"), new String("45"), new String("55"))
                .distinct()
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String integer) throws Exception {
                        Log.e(TAG, "distinct : " + integer + "\n");
                    }
                });
        Observable.just(1, 20, 65, -5, 19)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer >= 10;
                    }
                })
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e(TAG, "filter : " + integer + "\n");
            }
        });
        //buffer
        Observable.just(1, 2, 3, 4, 5)
                .buffer(3,2)
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) throws Exception {
                        Log.e(TAG, "buffer size : " + integers.size() + "\n");
                        Log.e(TAG, "buffer value : " + integers.toString());
                        for (Integer i : integers) {
                            Log.e(TAG, i + "");
                        }
                        Log.e(TAG, "\n");
                    }
                });
        Log.e(TAG, "timer start at "  + "\n");
        //timer
        Observable.timer(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) {
                        Log.e(TAG, "timer :" + aLong + " at , ThreadName = "+Thread.currentThread().getName());
//                        Log.e(TAG, "timer :" + aLong + " at " + DateUtil.getStringDate() + "\n");
                    }
                });

        Log.e(TAG, " interval :");
        Observable.interval(3,0, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.e(TAG, " interval :" + aLong + " at "  + "\n");
//                        Log.e(TAG, "timer :" + aLong + " at " + DateUtil.getStringDate() + "\n");
                    }
                });

        Observable.just(1, 2, 3, 4)
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "doOnNext 保存 " + integer + "成功" + "\n");
                    }
                })
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e(TAG, "doOnNext :" + integer + "\n");
            }
        });

        Observable.just(1, 2, 3, 4, 5)
                .skip(2)
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "skip : "+integer + "\n");
                    }
                });

        Flowable.fromArray(1, 2, 3, 4, 5)
                .take(2)//最多接收多少个参数
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "accept: take : "+integer + "\n" );
                    }
                });

        Observable.just("1", "2",1,5,7)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(new Consumer<Serializable>() {
                    @Override
                    public void accept(Serializable serializable) throws Exception {
                        Log.e(TAG,"accept : onNext : " + serializable + "\n" );
                    }
                });

        Single.just(new Random().nextInt())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Integer value) {
                        Log.e(TAG, "single : onSuccess : "+value+"\n" );
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "single : onError : "+e.getMessage()+"\n");
                    }
                });

        Observable.merge(Observable.just(1, 2), Observable.just(3, 4, 5, 99))
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "accept : " + integer + "\n");
                    }
                });
        Observable.just(1, 2, 3)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    //我们中间采用 reduce ，支持一个 function 为两数值相加
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        Log.e("RxJavaAct", "BiFunction: apply : " + integer + "  +  " + integer2 + " = " + (integer + integer2) + "\n");

                        return integer + integer2;
                    }
                })
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e("RxJavaAct", "accept: reduce : " + integer + "\n");
            }
        });

        Observable.just(1, 2, 3)
                .scan(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        Log.e("RxJavaAct", "BiFunction: apply : " + integer + "  +  " + integer2 + " = " + (integer + integer2) + "\n");

                        return integer + integer2;
                    }
                })
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e("RxJavaAct", "accept: reduce : " + integer + "\n");
            }
        });

//        Log.e("RxJavaAct", "window\n");
//        Observable.interval(1, TimeUnit.SECONDS)
//                .take(15)
//                .window(3, TimeUnit.SECONDS)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
//                .subscribe(new Consumer<Observable<Long>>() {
//                    @Override
//                    public void accept(Observable<Long> longObservable) throws Exception {
//                        Log.e("RxJavaAct", "Sub Divide begin...\n");
//                        longObservable.subscribeOn(Schedulers.io())
//                                .observeOn(AndroidSchedulers.mainThread())
//                                .subscribe(new Consumer<Long>() {
//                                    @Override
//                                    public void accept(Long aLong) throws Exception {
//
//                                        Log.e("RxJavaAct", "Next:" + aLong + "\n");
//                                    }
//                                });
//                    }
//                });





    }


    private Observable<String> getStringObservable() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                if (!e.isDisposed()) {
                    e.onNext("A");
                    Log.e(TAG, "String emit : A \n");
                    e.onNext("B");
                    Log.e(TAG, "String emit : B \n");
                    e.onNext("C");
                    Log.e(TAG, "String emit : C \n");
                }
            }
        });

    }

    private Observable<Integer> getInterObservable() {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                if (!e.isDisposed()) {
                    e.onNext(1);
                    Log.e(TAG, "Integer emit : 1 \n");
                    e.onNext(2);
                    Log.e(TAG, "Integer emit : 2 \n");
//                    e.onNext(3);
//                    Log.e(TAG, "Integer emit : 3 \n");
//                    e.onNext(4);
//                    Log.e(TAG, "Integer emit : 4 \n");
//                    e.onNext(5);
//                    Log.e(TAG, "Integer emit : 5 \n");
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
        compositeDisposable.clear();
    }

    public void newActivity() {
        startActivity(new Intent(this, TestActivity.class));
    }

    public void Retrofit() {

        Map<String, String> mapParams = new ArrayMap<>();
        mapParams.put("userName","user01");
        mapParams.put("passWord","123456");
        RetrofitManager.INSTANCE.create(APIService.class).login(mapParams)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(new ObserverIm<ResultBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        Log.e(TAG,"onSubscribe");
                    }

                    @Override
                    public void onNext(ResultBean loginBeanResponse) {
//                        try {
                            Log.e(TAG,"onNext: loginBeanResponse = "+loginBeanResponse.toString());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG,"onError:"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        Log.e(TAG,"onComplete");
                    }
                })
        ;

        RetrofitManager.INSTANCE.create(APIService.class).getList(mapParams)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(new ObserverIm<ListBean>() {
                    @Override
                    public void onNext(ListBean loginBeanResponse) {
//                    public void onNext(List<ListBean> loginBeanResponse) {
//                        try {
                        for (TestBean listBean: loginBeanResponse.data){
                            Log.e(TAG,"onNext: listBean = "+listBean.toString());
                        }
//                            Log.e(TAG,"onNext: loginBeanResponse = "+loginBeanResponse.toString());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG,"onError:"+e.getMessage());
                    }
                })
        ;

        RetrofitManager.INSTANCE.create(APIService.class).getList2(mapParams)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(new ObserverIm<List<TestBean>>() {
                    @Override
                    public void onNext(List<TestBean> loginBeanResponse) {
//                        try {
//                        for (TestBean listBean: loginBeanResponse.data){
//                            Log.e(TAG,"onNext: listBean = "+listBean.toString());
//                        }
                            Log.e(TAG,"onNext: loginBeanResponse = "+loginBeanResponse.toString());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG,"onError:"+e.getMessage());
                    }

                })
        ;


        Type mapType = new TypeToken<Map<String, Object>>() {}.getType();
//        String sortStr = filterValue.get("sortMap").toString();
        String sortStr = "{\"firstResult\":0,\"currentDate\":\"2020-07-23 14:21:17\",\"token\":\"c79835d5e193f4d65a8cf7cc8d708942\",\"pageSize\":20,\"pageIndex\":1,\"sessionId\":1595483546999,\"maxResult\":20,\"zuultoken\":\"c37c3c0d6d2164e4366f0518a3f0311f\",\"opAdvice\":\"无,m1 metal_android5.1,Meizu\",\"logoUseFlag\":\"4\",\"apiToken\":\"xQAeQHLzwpIgBLQVKVDJTMOCmtElTIgK\",\"sessionCustomerId\":\"1592208325933\",\"multiLoginDeviceId\":\"C82E922C3B8B4FF72237CFCC550D51BE71A00F21\",\"deviceToken\":\"190e35f7e03a43dd7f6\",\"appVersion\":\"40104\",\"visitSiteId\":6,\"customerId\":\"1592208325933\",\"tVersion\":\"m1 metal\",\"osVersion\":\"android5.1\",\"opIp\":\"100.64.69.5\",\"opUsr\":\"1592208325933\",\"readFansTime\":\"2020-07-23 13:16:28\"}";
        Map<String,Object> params = new Gson().fromJson(sortStr, mapType);
//
//        RetrofitManager.INSTANCE.create(APIService.class, "http://dev-api.allinmd.cn:18080/services/")
//                .getFollowFansMapList(sortStr)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
//                .subscribe(new ObserverIm<ResponseBody>() {
//                    @Override
//                    public void onNext(ResponseBody loginBeanResponse) {
//
//                        try {
//                            //获取bodyString
//                            BufferedSource source = loginBeanResponse.source();
//                            source.request(Long.MAX_VALUE);
//                            Buffer buffer = source.buffer();
//                            Charset charset = Charset.forName("UTF-8");
//                            MediaType contentType = loginBeanResponse.contentType();
//                            if (contentType != null) {
//                                charset = contentType.charset(charset);
//                            }
//                            String bodyString = buffer.clone().readString(charset);
////                        try {
////                        for (TestBean listBean: loginBeanResponse.data){
////                            Log.e(TAG,"onNext: listBean = "+listBean.toString());
////                        }
//                            Log.e(TAG,"onNext: loginBeanResponse = "+loginBeanResponse.toString());
//                            Log.e(TAG,"onNext: bodyString = "+bodyString);
////                        } catch (IOException e) {
////                            e.printStackTrace();
////                        }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e(TAG,"onError:"+e.getMessage());
//                    }
//
//                })
//        ;
    }

    public void dataBinding() {
        startActivity(new Intent(this, DataBindingActivity.class));
    }

    public void RunAndStart() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i< 100; i++){
                    Logger.d(TAG, "1111111111"+i);
                }
            }
        });
//        thread.start();
        thread.run();
        Logger.d(TAG, "----------------");
    }

    public void suanfa() {
        int[] array = {10,9,50,12,82,64,02,42,35,45,03,54,65,56,22,33,78};
        maopao(array);
        int zheban = zheban(array, 82, 0, array.length);
        Logger.d(TAG, "zheban = "+zheban);
        zheban = zheban(array, 82);
        Logger.d(TAG, "zheban = "+zheban);

    }

    private int zheban(int[] array, int i) {
        int ritht = array.length;
        int left = 0;
        int middle = (ritht+left)/2;
        Logger.d(TAG, "left = "+left+"；ritht = "+ritht);
        while (left < ritht){
            middle = (ritht+left)/2;
            if (array[middle] > i){
                ritht = middle-1;
            }else if (array[middle] < i){
                left = middle +1;
            }else {
                return array[middle];
            }
        }
        return 0;
    }

    private int zheban(int[] array, int i, int left, int right) {
        int length;
        int middle;
        length = right - left;
        middle = (right + left)/2;
        if (length <= 1 ){
            return 0;
        }
        Logger.d(TAG, "length = "+length+"；middle = "+middle);
        if (i>array[middle]){
            return zheban(array, i, middle, right);
        }else if (i == array[middle]){
            return array[middle];
        }else if (i < array[middle]){
            return zheban(array, i, left, middle);
        }else {
            return 0;
        }
    }


    private void maopao(int[] array) {
        int length = array.length;
        int temp;
        for (int i =0; i < length-1; i++){
            for (int j = i+1; j < length; j++){
                if (array[i] > array[j]){
                    temp = array[j];
                    array[j] = array[i];
                    array[i] = temp;
                }
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int item: array){
            stringBuffer.append(item+",");
        }
        Logger.d(TAG, stringBuffer.toString());
    }

    @SuppressLint("AutoDispose")
    public void yuanchengIntent() {

//        DemoIntentService intentService = new DemoIntentService("dsfsf");
//        intentService.start
        Intent intent = new Intent(this, DemoIntentService.class);
        intent.putExtra("qqqqqq", 20);
        startService(intent);
        startActivityForResult(new Intent(this, YuanChengActivity.class), 101);
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

            }
        });
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        observable
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .subscribe(observer);

    }

    public void startServiceC() {
        startService(new Intent(this, SingASongService.class));
    }

    /**
     * 一个后台任务管理器，就是封装了线程池。
     * 可以有序执行任务。
     * 可以丢人任务列表去执行。
     *
     * 通过builder设计模式设置参数。
     * 启动线程池。
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void workManager() {
        Logger.d(TAG, "main name = "+Thread.currentThread().getName());
        OneTimeWorkRequest.Builder builder = new OneTimeWorkRequest.Builder(UpLoadWorker.class);
        OneTimeWorkRequest uploadWorkRequest = builder
                .setInitialDelay(Duration.ofMillis(20))
//                .setInputData(data)
                .build();
        WorkManager.getInstance(this).enqueue(uploadWorkRequest);
//        workA---->workB---->workC
//        WorkManager.getInstance(this)
//                .beginWith(workA)
////                .then(workB)
////                .then(workC)
//                .enqueue();

        String suffix =  "_Impl";
        Class klass = WorkDatabase.class;
        final String fullPackage = klass.getPackage().getName();
        String name = klass.getCanonicalName();
        final String postPackageName = fullPackage.isEmpty()
                ? name
                : (name.substring(fullPackage.length() + 1));
        final String implName = postPackageName.replace('.', '_') + suffix;
        //noinspection TryWithIdenticalCatches
        String s = fullPackage.isEmpty() ? implName : fullPackage + "." + implName;
        Logger.d("tangzy", "s = "+s);
    }

    public void hookAlert() {
        lalalal("qqqqqq");
//        String qqqqqq = lalalal("qqqqqq");
//        Logger.d("tangzy", "result = "+ qqqqqq);
    }

    private void lalalal(String str) {
        Logger.d("tangzy", "result = "+ str);
//        return str+"_";
    }

    public void sendCallBack() {
        handler1.sendEmptyMessage(0);

    }

    public void MyAppWidgetProvider() {

    }

    public void kotlinTest() {
        BaseUrlManager.init();

    }

    public void serviceHandler() {
        startService(new Intent(this, Demo3Service.class));
    }

    public void viewHandler() {
        startActivity(new Intent(this, MyViewGroupActivity.class));
    }

    private boolean mStop = true;
    public void tingzhixunhuan() {
        Logger.d(TAG, "tingzhixunhuan ");
        mStop = false;
    }

    public void xunhuan() {
        Logger.d(TAG, "xunhuan ");
//        mStop = true;
        Observable.timer(200, TimeUnit.MILLISECONDS)
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(new Consumer<Long>() {
                               @Override
                               public void accept(Long aLong) throws Exception {
                                   Logger.d(TAG, "xunhuan accept  aLong = "+aLong);

                               }
                           }
                );


    }

    public void listenerTest() {
        startActivity(new Intent(this, ListenerActivity.class));
    }

    public void jspdf() {
        Intent intent = new Intent(this, PdfActivity.class);
        String path = Environment.getExternalStorageDirectory().toString();
//        path = path+"/Allinmd/1589177170204/download/pdfile/216e908f890d2f4bb85900ec8ceafa8f.pdf";
        path = path+"/Allinmd/download/项目管理知识体系指南 中文 第六版.pdf";
        intent.putExtra("url", path);
        startActivity(intent);
    }

    public void pdfRendererBasic() {
        Intent intent = new Intent(this, RecycleViewActivity1.class);
        String path = Environment.getExternalStorageDirectory().toString();
//        path = path+"/Allinmd/1589177170204/download/pdfile/216e908f890d2f4bb85900ec8ceafa8f.pdf";
//        path = path+"/Allinmd/download/pdfile/123456789.pdf";
        path = path+"/Allinmd/download/项目管理知识体系指南 中文 第六版.pdf";
        intent.putExtra("url", path);
        startActivity(intent);
    }

    private static final String[] sPermissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
//大小可忽略
    public void pdfViewPage() {
        if (!EasyPermissions.hasPermissions(this, sPermissions)) {
            EasyPermissions.requestPermissions(this, getString(com.mingyuechunqiu.recordermanager.R.string.rm_warn_allow_record_video_permissions), 1, sPermissions);
        } else {
            Intent intent = new Intent(this, RecycleViewActivity.class);
            String path = Environment.getExternalStorageDirectory().toString();
//        path = path+"/Allinmd/3D打印技术在足踝外科的应用价值.pdf";
//            path = path+"/Allinmd/download/pdfile/216e908f890d2f4bb85900ec8ceafa8f.pdf";
//            path = path+"/Allinmd/download/项目管理知识体系指南 中文 第六版.pdf";
            path = path+"/Allinmd/download/3D打印技术在足踝外科的应用价值.pdf";
//            path = path+"/Allinmd/12345678.pdf";
            File file = new File(path);
            if (!file.exists()){
                Logger.e("不存在");
            }
            intent.putExtra("url", path);
            startActivity(intent);
        }
    }
//    boolean bbb = true;
    public void customSnackBar() {
        ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content).getRootView();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if(!Settings.canDrawOverlays(getApplicationContext())) {
//                //启动Activity让用户授权
//                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
//                intent.setData(Uri.parse("package:" + getPackageName()));
//                startActivityForResult(intent,100);
//                return;
//            }
//        }
//        if (bbb){
            Logger.d("hhhhhhh", "show");
            SnackbarCus snackbarCus = SnackbarCus.make(this, "工作人员会在3个工作日内进行审核，结果会通过app给您通知，也可能会有运营人员电话联系您 。", SnackbarCus.LENGTH_LONG /* duration */);
//            SnackbarCus snackbarCus = SnackbarCus.make(this, "message", SnackbarCus.LENGTH_SHORT /* duration */);
            SnackbarManagerCus.show(snackbarCus);
//            bbb = false;


//        CustomToast.makeDkToast(this,"显示效果",10000).show();

//        startActivity(new Intent(this, DataBindingActivity.class));
//        }else {
//            Logger.d("hhhhhhh", "show");
//            SnackbarManagerCus.dismiss();
//            bbb = true;
//        }
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public void loaderSDClass() {

    }

    public void toViewPageActivity() {
        startActivity(new Intent(this, ViewPageActivity.class));
    }

    public void toMedialLib() {
        startActivity(new Intent(this, VideoLibActivity.class));
    }

    public void toNavigation() {
        startActivity(new Intent(this, NavigationLibActivity.class));
    }


    class Producer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (LOCK) {
                    while (count == FULL) {
                        try {
                            LOCK.wait();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    count++;
                    Log.d(TAG, Thread.currentThread().getName() + "生产者生产，目前总共有" + count);
                    LOCK.notifyAll();
                }
            }
        }
    }

    class Consumer_ implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (LOCK) {
                    while (count == 0) {
                        try {
                            LOCK.wait();
                        } catch (Exception e) {
                        }
                    }
                    count--;
                    Log.d(TAG, Thread.currentThread().getName() + "消费者消费，目前总共有" + count);
                    LOCK.notifyAll();
                }
            }
        }
    }

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    private Condition condition2 = lock.newCondition();

    public void serviceA() {
        try {
            lock.lock();
            condition.await();//线程挂起，进入等待中,和wait一样。
            Log.d(TAG, Thread.currentThread().getName() + "----Ai=");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void serviceB() {
        try {
            lock.lock();
            Log.d(TAG, Thread.currentThread().getName() + "----Bi=");
            condition.signal();//唤醒被await的线程
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void serviceC() {
        try {
            lock.lock();
            Log.d(TAG, Thread.currentThread().getName() + "----Ci=");
            condition2.await();//condition2的等候，只有condition2.signal才能唤醒哦
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void serviceD() {
        try {
            lock.lock();
            Log.d(TAG, Thread.currentThread().getName() + "----Di=");
            condition2.signal();//唤醒被condition2.await的线程
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public  void isServiceRunning(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(200)) {
            Log.e(TAG, "service.service.getClassName() = " + service.service.getPackageName()   );
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.d("tangzy", "onActivityResult");
        if (resultCode == Activity.RESULT_OK && requestCode == 0) {
//            Uri uri = data.getData();
            RecordVideoResultInfo info = data.getParcelableExtra(EXTRA_RECORD_VIDEO_RESULT_INFO);
            Log.e("MainActivity", "onActivityResult: " + " "
                    + info.getDuration() + " " + info.getFilePath());
        }else if (resultCode == Activity.RESULT_OK && requestCode == 101) {
            Logger.d("tangzy", "onActivityResult 101");
           String hhhhhh =  data.getStringExtra("hhhhhh");
            Logger.d("tangzy", "hhhhhh = "+hhhhhh);

        }

    }

}
