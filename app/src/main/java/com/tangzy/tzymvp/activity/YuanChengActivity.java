package com.tangzy.tzymvp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.servive.DemoIntentService;
import com.tangzy.tzymvp.view.toast.SnackbarCus;
import com.tangzy.tzymvp.view.toast.SnackbarManagerCus;

public class YuanChengActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yuancheng);
        SnackbarCus snackbarCus = SnackbarCus.make(this, "工作人员会在3个工作日内进行审核，结果会通过app给您通知，也可能会有运营人员电话联系您 。", SnackbarCus.LENGTH_LONG /* duration */);
//            SnackbarCus snackbarCus = SnackbarCus.make(this, "message", SnackbarCus.LENGTH_SHORT /* duration */);
        SnackbarManagerCus.show(snackbarCus);

        Intent intent1 = new Intent(this, Web1Activity.class);
        intent1.putExtra("path", "android_asset/index.html");
        startActivity(intent1);
    }

    public void backResult(View view) {
        Intent intent = new Intent();
        intent.putExtra("hhhhhh", "9999999");
        setResult(Activity.RESULT_OK, intent);
        finish();

    }

    public void startIntentService(View view) {
        Intent intent = new Intent(this, DemoIntentService.class);
        intent.putExtra("qqqqqq", 40);
        startService(intent);
    }
}
