package com.tangzy.tzymvp.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.util.Logger;

public class MyView extends View {
    private Context context;
    /**
     * 创建一个继承View的class
     *View一共有四个构造器 这里先说两个
     * 第一个构造器的参数就是context,这是在Activity实例化的时候所需的,比如Button button = new Button(context);
     * 第二个构造器有两个参数 第一个同样是context 第二个参数AttributeSet放入了一些属性,这是我们在写XML文件时用到的比如
     * android:layout_width="match_parent"
     * android:layout_height="match_parent"如果不写函数的话是无法通过XML添加View
     */
    public MyView(Context context) {
        super(context);
        this.context = context;
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Logger.d("tangzy", "onMeasure");
//        Logger.d("tangzy", "widthMeasureSpec = "+widthMeasureSpec);
//        Logger.d("tangzy", "heightMeasureSpec = "+heightMeasureSpec);
//
//        final int minimumWidth = getSuggestedMinimumWidth();
//        final int minimumHeight = getSuggestedMinimumHeight();
//        Logger.d("tangzy", "---minimumWidth = " + minimumWidth + "");
//        Logger.d("tangzy", "---minimumHeight = " + minimumHeight + "");
//        int width = measureWidth(minimumWidth, widthMeasureSpec);
//        int height = measureHeight(minimumHeight, heightMeasureSpec);
//        setMeasuredDimension(width, height);
    }

    private int measureWidth(int defaultWidth, int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        Logger.d("tangzy", "---speSize = " + specSize + "");
        switch (specMode) {
            case MeasureSpec.AT_MOST:
//                defaultWidth = (int) mPaint.measureText(mText) + getPaddingLeft() + getPaddingRight();
                Logger.d("tangzy", "---speMode = AT_MOST");
                break;
            case MeasureSpec.EXACTLY:
                Logger.d("tangzy", "---speMode = EXACTLY");
                defaultWidth = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                Logger.d("tangzy", "---speMode = UNSPECIFIED");
                defaultWidth = Math.max(defaultWidth, specSize);
            default:
                break;
        }
        return defaultWidth;
    }

    private int measureHeight(int defaultHeight, int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        Logger.d("tangzy", "---speSize = " + specSize + "");

        switch (specMode) {
            case MeasureSpec.AT_MOST:
//                defaultHeight = (int) (-mPaint.ascent() + mPaint.descent()) + getPaddingTop() + getPaddingBottom();
                Logger.d("tangzy", "---speMode = AT_MOST");
                break;
            case MeasureSpec.EXACTLY:
                defaultHeight = specSize;
                Logger.d("tangzy", "---speSize = EXACTLY");
                break;
            case MeasureSpec.UNSPECIFIED:
                defaultHeight = Math.max(defaultHeight, specSize);
                Logger.d("tangzy", "---speSize = UNSPECIFIED");
//        1.基准点是baseline
//        2.ascent：是baseline之上至字符最高处的距离
//        3.descent：是baseline之下至字符最低处的距离
//        4.leading：是上一行字符的descent到下一行的ascent之间的距离,也就是相邻行间的空白距离
//        5.top：是指的是最高字符到baseline的值,即ascent的最大值
//        6.bottom：是指最低字符到baseline的值,即descent的最大值
                break;
            default:
                break;
        }
        return defaultHeight;

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Logger.d("tangzy", "onLayout");// 循环所有子View改变子view位置
        Logger.d("tangzy", "left = "+left +",top = "+ top+",right = "+right+",bottom = "+bottom);// 循环所有子View
    }

    @Override
    public void layout(int left, int top, int right, int bottom) {
        Logger.d("tangzy", "layout");// 循环所有子View改变子view位置
        Logger.d("tangzy", "left = "+left +",top = "+ top+",right = "+right+",bottom = "+bottom);// 循环所有子View
//        super.layout(l, t, r, b);
        super.layout(20, 20, 900, 1500);//改变view位置
    }

    //重写onDraw方法
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Logger.d("tangzy", "onDraw");
        canvas.drawColor(Color.RED);      //设置canvas的背景色
        float radius = 50;                //给定半径
        //给定圆心的的坐标
        float cx = 50;
        float cy = 50;
        Paint paint = new Paint();       //实例化一个Paint对象
        paint.setColor(Color.BLUE);      //设置圆的颜色
        paint.setStrokeWidth(5);

//        canvas.drawLine(10,30,100,200, paint);
//        float[] pts = {10,350, 100, 50, 500,500,600,600};
//        float[] pts = new float[1000];
//        for (int i = 0; i <1000;){
//            pts[i] = i;
//            pts[i+1] = i%50;
//            i=i+2;
//        }

//        canvas.drawLines(pts, paint);
//        canvas.drawLines(pts, 10, 20, paint);

//        canvas.drawPoints(pts, paint);

//        saveMatrix(canvas);

        //通过canvas的drawCircle方法画一个圆圈.
//        canvas.drawCircle(cx, cy, radius, paint);//
//        canvas.drawOval(0,0,100,100,paint);//画一个椭圆.
//        canvas.drawRoundRect(0,0,200,400,100, 50 ,paint);//圆角矩形
//        canvas.drawArc(300,500,500,900,0, 200, true ,paint);//扇面弓形（可椭圆）
//        Drawable drawable = context.getDrawable(R.mipmap.ic_launcher);
//        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
//        canvas.drawBitmap(bitmap, 50, 50, paint);


        Path path = new Path();
        path.addArc(10,10,500,500,500,300);
//        canvas.drawPath(path, paint);
        canvas.drawArc(10,10,500,500,500,300, true, paint);
    }

    private void saveMatrix(Canvas canvas) {
        Paint mPaint = new Paint();       //实例化一个Paint对象
        mPaint.setColor(Color.BLUE);      //设置圆的颜色
        mPaint.setStrokeWidth(5);
        //绘制蓝色矩形
        mPaint.setColor(getResources().getColor(android.R.color.holo_blue_light));
        canvas.drawRect(0, 0, 200, 200, mPaint);
        //保存
        canvas.save();
        //裁剪画布,并绘制红色矩形
        mPaint.setColor(getResources().getColor(android.R.color.holo_red_light));
        //平移.
        canvas.translate(100, 0);
        //缩放.
        canvas.scale(0.5f, 0.5f);
        //旋转
        canvas.rotate(-45);
        //倾斜
        canvas.skew(0, 0.5f);
        canvas.drawRect(0, 0, 200, 200, mPaint);
        //恢复画布
        canvas.restore();
        //绘制绿色矩形
        mPaint.setColor(getResources().getColor(android.R.color.holo_green_light));
        canvas.drawRect(0, 0, 50, 200, mPaint);
    }
}
