package com.tangzy.tzymvp.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by tangzy on 16/10/23.
 */
public class NetUtil {

    /**
     * 判断网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean checkNetType(Context context) {
        // 判断手机的链接渠道
        // WLAN（wi-fi）
        boolean isWIFI = isWIFIConnectivity(context);
        // 手机APN接入点
        boolean isMobile = isMobileConnectivity(context);
        // 当前无可利用的通信渠道
        if (!isWIFI && !isMobile) {
            return false;
        }

        return true;
    }

    /**
     * 手机APN接入点
     *
     * @param context
     * @return
     */

    private static boolean isMobileConnectivity(Context context) {
        // ConnectivityManager---systemService---Context
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (networkInfo != null) {
            return networkInfo.isConnected();
        }
        return false;
    }

    /**
     * 判断手机的链接渠道 WLAN（wi-fi）
     *
     * @param context
     * @return
     */
    private static boolean isWIFIConnectivity(Context context) {
        // ConnectivityManager---systemService---Context
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo != null) {
            return networkInfo.isConnected();
        }
        return false;
    }

    /**
     * 获取ip地址
     *
     * @return
     * @throws Exception
     */
    public static String getIP(Context context) throws Exception {
        if (isWIFIConnectivity(context)) {
            return getWIFIIp(context);
        } else {
            return getLocalIpAddress();
        }

    }

    /**
     * 使用手机网络时，获取ip
     *
     * @return
     * @throws Exception
     */
    private static String getLocalIpAddress() throws Exception {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            throw new Exception(ex.getMessage());
        }
        return null;
    }

    /**
     * 使用wifi时，获取ip
     *
     * @return
     * @throws Exception
     */
    private static String getWIFIIp(Context context) throws Exception {
        // 获取wifi服务
        WifiManager wifiManager = (WifiManager)context
                .getSystemService(Context.WIFI_SERVICE);
        // 判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
//		wifiInfo.getSSID();
        String ip = intToIp(ipAddress);
        return ip;
    }

    /**
     * 转换成ip
     *
     * @param i
     * @return
     */
    private static String intToIp(int i) {

        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
                + "." + (i >> 24 & 0xFF);
    }
}
