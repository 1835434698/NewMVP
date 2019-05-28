package com.tangzy.tzymvp.activity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.tangzy.tzymvp.R;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ShowWaveActivity extends AppCompatActivity {
    private SurfaceHolder holder;
    private SurfaceView surface;
    private Paint paint;
    private final int HEIGHT = 320;
    // 要绘制的曲线的水平宽度
    private final int WIDTH = 500;
    // 离屏幕左边界的起始距离
    private final int X_OFFSET = 5;
    // 初始化X坐标
    private int cx = X_OFFSET;
    // 实际的Y轴的位置
    private int centerY = HEIGHT / 2;
//    private Timer timer = new Timer();
    /**
     * 线程任务
     */
    private ScheduledExecutorService time = new ScheduledThreadPoolExecutor(4);
    private TimerTask task = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_wave);
        // 获得SurfaceView对象
        surface = findViewById(R.id.activity_show_wave_sv);
        // 初始化SurfaceHolder对象
        holder = surface.getHolder();
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(3);

    }


    public void click(final View v) {
        drawBackGround(holder);
        cx = X_OFFSET;
        if (task != null) {
            task.cancel();
        }
        task = new TimerTask() {

            @Override
            public void run() {
                // 根据是正玄还是余玄和X坐标确定Y坐标
                int cy = v.getId() == R.id.activity_show_wave_btn_sin ?
                        centerY  - (int) (100 * Math.sin((cx - 5) * 2 * Math.PI / 150))
                        : centerY - (int) (100 * Math.cos((cx - 5) * 2 * Math.PI / 150));
                Canvas canvas = holder.lockCanvas(new Rect(cx,cy - 2,cx + 2,cy + 2));
                // 根据Ｘ，Ｙ坐标画点
                canvas.drawPoint(cx, cy, paint);
                cx++;
                // 超过指定宽度，线程取消，停止画曲线
                if (cx > WIDTH) {
                    task.cancel();
                    task = null;
                }
                // 提交修改
                holder.unlockCanvasAndPost(canvas);
            }
        };
//        timer.schedule(task, 0, 30);
        time.scheduleAtFixedRate(task, 0, 30, TimeUnit.MILLISECONDS);
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                drawBackGround(holder);
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
//                timer.cancel();
                time.shutdown();
            }
        });
    }

    private void drawBackGround(SurfaceHolder holder) {
        Canvas canvas = holder.lockCanvas();
        // 绘制白色背景
        canvas.drawColor(Color.WHITE);
        Paint p = new Paint();
        p.setColor(Color.BLACK);
        p.setStrokeWidth(2);
        // 绘制坐标轴
        canvas.drawLine(X_OFFSET, centerY, WIDTH, centerY, p);
        canvas.drawLine(X_OFFSET, 40, X_OFFSET, HEIGHT, p);
        holder.unlockCanvasAndPost(canvas);
        holder.lockCanvas(new Rect(0, 0, 0, 0));
        holder.unlockCanvasAndPost(canvas);
    }
}
