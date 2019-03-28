package com.tangzy.tzymvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tangzy.tzymvp.activity.Demo2Activity;
import com.tangzy.tzymvp.activity.DemoActivity;
import com.tangzy.tzymvp.activity.TzyActivity;
import com.tangzy.tzymvp.bean.DataBean;
import com.tangzy.tzymvp.bean.TzyBean;
import com.tangzy.tzymvp.bean.UserBean;
import com.tangzy.tzymvp.util.Logger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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
                startActivity(new Intent(this, DemoActivity.class));
//                break;
//            case R.id.button3:
//                startActivity(new Intent(this, ShellActivity.class));
//                break;
//            case R.id.button4:
//                startActivity(new Intent(this, com.tangzy.themvp.samples.demo4.ShellActivity.class));
//                break;
//            case R.id.button5:
//                startActivity(new Intent(this, Demo5Activity.class));
//                break;
//            case R.id.button6:
//                startActivity(new Intent(this, Demo6Activity.class));
//                break;
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
