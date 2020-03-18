package com.tangzy.tzymvp.servive;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;

import com.tangzy.tzymvp.Constant;
import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.util.Logger;

public class SingASongService extends Service {
    private static final String TAG = "SingASongService";

    private MediaPlayer mMediaPlayer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer = MediaPlayer.create(Constant.app, R.raw.no_kill);
        mMediaPlayer.setLooping(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Logger.d(TAG, "播放时 线程名称：");
                startPlaySong();
            }
        }).start();
        return START_STICKY;
    }

    //开始、暂停播放
    private void startPlaySong() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(Constant.app, R.raw.no_kill);
            mMediaPlayer.start();
        } else {
            mMediaPlayer.start();
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaPlayer.pause();
        stopPlaySong();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(new Intent(getApplicationContext(), SingASongService.class));
        } else {
            startService(new Intent(getApplicationContext(), SingASongService.class));
        }

    }

    //停止播放销毁对象
    private void stopPlaySong() {
        Logger.d(TAG, "音乐停止播放,播放对象为：" + mMediaPlayer.hashCode());
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
