package com.tangzy.tzymvp.activity;
import android.graphics.Bitmap;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMetadataRetriever;
import android.media.MediaMuxer;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import androidx.appcompat.app.AppCompatActivity;

import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.util.Logger;
import com.tangzy.tzymvp.util.Utils;

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
    private Button combineVideoBtn, get_img;
    private ImageView imageView, imageView1;

    private MediaExtractor mediaExtractor;
    private MediaMuxer mediaMuxer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidu);
        exactorBtn = (Button) findViewById(R.id.exactor);
        muxerBtn = (Button) findViewById(R.id.muxer);
        muxerAudioBtn = (Button) findViewById(R.id.muxer_audio);
        combineVideoBtn = (Button) findViewById(R.id.combine_video);
        get_img = (Button) findViewById(R.id.get_img);
        imageView = findViewById(R.id.imageView);
        imageView1 = findViewById(R.id.imageView1);

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
               Utils.muxerAudio(SDCARD_PATH + "/input.mp4", SDCARD_PATH + "/output_audio.mp3");
            }
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

}
