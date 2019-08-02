package com.tangzy.tzymvp.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMetadataRetriever;
import android.media.MediaMuxer;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

//import javax.sound.sampled.AudioFileFormat;
//import javax.sound.sampled.AudioFormat;
//import javax.sound.sampled.AudioInputStream;
//import javax.sound.sampled.AudioSystem;
//import javax.sound.sampled.UnsupportedAudioFileException;


public class Utils {
    private static final String TAG = "Utils";

    public static void writeParcelableBean(Parcelable classThis, Parcel dest, int flags) {
        try {
            //获取类的字节码文件对象
            Class aClass = classThis.getClass();
            //获取该类的所有成员变量
            Field[] f = aClass.getDeclaredFields();
            for (Field fie : f) {
                fie.setAccessible(true);
                Logger.d("tangzy", fie.get(classThis) + " = " + fie.getType());
                switch (fie.getType().toString()) {
                    case "char":
                        char c = (char) fie.get(classThis);
                        int di = c - '0';
                        Logger.d("tangzy", "c1 = " + di);
                        dest.writeInt(di);
                        break;
                    case "boolean":
                        dest.writeByte((byte) ((boolean) fie.get(classThis) ? 1 : 0));
                        break;
                    case "class java.lang.Boolean":
                        Object boole = fie.get(classThis);
                        dest.writeByte((byte) (boole == null ? 0 : (boolean) boole ? 1 : 2));
                        break;
                    case "int":
                        dest.writeInt(Integer.parseInt(fie.get(classThis).toString()));
                        break;
                    case "class java.lang.Integer":
                        if (fie.get(classThis) == null) {
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
                        if (fie.get(classThis) == null) {
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
                        if (fie.get(classThis) == null) {
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
                        if (fie.get(classThis) == null) {
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
                        Object shortV = fie.get(classThis);
                        dest.writeInt(shortV != null ? Integer.parseInt(shortV.toString()) : Integer.MAX_VALUE);
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
                        if (fie.get(classThis) == null) {
                            dest.writeString("");
                        } else {
                            dest.writeString(fie.get(classThis).toString());
                        }
                        break;
                    case "class java.util.ArrayList":
                    case "class java.util.LinkedList":
                    case "interface java.util.List":
                        // 如果是List类型，得到其Generic的类型
                        Type genericType = fie.getGenericType();
                        if (genericType == null) {
                            continue;
                        }
                        // 如果是泛型参数的类型
                        if (genericType instanceof ParameterizedType) {
                            ParameterizedType pt = (ParameterizedType) genericType;
                            //得到泛型里的class类型对象
                            Class<?> genericClazz = (Class<?>) pt.getActualTypeArguments()[0];
                            switch (genericClazz.toString()) {
                                case "class java.lang.String":
                                    dest.writeStringList((List<String>) fie.get(classThis));
                                    break;
                                default:
                                    dest.writeTypedList((List<Parcelable>) fie.get(classThis));
                                    break;

                            }
                        }
                        break;

                    default:
                        if (Parcelable.class.isAssignableFrom(fie.getType())) {
                            if (fie.get(classThis) == null) {
                                dest.writeParcelable(null, flags);
                            } else {
                                dest.writeParcelable((Parcelable) fie.get(classThis), flags);
                            }
                        }

                        break;
                }

            }
        } catch (Exception e) {
            Logger.e("tangzy", "error " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void readParcelableBean(Parcelable classThis, Parcel in) {
        Logger.d("tangzy", "readParcelableBean");
        try {
            Class aClass = classThis.getClass();
            //获取该类的所有成员变量
            Field[] f = aClass.getDeclaredFields();
            for (Field fie : f) {
                fie.setAccessible(true);
                switch (fie.getType().toString()) {
                    case "char":
                        char c = (char) (in.readInt() + '0');
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
                        if (in.readByte() == 0) {
                            fie.set(classThis, null);
                        } else {
                            fie.set(classThis, in.readInt());
                        }
                        break;
                    case "long":
                        fie.set(classThis, in.readLong());
                        break;
                    case "class java.lang.Long":
                        if (in.readByte() == 0) {
                            fie.set(classThis, null);
                        } else {
                            fie.set(classThis, in.readLong());
                        }
                        break;
                    case "double":
                        fie.set(classThis, in.readDouble());
                        break;
                    case "class java.lang.Double":
                        if (in.readByte() == 0) {
                            fie.set(classThis, null);
                        } else {
                            fie.set(classThis, in.readDouble());
                        }
                        break;
                    case "float":
                        fie.set(classThis, in.readFloat());
                        break;
                    case "class java.lang.Float":
                        if (in.readByte() == 0) {
                            fie.set(classThis, null);
                        } else {
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
                        if (genericType == null) {
                            continue;
                        }
                        // 如果是泛型参数的类型
                        if (genericType instanceof ParameterizedType) {
                            ParameterizedType pt = (ParameterizedType) genericType;
                            //得到泛型里的class类型对象
                            Class<?> genericClazz = (Class<?>) pt.getActualTypeArguments()[0];
                            switch (genericClazz.toString()) {
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
                        if (Parcelable.class.isAssignableFrom(fie.getType())) {
                            fie.set(classThis, in.readParcelable(fie.getType().getClassLoader()));
                        }

                        break;
                }
            }
        } catch (Exception e) {
            Logger.e("tangzy", "error " + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * 分离音频
     *
     * @param input  传入视频文件
     * @param output 输出音频文件
     * @return
     */
    public static boolean muxerAudio(String input, String output) {
        MediaExtractor mediaExtractor = new MediaExtractor();
        int audioIndex = -1;
        try {
            mediaExtractor.setDataSource(input);
            int trackCount = mediaExtractor.getTrackCount();
            for (int i = 0; i < trackCount; i++) {
                MediaFormat trackFormat = mediaExtractor.getTrackFormat(i);
                if (trackFormat.getString(MediaFormat.KEY_MIME).startsWith("audio/")) {
                    audioIndex = i;
                }
            }
            mediaExtractor.selectTrack(audioIndex);
            MediaFormat trackFormat = mediaExtractor.getTrackFormat(audioIndex);
            MediaMuxer mediaMuxer = new MediaMuxer(output, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
            int writeAudioIndex = mediaMuxer.addTrack(trackFormat);
            mediaMuxer.start();
            ByteBuffer byteBuffer = ByteBuffer.allocate(500 * 1024);
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            long stampTime = 0;
            //获取帧之间的间隔时间
            {
                mediaExtractor.readSampleData(byteBuffer, 0);
                if (mediaExtractor.getSampleFlags() == MediaExtractor.SAMPLE_FLAG_SYNC) {
                    mediaExtractor.advance();
                }
                mediaExtractor.readSampleData(byteBuffer, 0);
                long secondTime = mediaExtractor.getSampleTime();
                mediaExtractor.advance();
                mediaExtractor.readSampleData(byteBuffer, 0);
                long thirdTime = mediaExtractor.getSampleTime();
                stampTime = Math.abs(thirdTime - secondTime);
                Log.e("fuck", stampTime + "");
            }
            mediaExtractor.unselectTrack(audioIndex);
            mediaExtractor.selectTrack(audioIndex);
            while (true) {
                int readSampleSize = mediaExtractor.readSampleData(byteBuffer, 0);
                if (readSampleSize < 0) {
                    break;
                }
                mediaExtractor.advance();
                bufferInfo.size = readSampleSize;
                bufferInfo.flags = mediaExtractor.getSampleFlags();
                bufferInfo.offset = 0;
                bufferInfo.presentationTimeUs += stampTime;
                mediaMuxer.writeSampleData(writeAudioIndex, byteBuffer, bufferInfo);
            }
            mediaMuxer.stop();
            mediaMuxer.release();
            mediaExtractor.release();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 分离图片
     *
     * @param input  传入视频文件
     * @param output 输出音频文件
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    public static boolean muxerImg(String input, String output) {
        //创建MediaMetadataRetriever对象
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        //绑定资源
        mmr.setDataSource(input);
        //获取第一帧图像的bitmap对象
//        Bitmap bitmap = mmr.getFrameAtTime();
//        saveBitmap(output+"1.png", bitmap);
        String s = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        int time = 0;
        if (!TextUtils.isEmpty(s)) {
            time = Integer.parseInt(s);
        }
//        String s1 = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_FRAME_COUNT);
//                    mmr.getFrameAtIndex(1);
//        bitmap = mmr.getFrameAtIndex(2);
        List<Bitmap> framesAtIndex = mmr.getFramesAtIndex(4, 4);
        for (int i=0;i<4;i++){

            saveBitmap(output+i+"0.png", framesAtIndex.get(i));
        }
//        saveBitmap(output+"2.png", bitmap);
//        bitmap = mmr.getFrameAtIndex(3);
//        saveBitmap(output+"3.png", bitmap);
        return true;
    }

    public static void saveBitmap(String filePath, Bitmap mBitmap) {
        makesureFileExist(filePath);
        try {
            FileOutputStream outputStream = new FileOutputStream(filePath);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 确定指定文件是否存在，如果不存在，则创建空文件
     *
     * @param fileName
     */
    public static void makesureFileExist(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return;
        }
        // file path
        int index = fileName.lastIndexOf("/");
        File file = null;
        if (index != -1) {
            String filePath = fileName.substring(0, index);
            file = new File(filePath);
            if (!file.exists()) {
                boolean ret = file.mkdirs();
            }
        }
        file = new File(fileName);
        if (!file.exists()) {// 确保文件存在
            try {
                file.createNewFile();
            } catch (Exception e) {
            }
        }
    }

    /**
     * 读取asset目录下文件。
     *
     * @return content
     */
    public static String readFile(Context mContext, String file, String code) {
        int len = 0;
        byte[] buf = null;
        String result = "";
        try {
            InputStream in = mContext.getAssets().open(file);
            len = in.available();
            buf = new byte[len];
            in.read(buf, 0, len);

            result = new String(buf, code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 读取asset目录下音频文件。
     *
     * @return 二进制文件数据
     */
    public static byte[] readAudioFile(Context context, String filename) {
        try {
            InputStream ins = context.getAssets().open(filename);
            byte[] data = new byte[ins.available()];

            ins.read(data);
            ins.close();

            return data;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }


}
