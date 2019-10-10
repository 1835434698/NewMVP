package com.tangzy.tzymvp.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;


import androidx.annotation.RequiresApi;

import com.tangzy.tzymvp.permission.EasyPermissions;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.SecureRandom;
import java.util.Enumeration;

/**
 * @author pianrong
 * @time 2015-3-26上午10:59:06
 * @describe 获取包信息的工具类
 */
public class PackageAndDeviceUtils {
    private PackageAndDeviceUtils() {
    }

    /**
     * @param ctx
     * @return
     * @describe 获得版本号versionName
     */
    public static String getVersionName(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                return pi.versionName;
            }
        } catch (NameNotFoundException e) {

        }
        return null;
    }

    /**
     * @param ctx
     * @return
     * @describe 获得清单文件的versionCode
     */
    public static int getVersionCode(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                return pi.versionCode;
            }
        } catch (NameNotFoundException e) {

        }
        return -1;
    }

    /**
     * @param ctx
     * @return
     * @describe 获得包名
     */
    public static String getPackageName(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                return pi.packageName;
            }
        } catch (NameNotFoundException e) {

        }
        return null;
    }

    /**
     * @param ctx
     * @return
     * @describe 获得渠道名称
     */
    public static String getChannel(Context ctx) {
        ApplicationInfo appInfo = new ApplicationInfo();
        try {
            appInfo = ctx.getPackageManager().getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        String _Channel = appInfo.metaData.get("UMENG_CHANNEL").toString();
        _Channel = TextUtils.isEmpty(_Channel) ? "default" : _Channel;
        return _Channel;
    }
    // 有兴趣的朋友可以看下NetworkInterface在Android FrameWork中怎么实现的
    private static String macAddress() throws SocketException {
        String address = null;
        // 把当前机器上的访问网络接口的存入 Enumeration集合中
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface netWork = interfaces.nextElement();
            // 如果存在硬件地址并可以使用给定的当前权限访问，则返回该硬件地址（通常是 MAC）。
            byte[] by = netWork.getHardwareAddress();
            if (by == null || by.length == 0) {
                continue;
            }
            StringBuilder builder = new StringBuilder();
            for (byte b : by) {
                builder.append(String.format("%02X:", b));
            }
            if (builder.length() > 0) {
                builder.deleteCharAt(builder.length() - 1);
            }
            String mac = builder.toString();
            // 从路由器上在线设备的MAC地址列表，可以印证设备Wifi的 name 是 wlan0
            if ("wlan0".equals(netWork.getName())) {
                address = mac;
            }
        }
        return address;
    }

    /**
     * @param context
     * @return
     * @describe 获取设备唯一标识
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String getDeviceUniqueId(Context context) {

        String deviceId = "";
        if (null != context) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            /**
             * 唯一编号（IMEI, MEID, ESN, IMSI） 缺点 Android设备要具有电话功能 其工作不是很可靠 序列号
             * 当其工作时，该值保留了设备的重置信息（“恢复出厂设置”），从而可以消除当客户删除自己设备上的信息，并把设备转另一个人时发生的错误。
             */
            if(tm!=null){
                try {
                    if (EasyPermissions.hasPermissions(context, Manifest.permission.READ_PHONE_STATE)) {
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            deviceId = tm.getImei();
                        }else {
                            deviceId = tm.getDeviceId();
                        }
                    }
                    if (!TextUtils.isEmpty(deviceId)) {
                        return deviceId;
                    }
                }catch (SecurityException e){
                    e.getLocalizedMessage();
                }
                try {
                    if (EasyPermissions.hasPermissions(context, Manifest.permission.READ_PHONE_STATE)) {
                        deviceId = tm.getSubscriberId();
                    }
                    if (!TextUtils.isEmpty(deviceId)) {
                        return deviceId;
                    }

                }catch (SecurityException e){

                }
            }

            try {
//                WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//                WifiInfo info = wifi.getConnectionInfo();
                deviceId = macAddress();
                if (!TextUtils.isEmpty(deviceId)) {
                    return deviceId;
                }
            } catch (SocketException e) {
                e.printStackTrace();
            }

        }
        // 序列号 缺点序列号无法在所有Android设备上使用
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class);
            deviceId = (String) (get.invoke(c, "ro.serialno", "unknown"));
            if (!TextUtils.isEmpty(deviceId)) {
                return deviceId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * ANDROID_ID 缺点 对于Android 2.2（“Froyo”）之前的设备不是100％的可靠
         * 此外，在主流制造商的畅销手机中至少存在一个众所周知的错误，每一个实例都具有相同的ANDROID_ID。
         */
        if (null != context) {
            deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            if (!TextUtils.isEmpty(deviceId) && !"9774d56d682e549c".equals(deviceId)) {
                return deviceId;
            }
        }

        if (TextUtils.isEmpty(deviceId)) {
//            deviceId = SPLoader.read().getString(SPConstant.DEVICE_ID, "");
            if (!TextUtils.isEmpty(deviceId)) {
                return deviceId;
            } else {
                final SecureRandom random = new SecureRandom();
                deviceId = new BigInteger(64, random).toString(16);
//                SPLoader.edit().put(SPConstant.DEVICE_ID, deviceId).save();
                return deviceId;
            }
        }
        return deviceId;

    }

    /**
     * 获取屏幕长(像素)
     *
     * @param context
     * @return
     */
    public static int getScreenWith(Context context) {
        return getDisplayMetrics(context).widthPixels;
    }

    /**
     * 获取屏幕高(像素)
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        return getDisplayMetrics(context).heightPixels;
    }

    /**
     * 获取DisplayMetrics对象
     *
     * @param context
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }
}
