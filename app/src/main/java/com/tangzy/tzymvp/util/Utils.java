package com.tangzy.tzymvp.util;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static void writeParcelableBean(Parcelable classThis, Parcel dest, int flags){
        try {
            //获取类的字节码文件对象
            Class aClass = classThis.getClass();
            //获取该类的所有成员变量
            Field[] f = aClass.getDeclaredFields();
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
                    case "class java.lang.Boolean":
                        Object boole = fie.get(classThis);
                        dest.writeByte((byte) (boole == null ? 0 : (boolean)boole ? 1 : 2));
                        break;
                    case "int":
                        dest.writeInt(Integer.parseInt(fie.get(classThis).toString()));
                        break;
                    case "class java.lang.Integer":
                        if (fie.get(classThis) == null){
                            dest.writeByte((byte) 0);
                        } else {
                            dest.writeByte((byte) 1);
                            dest.writeInt(Integer.parseInt(fie.get(classThis).toString()));
                        }
                        break;
                    case "long":
                        dest.writeLong(Long.parseLong(fie.get(classThis).toString()));
                        break;
                    case "class java.lang.Long":
                        if (fie.get(classThis) == null){
                            dest.writeByte((byte) 0);
                        } else {
                            dest.writeByte((byte) 1);
                            dest.writeLong(Long.parseLong(fie.get(classThis).toString()));
                        }
                        break;
                    case "double":
                        dest.writeDouble(Double.parseDouble(fie.get(classThis).toString()));
                        break;
                    case "class java.lang.Double":
                        if (fie.get(classThis) == null){
                            dest.writeByte((byte) 0);
                        } else {
                            dest.writeByte((byte) 1);
                            dest.writeDouble(Double.parseDouble(fie.get(classThis).toString()));
                        }
                        break;
                    case "float":
                        dest.writeFloat(Float.parseFloat(fie.get(classThis).toString()));
                        break;
                    case "class java.lang.Float":
                        if (fie.get(classThis) == null){
                            dest.writeByte((byte) 0);
                        } else {
                            dest.writeByte((byte) 1);
                            dest.writeFloat(Float.parseFloat(fie.get(classThis).toString()));
                        }
                        break;
                    case "short":
                        dest.writeInt(Integer.parseInt(fie.get(classThis).toString()));
                        break;
                    case "class java.lang.Short":
                        Object short_V = fie.get(classThis);
                        dest.writeInt(short_V != null ? Integer.parseInt(short_V.toString()) : Integer.MAX_VALUE);
                        break;
                    case "byte":
                        dest.writeByte(Byte.parseByte(fie.get(classThis).toString()));
                        break;
                    case "class java.lang.Byte":
                        if (fie.get(classThis) == null) {
                            dest.writeByte((byte) 0);
                        } else {
                            dest.writeByte((byte) 1);
                            dest.writeByte(Byte.parseByte(fie.get(classThis).toString()));
                        }
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
                            switch (genericClazz.toString()){
                                case "class java.lang.String":
                                    dest.writeStringList((List<String>) fie.get(classThis));
                                    break;
                                default:
                                    dest.writeTypedList((List<Parcelable>)fie.get(classThis));
                                    break;

                            }
                        }
                        break;

                    default:
                        if (Parcelable.class.isAssignableFrom(fie.getType())){
                            if (fie.get(classThis) == null){
                                dest.writeParcelable(null, flags);
                            }else {
                                dest.writeParcelable((Parcelable) fie.get(classThis), flags);
                            }
                        }

                        break;
                }

            }
        } catch (Exception e) {
            Logger.e("tangzy", "error "+e.getMessage());
            e.printStackTrace();
        }
    }

    public static void readParcelableBean(Parcelable classThis, Parcel in){
        Logger.d("tangzy", "readParcelableBean");
        try {
            Class aClass = classThis.getClass();
            //获取该类的所有成员变量
            Field[] f = aClass.getDeclaredFields();
            for(Field fie : f){
                fie.setAccessible(true);
                switch (fie.getType().toString()){
                    case "char":
                        char c = (char)( in.readInt()+'0');
                        fie.set(classThis, c);
                        break;
                    case "boolean":
                        fie.set(classThis, in.readByte() != 0);
                        break;
                    case "class java.lang.Boolean":
                        byte tmp = in.readByte();
                        fie.set(classThis, tmp == 0 ? null : tmp == 1);
                        break;
                    case "int":
                        fie.set(classThis, in.readInt());
                        break;
                    case "class java.lang.Integer":
                        if (in.readByte() == 0){
                            fie.set(classThis, null);
                        }else {
                            fie.set(classThis, in.readInt());
                        }
                        break;
                    case "long":
                        fie.set(classThis, in.readLong());
                        break;
                    case "class java.lang.Long":
                        if (in.readByte() == 0) {
                            fie.set(classThis, null);
                        }else {
                            fie.set(classThis, in.readLong());
                        }
                        break;
                    case "double":
                        fie.set(classThis, in.readDouble());
                        break;
                    case "class java.lang.Double":
                        if (in.readByte() == 0) {
                            fie.set(classThis, null);
                        }else {
                            fie.set(classThis, in.readDouble());
                        }
                        break;
                    case "float":
                        fie.set(classThis, in.readFloat());
                        break;
                    case "class java.lang.Float":
                        if (in.readByte() == 0) {
                            fie.set(classThis, null);
                        }else {
                            fie.set(classThis, in.readFloat());
                        }
                        break;
                    case "short":
                        fie.set(classThis, (short) in.readInt());
                        break;
                    case "class java.lang.Short":
                        int tmpShort = in.readInt();
                        fie.set(classThis, tmpShort != Integer.MAX_VALUE ? (short) tmpShort : null);
                        break;
                    case "byte":
                        fie.set(classThis, in.readByte());
                        break;
                    case "class java.lang.Byte":
                        if (in.readByte() == 0) {
                            fie.set(classThis, null);
                        } else {
                            fie.set(classThis, in.readByte());
                        }
                        break;
                    case "class java.lang.String":
                        fie.set(classThis, in.readString());
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
                            switch (genericClazz.toString()){
                                case "class java.lang.String":
                                    fie.set(classThis, in.createStringArrayList());
                                    break;
                                default:
                                    Class<?> aClass1 = Class.forName(genericClazz.toString().substring(6));
//                                     获取匿名内部类实例
                                    Object o = aClass1.newInstance();
                                    Parcelable.Creator<?> creator = (Parcelable.Creator<?>) aClass1.getField("CREATOR").get(o);
                                    ArrayList<?> typedArrayList = in.createTypedArrayList(creator);
                                    fie.set(classThis, typedArrayList);
                                    break;
                            }
                        }
                        break;
                    default:
                        if (Parcelable.class.isAssignableFrom(fie.getType())){
                            fie.set(classThis, in.readParcelable(fie.getType().getClassLoader()));
                        }

                        break;
                }
            }
        } catch (Exception e) {
            Logger.e("tangzy", "error "+e.getMessage());
            e.printStackTrace();
        }
    }
}
