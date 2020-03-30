package com.tangzy.tzymvp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.impl.WorkDatabase;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.mingyuechunqiu.recordermanager.data.bean.RecordVideoRequestOption;
import com.mingyuechunqiu.recordermanager.data.bean.RecordVideoResultInfo;
import com.mingyuechunqiu.recordermanager.feature.record.RecorderManagerFactory;
import com.tangzy.tzymvp.activity.AiduActivity;
import com.tangzy.tzymvp.activity.DataBindingActivity;
import com.tangzy.tzymvp.activity.DemoActivity;
import com.tangzy.tzymvp.activity.IatDemo;
import com.tangzy.tzymvp.activity.NestedScrollViewActivity;
import com.tangzy.tzymvp.activity.RecyclerViewActivity;
import com.tangzy.tzymvp.activity.ShowWaveActivity;
import com.tangzy.tzymvp.activity.TestActivity;
import com.tangzy.tzymvp.activity.TwoActivity;
import com.tangzy.tzymvp.activity.TzyActivity;
import com.tangzy.tzymvp.activity.WebActivity;
import com.tangzy.tzymvp.activity.YuanChengActivity;
import com.tangzy.tzymvp.annotation.ParseAnnotation;
import com.tangzy.tzymvp.annotation.Test;
import com.tangzy.tzymvp.bean.DataBean;
import com.tangzy.tzymvp.bean.Info;
import com.tangzy.tzymvp.bean.TzyBean;
import com.tangzy.tzymvp.bean.UserBean;
import com.tangzy.tzymvp.hook.HookSetOnClickListenerHelper;
import com.tangzy.tzymvp.net.api.APIService;
import com.tangzy.tzymvp.net.bean.ListBean;
import com.tangzy.tzymvp.net.bean.ResultBean;
import com.tangzy.tzymvp.net.bean.TestBean;
import com.tangzy.tzymvp.net.retrofit.ObserverIm;
import com.tangzy.tzymvp.net.retrofit.RetrofitManager;
import com.tangzy.tzymvp.servive.DemoIntentService;
import com.tangzy.tzymvp.servive.DemoServive;
import com.tangzy.tzymvp.servive.SingASongService;
import com.tangzy.tzymvp.test.ChredUser;
import com.tangzy.tzymvp.test.DynamicProxy;
import com.tangzy.tzymvp.test.Iuser;
import com.tangzy.tzymvp.test.UserImpl;
import com.tangzy.tzymvp.util.FileType;
import com.tangzy.tzymvp.util.FileUtils;
import com.tangzy.tzymvp.util.Logger;
import com.tangzy.tzymvp.util.OnyWayLinkedList;
import com.tangzy.tzymvp.util.RsaUtils;
import com.tangzy.tzymvp.util.Utils;
import com.tangzy.tzymvp.view.CustomDialogFragment;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.mingyuechunqiu.recordermanager.data.constants.Constants.EXTRA_RECORD_VIDEO_RESULT_INFO;

@Route(path = "/test/MainActivity")
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "MainActivity";


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);//添加在onCreate（）
        setContentView(R.layout.activity_main);
        handler.sendEmptyMessage(0);
//        Constant.app = this;
        setListener();
        LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>();

        try {
            Logger.d("tangzy", "key1 = " + key1 + ".key3 = " + key3 + ".key4 = " + key4.getName());
        }catch (Exception e){

        }
        init();
        initNet();
        initData();


    }

    @Override
    protected void onResume() {
        super.onResume();
        hhhhhh();
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
//        registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
//            @Override
//            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
//
//            }
//
//            @Override
//            public void onActivityStarted(@NonNull Activity activity) {
//
//            }
//
//            @Override
//            public void onActivityResumed(@NonNull Activity activity) {
//
//            }
//
//            @Override
//            public void onActivityPaused(@NonNull Activity activity) {
//
//            }
//
//            @Override
//            public void onActivityStopped(@NonNull Activity activity) {
//
//            }
//
//            @Override
//            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
//
//            }
//
//            @Override
//            public void onActivityDestroyed(@NonNull Activity activity) {
//
//            }
//        });
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
    }

    private void setListener() {
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void videoRecord(View view) {
        FileUtils.makesureFileExist(filePath);
        RecordVideoRequestOption option = new RecordVideoRequestOption();
        option.setMaxDuration(20);
        option.setFilePath(filePath);
        option.setmCameraType(1);
        RecorderManagerFactory.getRecordVideoRequest().startRecordVideo(this, 0, option);
    }

    public void getPicture(View view) {
        Utils.muxerImg(filePath, SDCARD_PATH);
    }

    public void toSufaceView(View view) {
        startActivity(new Intent(this, ShowWaveActivity.class));
    }

    /**
     * 注解
     * 注解就是给类起了一个别名。
     *
     * @param view
     */
    public void toAnnotation(View view) {
        /**
         * 直接调用
         */
        Test.main(null);
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

    public void loader(View view) {
        ARouter.getInstance().build("/demoLoader/mainActivity")
                .navigation();
    }

    public void notification(View view) {
        test.notifyKJ(this);
    }

    public void RSA(View view) {
        RsaUtils.main();

//        String publicKey = "fhasflajkdsfalksff";
////        String publicKey = "fhasflajkdsfalksfQ";
//        byte[] a = RsaUtils.getA(publicKey);
//        Logger.d("tangzy1", Rsa2Utils.getA(a));
//        Logger.d("tangzy1", a);
    }

    private static final String SUCCESS = "success";

    public void threadPool(View view) {
//        ExecutorService service = Executors.newFixedThreadPool(4);//全部核心线程
//        Executors.newCachedThreadPool()//全部非核心线程
//        Executors.newScheduledThreadPool(4);//有数量固定的核心线程，且有数量无限多的非核心线程
//        Executors.newSingleThreadExecutor();//内部只有一个核心线程

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    Log.d("tangzy", "execute");

                }
            });

        }
        Future<Object> submit = threadPoolExecutor.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Log.d("tangzy", "call");
                Thread.sleep(5000);

                return SUCCESS;
            }
        });
        try {
            String s = (String) submit.get();
            if (SUCCESS.equals(s)) {
                String name = Thread.currentThread().getName();
                Log.d("tangzy", "经过返回值比较，submit方法执行任务成功    thread name: " + name);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
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

    public void Toast(View view) {
        showToast("1213234");
    }

    public void DialogFragment(View view) {
        CustomDialogFragment.showDialog(this);
    }

    public void nestedScrollView(View view) {
        startActivity(new Intent(this, NestedScrollViewActivity.class));
    }

    public void proxy(View view) {
        Iuser user = new UserImpl();
        InvocationHandler h = new DynamicProxy(user);
        Iuser proxy = (Iuser) Proxy.newProxyInstance(Iuser.class.getClassLoader(), new Class[]{Iuser.class}, h);
        proxy.eat("苹果");
        proxy.eat2("juzi");
        proxy.eat2("123", "456");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
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
                break;
            case R.id.button2:
                Intent intent1 = new Intent(this, WebActivity.class);
                intent1.putExtra("path", "android_asset/index.html");
                startActivity(intent1);

//                if (AnnotationUse.class.isAnnotationPresent(MyAnnotation.class)){
//                    MyAnnotation annotation = AnnotationUse.class.getAnnotation(MyAnnotation.class);
//                    Logger.d("tangzy", "annotation = "+annotation.color());
//                }else {
//                    Logger.d("tangzy", "annotation = no");
//                }

                break;
            case R.id.button3:
                startActivity(new Intent(this, TwoActivity.class));
                break;
            case R.id.button4:
                startActivity(new Intent(this, AiduActivity.class));
                break;
            case R.id.button5:
                startActivity(new Intent(this, IatDemo.class));
                break;
            case R.id.button6:
                intent = new Intent(this, DemoServive.class);
                intent.putExtra("kfc", 10);
//            intent.putExtra("kfc", kfc);
//                startService(intent);
                bindService(intent, con, Context.BIND_AUTO_CREATE);
                startActivity(new Intent(this, DemoActivity.class));

                ChredUser chredUser = new ChredUser();

                break;
            default:
                break;
        }
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

    public void leetCode(View view) {
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
    public void producerConsumer(View view) {
        new Thread(new Producer()).start();
        new Thread(new Consumer()).start();
        new Thread(new Producer()).start();
        new Thread(new Consumer()).start();
        new Thread(new Producer()).start();
        new Thread(new Consumer()).start();
        new Thread(new Producer()).start();
        new Thread(new Consumer()).start();

    }

    public void hook(View view) {
        HookSetOnClickListenerHelper.hook(this, view);

    }

    public void genericity(View view) {
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

    public void wayLinkedList(View view) {
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

    public void reentrantLock(View view) {
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

    public void recyclerView(View view) {
        startActivity(new Intent(this, RecyclerViewActivity.class));
    }
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    public void RxJava(View view) {
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    public void newActivity(View view) {
        startActivity(new Intent(this, TestActivity.class));
    }

    public void Retrofit(View view) {

        Map<String, String> mapParams = new ArrayMap<>();
        mapParams.put("userName","user01");
        mapParams.put("passWord","123456");
        RetrofitManager.INSTANCE.create(APIService.class).login(mapParams)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(new ObserverIm<ResultBean>() {
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
    }

    public void dataBinding(View view) {
        startActivity(new Intent(this, DataBindingActivity.class));
    }

    public void RunAndStart(View view) {
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

    public void suanfa(View view) {
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
    public void yuanchengIntent(View view) {

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

    public void startServiceC(View view) {
        startService(new Intent(this, SingASongService.class));
    }

    /**
     * 一个后台任务管理器，就是封装了线程池。
     * 可以有序执行任务。
     * 可以丢人任务列表去执行。
     *
     * 通过builder设计模式设置参数。
     * 启动线程池。
     * @param view
     */
    public void workManager(View view) {
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

    public void hookAlert(View view) {
        lalalal("qqqqqq");
//        String qqqqqq = lalalal("qqqqqq");
//        Logger.d("tangzy", "result = "+ qqqqqq);
    }

    private void lalalal(String str) {
        Logger.d("tangzy", "result = "+ str);
//        return str+"_";
    }

    public void sendCallBack(View view) {
        handler1.sendEmptyMessage(0);

    }
//    private String lalalal(String qqqqqq) {
//        return qqqqqq+"_";
//    }


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

    class Consumer implements Runnable {
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
