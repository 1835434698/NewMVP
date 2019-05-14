package com.tangzy.jnilibrary.jni;

public class JNITest {
    static {
        System.loadLibrary("JNITest");
    }
    public native static String getJNIName();
}
