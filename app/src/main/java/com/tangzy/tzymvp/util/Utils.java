package com.tangzy.tzymvp.util;

import android.graphics.Bitmap;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMetadataRetriever;
import android.media.MediaMuxer;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

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
    public static boolean muxerImg(String input, String output) {
        //创建MediaMetadataRetriever对象
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        //绑定资源
        mmr.setDataSource(input);
        //获取第一帧图像的bitmap对象
        Bitmap bitmap = mmr.getFrameAtTime();
        String s = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        int time=0;
        if (!TextUtils.isEmpty(s)){
            time = Integer.parseInt(s);
        }
//        String s1 = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_FRAME_COUNT);
//                    mmr.getFrameAtIndex(1);
        Bitmap bitmap1 = mmr.getFrameAtTime(time/2);
        saveBitmap(output, bitmap1);
        return true;
    }
    public static void saveBitmap(String filePath, Bitmap mBitmap){
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
     * 合成视频1的音频和视频2的图像
     *
     * @param audioVideoPath       提供音频的视频
     * @param audioStartTime       音频的开始时间
     * @param frameVideoPath       提供图像的视频
     * @param combinedVideoOutFile 合成后的文件
     */
    public static void combineTwoVideos(String audioVideoPath, long audioStartTime, String frameVideoPath, File combinedVideoOutFile) {
        MediaExtractor audioVideoExtractor = new MediaExtractor();
        int mainAudioExtractorTrackIndex = -1; //提供音频的视频的音频轨（有点拗口）
        int mainAudioMuxerTrackIndex = -1; //合成后的视频的音频轨
        int mainAudioMaxInputSize = 0; //能获取的音频的最大值

        MediaExtractor frameVideoExtractor = new MediaExtractor();
        int frameExtractorTrackIndex = -1; //视频轨
        int frameMuxerTrackIndex = -1; //合成后的视频的视频轨
        int frameMaxInputSize = 0; //能获取的视频的最大值
        int frameRate = 0; //视频的帧率
        long frameDuration = 0;

        MediaMuxer muxer = null; //用于合成音频与视频

        try {
            muxer = new MediaMuxer(combinedVideoOutFile.getPath(), MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);

            audioVideoExtractor.setDataSource(audioVideoPath); //设置视频源
            //音轨信息
            int audioTrackCount = audioVideoExtractor.getTrackCount(); //获取数据源的轨道数
            //在此循环轨道数，目的是找到我们想要的音频轨
            for (int i = 0; i < audioTrackCount; i++) {
                MediaFormat format = audioVideoExtractor.getTrackFormat(i); //得到指定索引的记录格式
                String mimeType = format.getString(MediaFormat.KEY_MIME); //主要描述mime类型的媒体格式
                if (mimeType.startsWith("audio/")) { //找到音轨
                    mainAudioExtractorTrackIndex = i;
                    mainAudioMuxerTrackIndex = muxer.addTrack(format); //将音轨添加到MediaMuxer，并返回新的轨道
                    mainAudioMaxInputSize = format.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE); //得到能获取的有关音频的最大值
//                    mainAudioDuration = format.getLong(MediaFormat.KEY_DURATION);
                }
            }

            //图像信息
            frameVideoExtractor.setDataSource(frameVideoPath); //设置视频源
            int trackCount = frameVideoExtractor.getTrackCount(); //获取数据源的轨道数
            //在此循环轨道数，目的是找到我们想要的视频轨
            for (int i = 0; i < trackCount; i++) {
                MediaFormat format = frameVideoExtractor.getTrackFormat(i); //得到指定索引的媒体格式
                String mimeType = format.getString(MediaFormat.KEY_MIME); //主要描述mime类型的媒体格式
                if (mimeType.startsWith("video/")) { //找到视频轨
                    frameExtractorTrackIndex = i;
                    frameMuxerTrackIndex = muxer.addTrack(format); //将视频轨添加到MediaMuxer，并返回新的轨道
                    frameMaxInputSize = format.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE); //得到能获取的有关视频的最大值
                    frameRate = format.getInteger(MediaFormat.KEY_FRAME_RATE); //获取视频的帧率
                    frameDuration = format.getLong(MediaFormat.KEY_DURATION); //获取视频时长
                }
            }

            muxer.start(); //开始合成

            audioVideoExtractor.selectTrack(mainAudioExtractorTrackIndex); //将提供音频的视频选择到音轨上
            MediaCodec.BufferInfo audioBufferInfo = new MediaCodec.BufferInfo();
            ByteBuffer audioByteBuffer = ByteBuffer.allocate(mainAudioMaxInputSize);
            while (true) {
                int readSampleSize = audioVideoExtractor.readSampleData(audioByteBuffer, 0); //检索当前编码的样本并将其存储在字节缓冲区中
                if (readSampleSize < 0) { //如果没有可获取的样本则退出循环
                    audioVideoExtractor.unselectTrack(mainAudioExtractorTrackIndex);
                    break;
                }

                long sampleTime = audioVideoExtractor.getSampleTime(); //获取当前展示样本的时间（单位毫秒）

                if (sampleTime < audioStartTime) { //如果样本时间小于我们想要的开始时间就快进
                    audioVideoExtractor.advance(); //推进到下一个样本，类似快进
                    continue;
                }

                if (sampleTime > audioStartTime + frameDuration) { //如果样本时间大于开始时间+视频时长，就退出循环
                    break;
                }
                //设置样本编码信息
                audioBufferInfo.size = readSampleSize;
                audioBufferInfo.offset = 0;
                audioBufferInfo.flags = audioVideoExtractor.getSampleFlags();
                audioBufferInfo.presentationTimeUs = sampleTime - audioStartTime;

                muxer.writeSampleData(mainAudioMuxerTrackIndex, audioByteBuffer, audioBufferInfo); //将样本写入
                audioVideoExtractor.advance(); //推进到下一个样本，类似快进
            }

            frameVideoExtractor.selectTrack(frameExtractorTrackIndex); //将提供视频图像的视频选择到视频轨上
            MediaCodec.BufferInfo videoBufferInfo = new MediaCodec.BufferInfo();
            ByteBuffer videoByteBuffer = ByteBuffer.allocate(frameMaxInputSize);
            while (true) {
                int readSampleSize = frameVideoExtractor.readSampleData(videoByteBuffer, 0); //检索当前编码的样本并将其存储在字节缓冲区中
                if (readSampleSize < 0) { //如果没有可获取的样本则退出循环
                    frameVideoExtractor.unselectTrack(frameExtractorTrackIndex);
                    break;
                }
                //设置样本编码信息
                videoBufferInfo.size = readSampleSize;
                videoBufferInfo.offset = 0;
                videoBufferInfo.flags = frameVideoExtractor.getSampleFlags();
                videoBufferInfo.presentationTimeUs += 1000 * 1000 / frameRate;

                muxer.writeSampleData(frameMuxerTrackIndex, videoByteBuffer, videoBufferInfo); //将样本写入
                frameVideoExtractor.advance(); //推进到下一个样本，类似快进
            }
        } catch (IOException e) {
            Log.e(TAG, "combineTwoVideos: ", e);
        } finally {
            //释放资源
            audioVideoExtractor.release();
            frameVideoExtractor.release();
            if (muxer != null) {
                muxer.release();
            }
        }
    }
}
