package com.tangzy.tzymvp.activity;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMetadataRetriever;
import android.media.MediaMuxer;
import android.os.Environment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.LinkedHashMap;

import androidx.appcompat.app.AppCompatActivity;

import com.frank.ffmpeg.FFmpegCmd;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.tangzy.tzymvp.R;
//import com.tangzy.tzymvp.util.FFmpegCmd;
import com.tangzy.tzymvp.util.FFmpegUtil;
import com.tangzy.tzymvp.util.JsonParser;
import com.tangzy.tzymvp.util.Logger;
import com.tangzy.tzymvp.util.Toasts;
import com.tangzy.tzymvp.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * ${CLASS} class
 *
 * @author Administrator
 * @date 2019/7/10
 */
public class AiduActivity extends AppCompatActivity {

    private static final String SDCARD_PATH = Environment.getExternalStorageDirectory().getPath()+"/aaaaa";

    private Button exactorBtn;
    private Button muxerBtn;
    private Button muxerAudioBtn;
    private Button combineVideoBtn, get_img, get_text;
    private ImageView imageView, imageView1;

    private MediaExtractor mediaExtractor;
    private MediaMuxer mediaMuxer;
    private final String TAG = "AiduActivity";
//    private String voicePath = SDCARD_PATH + "/transform.aac";
    private String voicePath = SDCARD_PATH + "/new.wav";
//    private String voicePath = SDCARD_PATH + "/output_audio.mp3";
//    private String voicePath = SDCARD_PATH + "/iattest.wav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidu);
        exactorBtn = (Button) findViewById(R.id.exactor);
        muxerBtn = (Button) findViewById(R.id.muxer);
        muxerAudioBtn = (Button) findViewById(R.id.muxer_audio);
        combineVideoBtn = (Button) findViewById(R.id.combine_video);
        get_img = (Button) findViewById(R.id.get_img);
        get_text = findViewById(R.id.get_text);
        imageView = findViewById(R.id.imageView);
        imageView1 = findViewById(R.id.imageView1);


        // 初始化识别无UI识别对象
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(AiduActivity.this, mInitListener);

        mSharedPreferences = getSharedPreferences(PREFER_NAME,
                Activity.MODE_PRIVATE);

        exactorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exactorMedia();
            }
        });
        muxerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                muxerMedia();
            }
        });
        muxerAudioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               Utils.muxerAudio(SDCARD_PATH + "/input.mp4", voicePath);
                getVoice(SDCARD_PATH + "/input.mp4", voicePath);

            }
        });
        get_text.setOnClickListener(v -> {
//            Utils.byteToWav(Utils.getBytes(voicePath), SDCARD_PATH + "/output_audio.wav");

//            Utils.mp3ToPcm(voicePath, SDCARD_PATH + "/output_audio.pcm");
            executeStream();
        });

        combineVideoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                combineVideo();
            }
        });
        mediaExtractor = new MediaExtractor();


        get_img.setOnClickListener(v -> {
            Utils.muxerImg(SDCARD_PATH + "/input.mp4", SDCARD_PATH + "/output_audio.jpg");
//创建MediaMetadataRetriever对象
            MediaMetadataRetriever mmr=new MediaMetadataRetriever();
//设置资源位置
            String path = SDCARD_PATH + "/input.mp4";
//绑定资源
            mmr.setDataSource(path);
//获取第一帧图像的bitmap对象
            Bitmap bitmap=mmr.getFrameAtTime();
            String s = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            Logger.d("tangzy", "s = "+s);
            String s1 = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_FRAME_COUNT);
            Logger.d("tangzy", "s1 = "+s1);
//            mmr.getFrameAtIndex(1);
            Bitmap bitmap1 = mmr.getFrameAtTime(2000);
//加载到ImageView控件上
            imageView.setImageBitmap(bitmap);
            imageView1.setImageBitmap(bitmap1);
        });


    }

    private void getVoice(String srcFile, String wavFile) {
        String[] commandLine = null;
        //pcm数据的采样率，一般采样率为8000、16000、44100
        int sampleRate = 16000;
        //pcm数据的声道，单声道为1，立体声道为2
        int channel = 1;
        commandLine = FFmpegUtil.encodeAudio(srcFile, wavFile, sampleRate, channel);
        executeFFmpegCmd(commandLine);
    }
    /**
     * 执行ffmpeg命令行
     * @param commandLine commandLine
     */
    private void executeFFmpegCmd(final String[] commandLine){
        if(commandLine == null){
            return;
        }
        FFmpegCmd.execute(commandLine, new FFmpegCmd.OnHandleListener() {
            @Override
            public void onBegin() {
                Log.i(TAG, "handle audio onBegin...");
//                mHandler.obtainMessage(MSG_BEGIN).sendToTarget();
            }

            @Override
            public void onEnd(int result) {
                Log.i(TAG, "handle audio onEnd...");
//                mHandler.obtainMessage(MSG_FINISH).sendToTarget();
            }
        });
    }

    /**
     * 根据 mime 分离 视频的 音频轨和视频轨
     */
    private void exactorMedia() {
        FileOutputStream videoOutputStream = null;
        FileOutputStream audioOutputStream = null;
        try {
            File videoFile = new File(SDCARD_PATH, "output_video.mp4");
            if (!videoFile.exists()) {
                videoFile.createNewFile();
            }

            File audioFile = new File(SDCARD_PATH, "output_audio");
            videoOutputStream = new FileOutputStream(videoFile);
            audioOutputStream = new FileOutputStream(audioFile);
            mediaExtractor.setDataSource(SDCARD_PATH + "/input.mp4");
            int trackCount = mediaExtractor.getTrackCount();
            int audioTrackIndex = -1;
            int videoTrackIndex = -1;
            for (int i = 0; i < trackCount; i++) {
                MediaFormat trackFormat = mediaExtractor.getTrackFormat(i);
                Log.e("fuck", trackFormat.toString());
                String mineType = trackFormat.getString(MediaFormat.KEY_MIME);
                if (mineType.startsWith("video/")) {
                    videoTrackIndex = i;
                }
                if (mineType.startsWith("audio/")) {
                    audioTrackIndex = i;
                }
            }
            ByteBuffer byteBuffer = ByteBuffer.allocate(500 * 1024);
            mediaExtractor.selectTrack(videoTrackIndex);
            while (true) {
                int readSampleCount = mediaExtractor.readSampleData(byteBuffer, 0);
                if (readSampleCount < 0) {
                    break;
                }
                byte[] buffer = new byte[readSampleCount];
                byteBuffer.get(buffer);
                videoOutputStream.write(buffer);
                byteBuffer.clear();
                mediaExtractor.advance(); // “快进”到指定帧
            }
            mediaExtractor.selectTrack(audioTrackIndex);
            while (true) {
                int readSampleCount = mediaExtractor.readSampleData(byteBuffer, 0);
                if (readSampleCount < 0) {
                    break;
                }
                byte[] buffer = new byte[readSampleCount];
                byteBuffer.get(buffer);
                audioOutputStream.write(buffer);
                byteBuffer.clear();
                mediaExtractor.advance();
            }
            Log.e("fuck", "finish");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            mediaExtractor.release();
            try {
                videoOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void muxerMedia() {
        mediaExtractor = new MediaExtractor();
        int videoIndex = -1;
        try {
            mediaExtractor.setDataSource(SDCARD_PATH + "/input.mp4");
            int trackCount = mediaExtractor.getTrackCount();
            for (int i = 0; i < trackCount; i++) {
                MediaFormat trackFormat = mediaExtractor.getTrackFormat(i);
                String mimeType = trackFormat.getString(MediaFormat.KEY_MIME);
                if (mimeType.startsWith("video/")) {
                    videoIndex = i;
                }
            }
            mediaExtractor.selectTrack(videoIndex);
            MediaFormat trackFormat = mediaExtractor.getTrackFormat(videoIndex);
            mediaMuxer = new MediaMuxer(SDCARD_PATH + "/output_video", MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
            int trackIndex = mediaMuxer.addTrack(trackFormat);
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 500);
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            mediaMuxer.start();
            long videoSampleTime;
            {
                mediaExtractor.readSampleData(byteBuffer, 0);
                //skip first I frame
                if (mediaExtractor.getSampleFlags() == MediaExtractor.SAMPLE_FLAG_SYNC){
                    mediaExtractor.advance();
                }
                mediaExtractor.readSampleData(byteBuffer, 0);
                long firstVideoPTS = mediaExtractor.getSampleTime();
                mediaExtractor.advance();
                mediaExtractor.readSampleData(byteBuffer, 0);
                long SecondVideoPTS = mediaExtractor.getSampleTime();
                videoSampleTime = Math.abs(SecondVideoPTS - firstVideoPTS);
                Log.d("fuck", "videoSampleTime is " + videoSampleTime);
            }
            mediaExtractor.unselectTrack(videoIndex);
            mediaExtractor.selectTrack(videoIndex);
            while (true) {
                int readSampleSize = mediaExtractor.readSampleData(byteBuffer, 0);
                if (readSampleSize < 0) {
                    break;
                }
                mediaExtractor.advance();
                bufferInfo.size = readSampleSize;
                bufferInfo.offset = 0;
                bufferInfo.flags = mediaExtractor.getSampleFlags();
                bufferInfo.presentationTimeUs += videoSampleTime;
                mediaMuxer.writeSampleData(trackIndex, byteBuffer, bufferInfo);
            }
            mediaMuxer.stop();
            mediaExtractor.release();
            mediaMuxer.release();
            Log.e("TAG", "finish");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void muxerAudio() {
        mediaExtractor = new MediaExtractor();
        int audioIndex = -1;
        try {
            mediaExtractor.setDataSource(SDCARD_PATH + "/input.mp4");
            int trackCount = mediaExtractor.getTrackCount();
            for (int i = 0; i < trackCount; i++) {
                MediaFormat trackFormat = mediaExtractor.getTrackFormat(i);
                if (trackFormat.getString(MediaFormat.KEY_MIME).startsWith("audio/")) {
                    audioIndex = i;
                }
            }
            mediaExtractor.selectTrack(audioIndex);
            MediaFormat trackFormat = mediaExtractor.getTrackFormat(audioIndex);
            mediaMuxer = new MediaMuxer(SDCARD_PATH + "/output_audio.mp3", MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
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
            Log.e("fuck", "finish");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void combineVideo() {
        try {
            MediaExtractor videoExtractor = new MediaExtractor();
            videoExtractor.setDataSource(SDCARD_PATH + "/output_video");
            MediaFormat videoFormat = null;
            int videoTrackIndex = -1;
            int videoTrackCount = videoExtractor.getTrackCount();
            for (int i = 0; i < videoTrackCount; i++) {
                videoFormat = videoExtractor.getTrackFormat(i);
                String mimeType = videoFormat.getString(MediaFormat.KEY_MIME);
                if (mimeType.startsWith("video/")) {
                    videoTrackIndex = i;
                    break;
                }
            }
            MediaExtractor audioExtractor = new MediaExtractor();
            audioExtractor.setDataSource(SDCARD_PATH + "/output_audio");
            MediaFormat audioFormat = null;
            int audioTrackIndex = -1;
            int audioTrackCount = audioExtractor.getTrackCount();
            for (int i = 0; i < audioTrackCount; i++) {
                audioFormat = audioExtractor.getTrackFormat(i);
                String mimeType = audioFormat.getString(MediaFormat.KEY_MIME);
                if (mimeType.startsWith("audio/")) {
                    audioTrackIndex = i;
                    break;
                }
            }
            videoExtractor.selectTrack(videoTrackIndex);
            audioExtractor.selectTrack(audioTrackIndex);
            MediaCodec.BufferInfo videoBufferInfo = new MediaCodec.BufferInfo();
            MediaCodec.BufferInfo audioBufferInfo = new MediaCodec.BufferInfo();
            mediaMuxer = new MediaMuxer(SDCARD_PATH + "/output", MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
            int writeVideoTrackIndex = mediaMuxer.addTrack(videoFormat);
            int writeAudioTrackIndex = mediaMuxer.addTrack(audioFormat);
            mediaMuxer.start();
            ByteBuffer byteBuffer = ByteBuffer.allocate(500 * 1024);
            long sampleTime = 0;
            {
                videoExtractor.readSampleData(byteBuffer, 0);
                if (videoExtractor.getSampleFlags() == MediaExtractor.SAMPLE_FLAG_SYNC) {
                    videoExtractor.advance();
                }
                videoExtractor.readSampleData(byteBuffer, 0);
                long secondTime = videoExtractor.getSampleTime();
                videoExtractor.advance();
                long thirdTime = videoExtractor.getSampleTime();
                sampleTime = Math.abs(thirdTime - secondTime);
            }
            videoExtractor.unselectTrack(videoTrackIndex);
            videoExtractor.selectTrack(videoTrackIndex);
            while (true) {
                int readVideoSampleSize = videoExtractor.readSampleData(byteBuffer, 0);
                if (readVideoSampleSize < 0) {
                    break;
                }
                videoBufferInfo.size = readVideoSampleSize;
                videoBufferInfo.presentationTimeUs += sampleTime;
                videoBufferInfo.offset = 0;
                videoBufferInfo.flags = videoExtractor.getSampleFlags();
                mediaMuxer.writeSampleData(writeVideoTrackIndex, byteBuffer, videoBufferInfo);
                videoExtractor.advance();
            }
            while (true) {
                int readAudioSampleSize = audioExtractor.readSampleData(byteBuffer, 0);
                if (readAudioSampleSize < 0) {
                    break;
                }
                audioBufferInfo.size = readAudioSampleSize;
                audioBufferInfo.presentationTimeUs += sampleTime;
                audioBufferInfo.offset = 0;
                audioBufferInfo.flags = videoExtractor.getSampleFlags();
                mediaMuxer.writeSampleData(writeAudioTrackIndex, byteBuffer, audioBufferInfo);
                audioExtractor.advance();
            }
            mediaMuxer.stop();
            mediaMuxer.release();
            videoExtractor.release();
            audioExtractor.release();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 语音听写对象
    private SpeechRecognizer mIat;

    private StringBuffer buffer = new StringBuffer();
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    private String resultType = "json";

    private SharedPreferences mSharedPreferences;
    public static final String PREFER_NAME = "com.iflytek.setting";
    private boolean mTranslateEnable = false;

    private boolean cyclic = false;//音频流识别是否循环调用

    int ret = 0; // 函数调用返回值

    Handler han = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x001) {
                executeStream();
            }
        }
    };

    //执行音频流识别操作
    private void executeStream() {

        buffer.setLength(0);
        mIatResults.clear();
        // 设置参数
        setParam();
        // 设置音频来源为外部文件
        mIat.setParameter(SpeechConstant.AUDIO_SOURCE, "-1");
        // 也可以像以下这样直接设置音频文件路径识别（要求设置文件在sdcard上的全路径）：
        // mIat.setParameter(SpeechConstant.AUDIO_SOURCE, "-2");
        //mIat.setParameter(SpeechConstant.ASR_SOURCE_PATH, "sdcard/XXX/XXX.pcm");
        ret = mIat.startListening(mRecognizerListener);
        if (ret != ErrorCode.SUCCESS) {
            Toasts.showToastShort("识别失败,错误码：" + ret+",请点击网址https://www.xfyun.cn/document/error-code查询解决方案");
        } else {
            byte[] audioData = Utils.readAudioFile(voicePath);

            if (null != audioData) {
                Toasts.showToastShort(getString(R.string.text_begin_recognizer));
                // 一次（也可以分多次）写入音频文件数据，数据格式必须是采样率为8KHz或16KHz（本地识别只支持16K采样率，云端都支持），
                // 位长16bit，单声道的wav或者pcm
                // 写入8KHz采样的音频时，必须先调用setParameter(SpeechConstant.SAMPLE_RATE, "8000")设置正确的采样率
                // 注：当音频过长，静音部分时长超过VAD_EOS将导致静音后面部分不能识别。
                // 音频切分方法：FucUtil.splitBuffer(byte[] buffer,int length,int spsize);
                mIat.writeAudio(audioData, 0, audioData.length);

                mIat.stopListening();
            } else {
                mIat.cancel();
                Toasts.showToastShort("读取音频流失败");
            }
        }
    }
    /**
     * 参数设置
     *
     * @return
     */
    public void setParam() {
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);

        // 设置听写引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, resultType);

        this.mTranslateEnable = mSharedPreferences.getBoolean( "translate", false );
        if( mTranslateEnable ){
            Log.i( TAG, "translate enable" );
            mIat.setParameter( SpeechConstant.ASR_SCH, "1" );
            mIat.setParameter( SpeechConstant.ADD_CAP, "translate" );
            mIat.setParameter( SpeechConstant.TRS_SRC, "its" );
        }

        String lag = mSharedPreferences.getString("iat_language_preference",
                "mandarin");
        if (lag.equals("en_us")) {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
            mIat.setParameter(SpeechConstant.ACCENT, null);

            if( mTranslateEnable ){
                mIat.setParameter( SpeechConstant.ORI_LANG, "en" );
                mIat.setParameter( SpeechConstant.TRANS_LANG, "cn" );
            }
        } else {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            // 设置语言区域
            mIat.setParameter(SpeechConstant.ACCENT, lag);

            if( mTranslateEnable ){
                mIat.setParameter( SpeechConstant.ORI_LANG, "cn" );
                mIat.setParameter( SpeechConstant.TRANS_LANG, "en" );
            }
        }
        //此处用于设置dialog中不显示错误码信息
        //mIat.setParameter("view_tips_plain","false");

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString("iat_vadbos_preference", "4000"));

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString("iat_vadeos_preference", "1000"));

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences.getString("iat_punc_preference", "1"));

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/iat.wav");
    }


    /**
     * 听写监听器。
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            Toasts.showToastShort("开始说话");
        }

        @Override
        public void onError(SpeechError error) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            if(mTranslateEnable && error.getErrorCode() == 14002) {
                Toasts.showToastShort( error.getPlainDescription(true)+"\n请确认是否已开通翻译功能" );
            } else {
                Toasts.showToastShort(error.getPlainDescription(true));
            }
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            Toasts.showToastShort("结束说话");
        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            Log.d(TAG, "results = "+results.getResultString());
            if (resultType.equals("json")) {
                if( mTranslateEnable ){
                    printTransResult( results );
                }else{
                    printResult(results);
                }
            }else if(resultType.equals("plain")) {
                buffer.append(results.getResultString());
                Logger.d(TAG, "onResult = "+buffer.toString() );
            }

            if (isLast & cyclic) {
                // TODO 最后的结果
                Message message = Message.obtain();
                message.what = 0x001;
                han.sendMessageDelayed(message,100);
            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            Toasts.showToastShort("当前正在说话，音量大小：" + volume);
            Log.d(TAG, "返回音频数据："+data.length);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };

    private void printResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }
        Logger.d(TAG, "printResult = "+resultBuffer.toString() );
//        mResultText.setText(resultBuffer.toString());
//        mResultText.setSelection(mResultText.length());
    }

    private void printTransResult (RecognizerResult results) {
        String trans  = JsonParser.parseTransResult(results.getResultString(),"dst");
        String oris = JsonParser.parseTransResult(results.getResultString(),"src");

        if( TextUtils.isEmpty(trans)||TextUtils.isEmpty(oris) ){
            Toasts.showToastShort( "解析结果失败，请确认是否已开通翻译功能。" );
        }else{
            Logger.d(TAG, "原始语言:\n"+oris+"\n目标语言:\n"+trans );
//            mResultText.setText( "原始语言:\n"+oris+"\n目标语言:\n"+trans );
        }
    }
    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                Toasts.showToastShort("初始化失败，错误码：" + code+",请点击网址https://www.xfyun.cn/document/error-code查询解决方案");
            }
        }
    };
}
