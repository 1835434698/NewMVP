package com.tangzy.tzymvp;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
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
import com.tangzy.tzymvp.bean.DataBean;
import com.tangzy.tzymvp.bean.TzyBean;
import com.tangzy.tzymvp.bean.UserBean;
import com.tangzy.tzymvp.test.ChredUser;
import com.tangzy.tzymvp.util.FileUtils;
import com.tangzy.tzymvp.util.Logger;
import com.tangzy.tzymvp.util.Utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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



    String filePath = Environment.getExternalStorageDirectory().getPath() + "/aaaaa/123456.mp4";
    String SDCARD_PATH = Environment.getExternalStorageDirectory().getPath() + "/aaaaa/";
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
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

        Logger.d("tangzy", "key1 = "+key1+".key3 = "+key3+".key4 = "+key4.getName());


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
