package com.tangzy.tzymvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.tangzy.tzymvp.MainActivity;
import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.bean.DataBean;
import com.tangzy.tzymvp.net.NetUtil;
import com.tangzy.tzymvp.net.OkHttpManager;
import com.tangzy.tzymvp.net.ResponseListener;
import com.tangzy.tzymvp.test.Outer;
import com.tangzy.tzymvp.util.Logger;
import com.tangzy.tzymvp.util.Toasts;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

/**
 * ${CLASS} class
 *
 * @author Administrator
 * @date 2019/7/17
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivyty";
    private EditText et_login_id;
    private EditText et_login_password;
    private TextView tv_forget_password;
    private TextView tv_login_register;
    private TextView tv_login;
    private String userName;
    private String userPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Logger.d("hanyang", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }
    private void init() {
        et_login_id = findViewById(R.id.et_login_id);
        et_login_password = (EditText) findViewById(R.id.et_login_password);
        tv_login = (TextView) findViewById(R.id.tv_login);
        tv_login.setOnClickListener(this);
//        CustomDialog ustomDialog =  new CustomDialog(context);
//        Class<? extends CustomDialog> aClass = ustomDialog.getClass();
//        Field[] fields = ustomDialog.getClass().getFields();
//        Field[] declaredFields = aClass.getDeclaredFields();
        tv_forget_password = (TextView) findViewById(R.id.tv_forget_password);
        tv_forget_password.setOnClickListener(this);
        tv_login_register = (TextView) findViewById(R.id.tv_login_register);
        tv_login_register.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_login:
//                ARouter.getInstance().build("/test/TestActivity").navigation();
                DataBean dataBean = new DataBean();
                dataBean.setName("张三");
                // 2. 跳转并携带参数
                ARouter.getInstance().build("/test/MainActivity")
                        .withLong("key1", 666L)
                        .withString("key3", "888")
                        .withParcelable("key4", dataBean)
                        .navigation();

//                if (netCheck()&&textCheck()){
//                    final JSONObject httpParams =  new JSONObject();
//                    try {
//                        httpParams.put("userName",userName);
//                        httpParams.put("passWord",userPassword);
//                        HashMap<String, File> files = new HashMap<>();
//                        OkHttpManager.INSTANCE.asyncRequest("test/login.php", httpParams, files, listener, true, false);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    httpParams.put("devicecode", "343257687798790");
//                    final String url = Constant.url+"login";
//                    final String url = "http://www.baidu.com";

//                    OkHttpManager.asyncRequest("mobileLogin.php", httpParams,listener,false);

//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            AsyncOkHttpManager.asyncHttpRequest(url ,httpParams, false);
//                        }
//                    }).start();

//
//                    ResponseResult responseResult =null;
//                    try {
//                         responseResult = AsyncOkHttpManager.asyncRequest(url ,httpParams, false);
//
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
////
//                    String status = responseResult.getStatus();
//                    Response response = responseResult.getResponse();
//
//
//
//                    stopProgressDialog();
//                    MiddleView.getInstance().startCleanActivity(FirstPageAc.class, null);

//                }


                break;
//            case R.id.tv_forget_password:
//                MiddleView.getInstance().startCallbackActivity(ForgetAc.class, null);
//
//                break;
//            case R.id.tv_login_register:
//                MiddleView.getInstance().startCallbackActivity(RegisterAc.class, null);

            default:
                break;
        }

    }
    private ResponseListener listener = new ResponseListener() {
        @Override
        public void onResp(String respons, String uri) {
            Logger.d(TAG, "onResp = "+respons);
//            stopProgressDialog();
            JSONObject json = null;
            try {
                json = new JSONObject(respons);
                Toasts.showToastShort(json.optString("retMessage"));
                if (json.optString("retCode").equals("0")){
//                    MiddleView.getInstance().startCleanActivity(FirstPageAc.class, null);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            } catch (JSONException e) {
                Toasts.showToastShort("返回数据格式不正确");
                e.printStackTrace();
            }
        }

        @Override
        public void onErr(int errorCode, String respons, String uri) {
//            stopProgressDialog();
            Logger.d(TAG, "onErr = "+respons);
            Toasts.showToastShort("网络地址错误");
        }

    };


    private boolean textCheck(){
        userName = et_login_id.getText().toString().trim();
        userPassword = et_login_password.getText().toString().trim();
        if (userName.equals("")) {
            Toasts.showToastLong( "用户名不能为空");
            return false;
        }
        if (userPassword.equals("")) {
            Toasts.showToastLong( "密码不能为空");
            return false;
        }
        return true;
    }

    private boolean netCheck() {
        if (!NetUtil.checkNetType(this)) {
            Toasts.showToastLong( "请打开手机网络连接");
            return false;
        }
        return true;
    }

    @Override
    protected void onStart() {
        Logger.d("hanyang", "onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Logger.d("hanyang", "onResume");
        super.onResume();
    }

    @Override
    protected void onRestart() {
        Logger.d("hanyang", "onRestart");
        super.onRestart();
    }

    @Override
    protected void onPause() {
        Logger.d("hanyang", "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Logger.d("hanyang", "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Logger.d("hanyang", "onDestroy");
        super.onDestroy();
    }
}
