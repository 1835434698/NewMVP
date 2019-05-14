//
// Created by Administrator on 2019/5/14.
//
#include <jni.h>
#include<stdio.h>

#include <com_tangzy_jnilibrary_jni_JNITest.h>

JNIEXPORT jstring JNICALL Java_com_tangzy_jnilibrary_jni_JNITest_getJNIName
  (JNIEnv *env, jclass jclass){
    //返回一个字符串
    return (*env)->NewStringUTF(env,"This is my first NDK Application");
  }
