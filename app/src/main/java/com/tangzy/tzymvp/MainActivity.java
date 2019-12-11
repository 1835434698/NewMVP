package com.tangzy.tzymvp;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.mingyuechunqiu.recordermanager.data.bean.RecordVideoRequestOption;
import com.mingyuechunqiu.recordermanager.data.bean.RecordVideoResultInfo;
import com.mingyuechunqiu.recordermanager.feature.record.RecorderManagerFactory;
import com.tangzy.tzymvp.activity.AiduActivity;
import com.tangzy.tzymvp.activity.DemoActivity;
import com.tangzy.tzymvp.activity.IatDemo;
import com.tangzy.tzymvp.activity.ShowWaveActivity;
import com.tangzy.tzymvp.activity.TwoActivity;
import com.tangzy.tzymvp.activity.TzyActivity;
import com.tangzy.tzymvp.activity.WebActivity;
import com.tangzy.tzymvp.annotation.ParseAnnotation;
import com.tangzy.tzymvp.annotation.Test;
import com.tangzy.tzymvp.bean.DataBean;
import com.tangzy.tzymvp.bean.TzyBean;
import com.tangzy.tzymvp.bean.UserBean;
import com.tangzy.tzymvp.test.ChredUser;
import com.tangzy.tzymvp.util.FileUtils;
import com.tangzy.tzymvp.util.Logger;
import com.tangzy.tzymvp.util.RsaUtils;
import com.tangzy.tzymvp.util.Utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.mingyuechunqiu.recordermanager.data.constants.Constants.EXTRA_RECORD_VIDEO_RESULT_INFO;

@Route(path = "/test/activity")
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "tangzy";


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

    private ImageView iv_gif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);//添加在onCreate（）
        setContentView(R.layout.activity_main);
        handler.sendEmptyMessage(0);
//        Constant.app = this;
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
        LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>();

        Logger.d("tangzy", "key1 = " + key1 + ".key3 = " + key3 + ".key4 = " + key4.getName());

        iv_gif = findViewById(R.id.iv_gif);
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

    public class EcilInstrumentation extends Instrumentation {

        Instrumentation mBase;

        public EcilInstrumentation(Instrumentation base) {
            mBase = base;
        }

//        public ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token, Activity target,
//                                                Intent intent, int requestCode, Bundle options){
//            Logger.d(TAG, "z s 到此一游");
//            Class[] p1 = {Context.class, IBinder.class,
//            IBinder.class, Activity.class,
//            Intent.class, int.class, Bundle.class};
//            Object[] v1 = {who, contextThread, token, target,
//            intent, requestCode, options};
//            return RefInvoke.invokeInstanceMethod()
//
//        }

    }

    public class InvocationHandlerForTest implements InvocationHandler {
        private Object target;

        public InvocationHandlerForTest(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Logger.d(TAG, "日志开始");
            Object object = method.invoke(target, args);
            Logger.d(TAG, "日志结束");
            return object;
        }
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
                startActivity(new Intent(this, WebActivity.class));

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
                startActivity(new Intent(this, DemoActivity.class));

//                Iuser user = new UserImpl();
//                InvocationHandler h = new DynamicProxy(user);
//                Iuser proxy = (Iuser) Proxy.newProxyInstance(Iuser.class.getClassLoader(), new Class[]{Iuser.class}, h);
//                proxy.eat("苹果");
//                proxy.eat2("juzi");
                ChredUser chredUser = new ChredUser();

                break;
            default:
                break;
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
        }
    }
}
