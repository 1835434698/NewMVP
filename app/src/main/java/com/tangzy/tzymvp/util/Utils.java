package com.tangzy.tzymvp.util;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class Utils {

    public static void writeParcelableBean(Parcelable classThis, Parcel dest, int flags){
        try {
            //获取类的字节码文件对象
            Class aClass = classThis.getClass();
            //获取该类的所有成员变量
            Field[] f = aClass.getDeclaredFields();
            Logger.d("tangzy", "f.length = "+f.length);
            for(Field fie : f){
                fie.setAccessible(true);
                Logger.d("tangzy", fie.get(classThis)+" = "+fie.getType());
                switch (fie.getType().toString()){
                    case "char":
                        char c = (char) fie.get(classThis);
                        int di = c-'0';
                        Logger.d("tangzy", "c1 = "+di);
                        dest.writeInt(di);
                        break;
                    case "boolean":
                        dest.writeByte((byte) ((boolean)fie.get(classThis) ? 1 : 0));
                        break;
                    case "int":
                        dest.writeInt(Integer.parseInt(fie.get(classThis).toString()));
                        break;
                    case "long":
                        dest.writeLong(Long.parseLong(fie.get(classThis).toString()));
                        break;
                    case "class java.lang.String":
                        if (fie.get(classThis) == null){
                            dest.writeString("");
                        }else {
                            dest.writeString(fie.get(classThis).toString());
                        }
                        break;
                    case "class java.util.ArrayList":
                    case "class java.util.LinkedList":
                    case "interface java.util.List":
                        // 如果是List类型，得到其Generic的类型
                        Type genericType = fie.getGenericType();
                        if(genericType == null) continue;
                        // 如果是泛型参数的类型
                        if(genericType instanceof ParameterizedType){
                            ParameterizedType pt = (ParameterizedType) genericType;
                            //得到泛型里的class类型对象
                            Class<?> genericClazz = (Class<?>)pt.getActualTypeArguments()[0];
                            Logger.d("tangzy", "genericClazz= "+genericClazz);
                            dest.writeTypedList((List<Parcelable>)fie.get(classThis));
                        }
                        break;

                    default:
                        Logger.d("tangzy", "loader1= "+fie.getType().getClassLoader());
                        if (Parcelable.class.isAssignableFrom(fie.getType())){
                            if (fie.get(classThis) == null){
                                dest.writeParcelable(null, flags);
                            }else {
                                dest.writeParcelable((Parcelable) fie.get(classThis), flags);
                            }
                        }else {
                            Logger.d("tangzy", "false");
                        }

                        break;
                }

            }
        } catch (Exception e) {
            Logger.e("tangzy", "error "+e.getLocalizedMessage());
            Logger.e("tangzy", "error "+e.getMessage());
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
                    case "char":
                        char c = (char)( in.readInt()+'0');
                        Logger.d("tangzy", "c2 = "+c);
                        fie.set(classThis, c);
                        break;
                    case "boolean":
                        fie.set(classThis, in.readByte() != 0);
                        break;
                    case "int":
                        fie.set(classThis, in.readInt());
                        break;
                    case "class java.lang.String":
                        fie.set(classThis, in.readString());
                        break;
//                    case "class java.util.ArrayList":
//                    case "class java.util.LinkedList":
//                    case "interface java.util.List":
//                        // 如果是List类型，得到其Generic的类型
//                        Type genericType = fie.getGenericType();
//                        if(genericType == null) continue;
//                        // 如果是泛型参数的类型
//                        if(genericType instanceof ParameterizedType){
//                            ParameterizedType pt = (ParameterizedType) genericType;
//                            //得到泛型里的class类型对象
//                            Class<?> genericClazz = (Class<?>)pt.getActualTypeArguments()[0];
//                            Logger.d("tangzy", "genericClazz= "+genericClazz);
////                            genericClazz
//                            Logger.d("tangzy", "getOwnerType= "+pt.getOwnerType());
//                            Logger.d("tangzy", "getRawType= "+pt.getRawType());
//
//
//
//                            List<BankCard> parts = new ArrayList<>();
//
//                            in.readTypedList(parts, BankCard.CREATOR);
//                            fie.set(classThis, parts);
//                        }
//                        break;
                    default:
                        if (Parcelable.class.isAssignableFrom(fie.getType())){
                            fie.set(classThis, in.readParcelable(fie.getType().getClassLoader()));
                        }else {

                        }

                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classThis;
    }
}
