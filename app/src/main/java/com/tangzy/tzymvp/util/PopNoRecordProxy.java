package com.tangzy.tzymvp.util;


import android.app.NotificationManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.ViewManager;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class PopNoRecordProxy implements InvocationHandler {
    private static final String TAG = "PopNoRecordProxy";


//    private Method getMethod(String name, Class<?>[] parameterTypes, boolean recursivePublicMethods)
//            throws NoSuchMethodException {
//        if (name == null) {
//            throw new NullPointerException("name == null");
//        }
//        if (parameterTypes == null) {
//            parameterTypes = EmptyArray.CLASS;
//        }
//        for (Class<?> c : parameterTypes) {
//            if (c == null) {
//                throw new NoSuchMethodException("parameter type is null");
//            }
//        }
//        Method result = recursivePublicMethods ? getPublicMethodRecursive(name, parameterTypes)
//                : getDeclaredMethodInternal(name, parameterTypes);
//        // Fail if we didn't find the method or it was expected to be public.
//        if (result == null ||
//                (recursivePublicMethods && !Modifier.isPublic(result.getAccessFlags()))) {
//            throw new NoSuchMethodException(name + " " + Arrays.toString(parameterTypes));
//        }
//        return result;
//    }

//    static  Method getRotation;
//    static Object IWindowManager;
//    public static void getRotation(){
//        try {
//            //加载得到ServiceManager类，然后得到方法getService。
//            Method getServiceMethod = Class.forName("android.os.ServiceManager").getDeclaredMethod("getService", new Class[]{String.class});
//            //通过getServiceMethod得到ServiceManager的实例（隐藏类，所以使用Object）
//            Object ServiceManager = getServiceMethod.invoke(null, new Object[]{"window"});
//            //通过反射的到Stub
//            Class<?> cStub =  Class.forName("android.view.IWindowManager$Stub");
//            //得到Stub类的asInterface 方法
//            Method asInterface = cStub.getMethod("asInterface", IBinder.class);
//            //然后通过类似serviceManager.getIWindowManager的方法获取IWindowManager的实例
//            IWindowManager = asInterface.invoke(null, ServiceManager);
//            //通过IWindowManager的实例得到方法getRotation
//            getRotation = IWindowManager.getClass().getMethod("getRotation");
//
//        } catch (Exception e) {
//            Log.e("wmb", "--shutDown has an exception");
//            e.printStackTrace();
//        }
//    }

    private Object mWindowManager;//PopupWindow类的mWindowManager对象

    public static PopNoRecordProxy instance() {
        return new PopNoRecordProxy();
    }

    public void noScreenRecord(PopupWindow popupWindow) {
        Logger.d(TAG, "noScreenRecord");;
        if (popupWindow == null) {
            return;
        }
        try {
            //通过反射获得PopupWindow类的私有对象：mWindowManager
            Field windowManagerField = PopupWindow.class.getDeclaredField("mWindowManager");
            windowManagerField.setAccessible(true);
            mWindowManager = windowManagerField.get(popupWindow);
            if(mWindowManager == null){
                return;
            }
            //创建WindowManager的动态代理对象proxy
            Object proxy = Proxy.newProxyInstance(Handler.class.getClassLoader(), new Class[]{WindowManager.class}, this);

            //注入动态代理对象proxy（即：mWindowManager对象由proxy对象来代理）
            windowManagerField.set(popupWindow, proxy);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Logger.d(TAG, "invoke");;
        try {
            //拦截方法mWindowManager.addView(View view, ViewGroup.LayoutParams params);
            if (method != null && method.getName() != null && method.getName().equals("addView")
                    && args != null && args.length == 2) {
                Logger.d(TAG, "invoke - addView");;
                //获取WindowManager.LayoutParams，即：ViewGroup.LayoutParams
                WindowManager.LayoutParams params = (WindowManager.LayoutParams) args[1];
                //禁止录屏
                setNoScreenRecord(params);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return method.invoke(mWindowManager, args);
    }

    /**
     * 禁止录屏
     */
    private void setNoScreenRecord(WindowManager.LayoutParams params) {
        setFlags(params, WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
    }

    /**
     * 允许录屏
     */
    private void setAllowScreenRecord(WindowManager.LayoutParams params) {
        setFlags(params, 0, WindowManager.LayoutParams.FLAG_SECURE);
    }

    /**
     * 设置WindowManager.LayoutParams flag属性（参考系统类Window.setFlags(int flags, int mask)）
     *
     * @param params WindowManager.LayoutParams
     * @param flags  The new window flags (see WindowManager.LayoutParams).
     * @param mask   Which of the window flag bits to modify.
     */
    private void setFlags(WindowManager.LayoutParams params, int flags, int mask) {
        try {
            if (params == null) {
                return;
            }
            params.flags = (params.flags & ~mask) | (flags & mask);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static Object sOriginService;
    public static void hookNotificationManager(final Context context) throws Exception {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

//        Method getService = NotificationManager.class.getDeclaredMethod("getService");
//
//        getService.setAccessible(true);

        Field sServiceField = NotificationManager.class.getDeclaredField("sService");
        sServiceField.setAccessible(true);

        // 第一步：得到系统的 sService
        final Object sOriginService = sServiceField.get(notificationManager);
//        sOriginService = getService.invoke(notificationManager);

        Class iNotiMngClz = Class.forName("android.app.INotificationManager");
        // 第二步：因为 sService 是接口，得到我们的动态代理对象
        Object proxyNotiMng = Proxy.newProxyInstance(context.getClass().getClassLoader(), new
                Class[]{iNotiMngClz}, myInvocationHandler);
//        Object proxyNotiMng = Proxy.newProxyInstance(context.getClass().getClassLoader(), new
//                Class[]{iNotiMngClz}, new InvocationHandler() {
//
//            @Override
//            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                Log.d(TAG, "invoke(). method:" + method);
//                String name = method.getName();
//                Log.d(TAG, "invoke: name=" + name);
//                if (args != null && args.length > 0) {
//                    for (Object arg : args) {
//                        Log.d(TAG, "invoke: arg=" + arg);
//                    }
//                }
//                Toast.makeText(context.getApplicationContext(), "检测到有人发通知了", Toast.LENGTH_SHORT).show();
//                // 操作交由 sOriginService 处理，不拦截通知
//                return method.invoke(sOriginService, args);
//                // 拦截通知，什么也不做
////                                    return null;
//            }
//        });
        // 第三步：偷梁换柱，使用 proxyNotiMng 替换系统的 sService
//        Field sServiceField = NotificationManager.class.getDeclaredField("sService");
            sServiceField.setAccessible(true);
            sServiceField.set(notificationManager, proxyNotiMng);

    }
    public static MyInvocationHandler myInvocationHandler = new MyInvocationHandler();

    static class MyInvocationHandler implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Log.d(TAG, "invoke(). method:" + method);
            String name = method.getName();
            Log.d(TAG, "invoke: name=" + name);
            if (args != null && args.length > 0) {
                for (Object arg : args) {
                    Log.d(TAG, "invoke: arg=" + arg);
                }
            }
            // 操作交由 sOriginService 处理，不拦截通知
            return method.invoke(sOriginService, args);
//            return null;
        }
    }

    public static void hookNotificationManager22(final Context context) throws Exception {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Method getService = NotificationManager.class.getDeclaredMethod("getService");
        getService.setAccessible(true);
        // 第一步：得到系统的 sService
        final Object sOriginService = getService.invoke(notificationManager);

        Class iNotiMngClz = Class.forName("android.app.INotificationManager");
        // 第二步：因为 sService 是接口，得到我们的动态代理对象
        Object proxyNotiMng = Proxy.newProxyInstance(context.getClass().getClassLoader(), new
                Class[]{iNotiMngClz}, new InvocationHandler() {

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Log.d(TAG, "invoke(). method:" + method);
                String name = method.getName();
                Log.d(TAG, "invoke: name=" + name);
                if (args != null && args.length > 0) {
                    for (Object arg : args) {
                        Log.d(TAG, "invoke: arg=" + arg);
                    }
                }
                Toast.makeText(context.getApplicationContext(), "检测到有人发通知了", Toast.LENGTH_SHORT).show();
                // 操作交由 sOriginService 处理，不拦截通知
                return method.invoke(sOriginService, args);
                // 拦截通知，什么也不做
//                                    return null;
            }
        });
        // 第三步：偷梁换柱，使用 proxyNotiMng 替换系统的 sService
        Field sServiceField = NotificationManager.class.getDeclaredField("sService");
        sServiceField.setAccessible(true);
        sServiceField.set(notificationManager, proxyNotiMng);

    }
    public static void hookClipboardService(final Context context) throws Exception {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        Field mServiceFiled = ClipboardManager.class.getDeclaredField("mService");
        mServiceFiled.setAccessible(true);
        // 第一步：得到系统的 mService
        final Object mService = mServiceFiled.get(clipboardManager);

        // 第二步：初始化动态代理对象
        Class aClass = Class.forName("android.content.IClipboard");
        Object proxyInstance = Proxy.newProxyInstance(context.getClass().getClassLoader(), new
                Class[]{aClass}, new InvocationHandler() {

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Log.d(TAG, "invoke(). method:" + method);
                String name = method.getName();
                if (args != null && args.length > 0) {
                    for (Object arg : args) {
                        Log.d(TAG, "invoke: arg=" + arg);
                    }
                }
                if ("setPrimaryClip".equals(name)) {
                    Object arg = args[0];
                    if (arg instanceof ClipData) {
                        ClipData clipData = (ClipData) arg;
                        int itemCount = clipData.getItemCount();
                        for (int i = 0; i < itemCount; i++) {
                            ClipData.Item item = clipData.getItemAt(i);
                            Log.i(TAG, "invoke: item=" + item);
                        }
                    }
                    Toast.makeText(context, "检测到有人设置粘贴板内容", Toast.LENGTH_SHORT).show();
                } else if ("getPrimaryClip".equals(name)) {
                    Toast.makeText(context, "检测到有人要获取粘贴板的内容", Toast.LENGTH_SHORT).show();
                }
                // 操作交由 sOriginService 处理，不拦截通知
                return method.invoke(mService, args);

            }
        });

        // 第三步：偷梁换柱，使用 proxyNotiMng 替换系统的 mService
        Field sServiceField = ClipboardManager.class.getDeclaredField("mService");
        sServiceField.setAccessible(true);
        sServiceField.set(clipboardManager, proxyInstance);

    }
    static Object mWindowManager1;
    public static void hookManagerService(final Context context) throws Exception {

//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        Method getService = NotificationManager.class.getDeclaredMethod("getService");
//        getService.setAccessible(true);
//        // 第一步：得到系统的 sService
//        final Object sOriginService = getService.invoke(notificationManager);

        //通过反射获得PopupWindow类的私有对象：mWindowManager
//        Class WindowManagerImpl = Class.forName("android.view.WindowManagerImpl");
//        Field windowManagerField = WindowManagerImpl.class.getDeclaredField("mGlobal");
//        windowManagerField.setAccessible(true);
//        mWindowManager = windowManagerField.get(popupWindow);

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        Class<?> WindowManagerImpl = Class.forName("android.view.WindowManagerImpl");
//        WindowManagerImpl.getMethod("getService");//这是获取方法的
        Field field = WindowManagerImpl.getDeclaredField("mGlobal");//这是获取成员变量的
        field.setAccessible(true);
        mWindowManager1 =  field.get(windowManager);

        Class iNotiMngClz = Class.forName("android.view.ViewManager");
        // 第二步：因为 sService 是接口，得到我们的动态代理对象
        Object proxyInstance = Proxy.newProxyInstance(context.getClass().getClassLoader(), new Class[]{iNotiMngClz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Log.d(TAG, "invoke(). method:" + method);
                String name = method.getName();
                Log.d(TAG, "invoke: name=" + name);
                // 操作交由 sOriginService 处理，不拦截通知
                return method.invoke(mWindowManager1, args);
//                return null;
            }
        });
//        // 第三步：偷梁换柱，使用 proxyNotiMng 替换系统的 mService
////        Field sServiceField = ClipboardManager.class.getDeclaredField("mService");
//        Field sServiceField = windowManagerGlobal.getDeclaredField("sWindowManagerService");
//        sServiceField.setAccessible(true);
        field.set(mWindowManager1, proxyInstance);

    }





}
