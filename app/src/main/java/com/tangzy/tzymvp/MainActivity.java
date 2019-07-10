package com.tangzy.tzymvp;

import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.tangzy.tzymvp.activity.AiduActivity;
import com.tangzy.tzymvp.activity.DemoActivity;
import com.tangzy.tzymvp.activity.TzyActivity;
import com.tangzy.tzymvp.activity.WebActivity;
import com.tangzy.tzymvp.bean.DataBean;
import com.tangzy.tzymvp.bean.TzyBean;
import com.tangzy.tzymvp.bean.UserBean;
import com.tangzy.tzymvp.test.ChredUser;
import com.tangzy.tzymvp.util.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "tangzy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Constant.app = this;
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
        LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>();


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
//            case R.id.button3:
//                startActivity(new Intent(this, ShellActivity.class));
//                break;
//            case R.id.button4:
//                startActivity(new Intent(this, com.tangzy.themvp.samples.demo4.ShellActivity.class));
//                break;
            case R.id.button5:
                startActivity(new Intent(this, AiduActivity.class));
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
    }
}
