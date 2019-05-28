package com.tangzy.tzymvp.activity.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.tangzy.themvp.databind.BaseDataBinder;
import com.tangzy.themvp.model.IModel;
import com.tangzy.themvp.presenter.BaseActivityPresenter;
import com.tangzy.themvp.view.IDelegate;
import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.core.presenter.MvpPresenterIml;
import com.tangzy.tzymvp.core.view.NetView;
import com.tangzy.tzymvp.listener.NoDoubleClickListener;
import com.tangzy.tzymvp.permission.EasyPermissions;
import com.tangzy.tzymvp.util.Logger;
import com.tangzy.tzymvp.util.StatusBarUtil;
import com.tangzy.tzymvp.view.ProgressDialog;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivity <T extends IDelegate> extends
        BaseActivityPresenter<T> implements NetView, EasyPermissions.PermissionCallbacks {
    private static final String TAG = "BaseActivity";
    protected BaseDataBinder binder;
    private TextView tvTitle;
    private ImageView ivTitleBack;

    private List<MvpPresenterIml> mvpPresenterImls;
    protected int colorTitle = R.color.color_53B890;
    protected boolean dark = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = getDataBinder();
        setTitleLayout(colorTitle);
        setStatusBarFontIconDark(dark);
        StatusBarUtil.setTranslucentForImageView(BaseActivity.this, 0);
    }

    protected void setTitleBack(int gone) {
        if (ivTitleBack != null){
            ivTitleBack.setVisibility(gone);
        }
    }
    protected void setTitle(String title){
        if (tvTitle != null){
            tvTitle.setText(title);
        }
    }

    public void setTitleLayout(@ColorRes int id) {
        View title = findViewById(R.id.title);
        if (title  != null){
            ivTitleBack = findViewById(R.id.iv_title_back);
            ivTitleBack.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    finish();
                }
            });
            tvTitle = findViewById(R.id.tv_title);
            title.setBackgroundColor(ContextCompat.getColor(this, id));
        }
    }
    /**
     * 设置Android状态栏的字体颜色，状态栏为亮色的时候字体和图标是黑色，状态栏为暗色的时候字体和图标为白色
     *
     * @param dark 状态栏字体是否为深色
     */
    public void setStatusBarFontIconDark(boolean dark) {
        Logger.d(TAG, "dark = "+dark);
        // 小米MIUI
        try {
            Window window = getWindow();
            Class clazz = getWindow().getClass();
            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            if (dark) {    //状态栏亮色且黑色字体
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
            } else {       //清除黑色字体
                extraFlagField.invoke(window, 0, darkModeFlag);
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }

        // 魅族FlymeUI
        try {
            Window window = getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (dark) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            window.setAttributes(lp);
        } catch (Exception e) {
//            e.printStackTrace();
        }
        // android6.0+系统
        // 这个设置和在xml的style文件中用这个<item name="android:windowLightStatusBar">true</item>属性是一样的
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (dark) {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        cleanPresenter();
    }

    public abstract BaseDataBinder getDataBinder();

    public <D extends IModel> void notifyModelChanged(D data) {
        if (binder != null) {
            binder.viewBindModel(viewDelegate, data);
        }
    }

    public void addPresenter(MvpPresenterIml presenterIml){
        Logger.d(TAG, "addPresenter");
        if (mvpPresenterImls == null){
            mvpPresenterImls = new ArrayList<>();
        }
        mvpPresenterImls.add(presenterIml);
    }

    public void cleanPresenter(){
        Logger.d(TAG, "cleanPresenter");
        if (mvpPresenterImls !=null && mvpPresenterImls.size() > 0){
            int size = mvpPresenterImls.size();
            for (int i=0; i < size; i ++){
                MvpPresenterIml mvpPresenterIml = mvpPresenterImls.get(i);
                if (mvpPresenterIml != null) {
                    Logger.d(TAG, "detachView");
                }
                mvpPresenterIml.detachView(false);
            }
        }
    }

    protected static final int RC_PERM = 123;

    protected static int reSting = R.string.ask_again;//默认提示语句
    private CheckPermListener mListener;

    public interface CheckPermListener {
        //权限通过后的回调方法
        void superPermission();
    }
    public void checkPermission(CheckPermListener listener, int resString, String... mPerms) {
        mListener = listener;
        List<String> list = new ArrayList<>();
        for (String item: mPerms) {
            if (!EasyPermissions.hasPermissions(this, item)) {
                list.add(item);
            }
        }
        int length = list.size();
        if (length>0){
            String[] perms = new String[list.size()];
            for (int i = 0; i<length; i++){
                perms[i] = list.get(i);
            }
            EasyPermissions.requestPermissions(this, getString(resString),
                    RC_PERM, perms);
        }else {
            if (mListener != null) {
                mListener.superPermission();
            }
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        //同意了某些权限可能不是全部
    }

    @Override
    public void onPermissionsAllGranted() {
        if (mListener != null) {
            mListener.superPermission();//同意了全部权限的回调
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

        EasyPermissions.checkDeniedPermissionsNeverAskAgain(this,
                getString(R.string.perm_tip),
                R.string.setting, R.string.cancel, null, perms);
    }
    /**
     * 用户权限处理,
     * 如果全部获取, 则直接过.
     * 如果权限缺失, 则提示Dialog.
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private ProgressDialog progressDialog;

    @Override
    public void showLoading() {
        showLoading("");
    }

    @Override
    public void showLoading(String waitMessage) {
        if (progressDialog == null){
            progressDialog = new ProgressDialog(this);
        }
        progressDialog.setMessage(waitMessage).show();
//        ProgressDialog.getInstance(this).setMessage(waitMessage);
//        ProgressDialog.getInstance(this).show();
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null){
            progressDialog.dismiss();
        }
    }
}
