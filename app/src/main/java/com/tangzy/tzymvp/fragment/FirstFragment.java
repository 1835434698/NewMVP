package com.tangzy.tzymvp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.tangzy.themvp.databind.BaseDataBinder;
import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.databind.FragmentActivityBinder;
import com.tangzy.tzymvp.fragment.base.BaseFragment;
import com.tangzy.tzymvp.listener.NoDoubleClickListener;
import com.tangzy.tzymvp.util.Logger;
import com.tangzy.tzymvp.viewbind.FirstFragDelegate;

import java.util.List;

public class FirstFragment extends BaseFragment<FirstFragDelegate> {
    private static final String TAG = "tzy11";
    @Override
    protected Class<FirstFragDelegate> getDelegateClass() {
        return FirstFragDelegate.class;
    }

    @Override
    public BaseDataBinder getDataBinder() {




        return new FragmentActivityBinder();
    }

    protected void showKeyboard(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
//        editText.findFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(editText, 0);
    }
    private EditText mEtInvitation;



    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.get(R.id.button).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN://0
                        Logger.d("TAG", " onTouch按住");
                        break;
                    case MotionEvent.ACTION_UP://1
                        Logger.d("TAG", " onTouch抬起");
                        break;
                    case MotionEvent.ACTION_MOVE://2
                        Logger.d("TAG", " onTouch移动");
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        viewDelegate.get(R.id.button).setOnClickListener(noDoubleClickListener);
        mEtInvitation = (EditText) viewDelegate.get(R.id.edittext);
//        mEtInvitation.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
//            @Override
//            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
//                showKeyboard(mEtInvitation);
//            }
//        });
//        showKeyboard(mEtInvitation);
    }

    private void getAppList() {
        PackageManager pm = getActivity().getPackageManager();
        // Return a List of all packages that are installed on the device.
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        for (PackageInfo packageInfo : packages) {
            // 判断系统/非系统应用
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) // 非系统应用
            {
                Logger.d(TAG, "MainActivity.getAppList, packageInfo=" + pm.getApplicationLabel(packageInfo.applicationInfo));
                System.out.println("MainActivity.getAppList, packageInfo=" + packageInfo.packageName);
            } else {
                // 系统应用
            }
        }


//
//        PackageManager pm = getActivity().getPackageManager();
//
//        String examplePackageName = "com.jeejen.family"; //请修改为需要设置的 package name
//        String exampleActivityName = "com.jeejen.home.launcher.Launcher"; //请修改为需要设置的 launcher activity name
//
//        ComponentName defaultLauncher = null;
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_HOME);
//
//        List<ResolveInfo> resolveInfoList =  pm.queryIntentActivities(intent, 0);
//        for (ResolveInfo resolveInfo : resolveInfoList) {
////             判断系统/非系统应用
////            if ((resolveInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) // 非系统应用
////            {
//                Logger.d(TAG, "MainActivity.getAppList, packageInfo=" + resolveInfo.resolvePackageName);
////                System.out.println("MainActivity.getAppList, packageInfo=" + packageInfo.packageName);
////            } else {
////                // 系统应用
////            }
//        }


        Logger.d(TAG, "----------------------" );

//        ArrayList<String> result = new ArrayList<String>();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
//        intent.setPackage(getActivity().getPackageName());
        for (ResolveInfo info : getActivity().getPackageManager().queryIntentActivities(intent, 0)) {
//            result.add(info.activityInfo.name);
            Logger.d(TAG, "info.activityInfo.name = "+info.loadLabel(getActivity().getPackageManager()) );
        }

                Logger.d(TAG, "11111111111111111111" );

        ResolveInfo bestMatch = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);

        List<ResolveInfo> allMatches = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo info: allMatches) {
            Logger.d(TAG, "info.activityInfo.name = "+info.loadLabel(getActivity().getPackageManager()) );

        }
        Logger.d(TAG, "11111111111111111111" );
    }


    NoDoubleClickListener noDoubleClickListener = new NoDoubleClickListener() {
        @Override
        public void onNoDoubleClick(View v) {
            switch (v.getId()){
                case R.id.button:
                    Logger.d("TAG", " OnClick");
//                    showKeyboard(mEtInvitation);
//                    getAppList();


                    break;
                default:
                    break;
            }
        }
    };
}
