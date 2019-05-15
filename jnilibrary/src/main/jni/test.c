//
// Created by Administrator on 2019/5/14.
//
#include <jni.h>
#include<stdio.h>

#include <com_tangzy_jnilibrary_jni_JNITest.h>

/**
 * C
 * @param env
 * @param jclass
 * @return
 */
JNIEXPORT jstring JNICALL Java_com_tangzy_jnilibrary_jni_JNITest_getJNIName
  (JNIEnv *env, jclass jclass){
    //返回一个字符串
    return (*env)->NewStringUTF(env,"This is my first NDK Application");
  }

///**
// * C++
// */
//extern "C" jstring JNICALL Java_com_tangzy_jnilibrary_jni_JNITest_getJNIName
//        (JNIEnv *env, jclass jclass){
//    //返回一个字符串
//    return env -> NewStringUTF("This is my first NDK Application");//C++
//}

JNIEXPORT jint JNICALL Java_com_tangzy_jnilibrary_jni_JNITest_getAddResult
        (JNIEnv *env, jclass jclass, jint a, jint b){
    jint c = a + b;
    return c;
}

JNIEXPORT jint JNICALL
Java_com_tangzy_jnilibrary_jni_JNITest_getAddResult__III(JNIEnv *env, jclass type, jint a, jint b,
                                                         jint c) {
    return a+b+c;
}
