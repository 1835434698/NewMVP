package com.tangzy.tzymvp.util;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Field;

public class Utils {

    public static void writeParcelableBean(Parcelable classThis, Parcel dest, int flags){
        try {
            //获取类的字节码文件对象
            Class aClass = classThis.getClass();
            //获取该类的所有成员变量
            Field[] f = aClass.getDeclaredFields();
            for(Field fie : f){
                fie.setAccessible(true);
                switch (fie.getType().toString()){
                    case "int":
                        dest.writeInt(Integer.parseInt(fie.get(classThis).toString()));
                        break;
                    case "class java.lang.String":
                        dest.writeString(fie.get(classThis).toString());
                        break;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Parcelable readParcelableBean(Parcelable classThis, Parcel in){
        try {
            Class aClass = classThis.getClass();
            //获取该类的所有成员变量
            Field[] f = aClass.getDeclaredFields();
            for(Field fie : f){
                fie.setAccessible(true);
                switch (fie.getType().toString()){
                    case "int":
                        fie.set(classThis, in.readInt());
                        break;
                    case "class java.lang.String":
                        fie.set(classThis, in.readString());
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classThis;
    }

}
