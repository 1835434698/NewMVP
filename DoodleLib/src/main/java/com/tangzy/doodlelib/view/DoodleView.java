package com.tangzy.doodlelib.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.tangzy.doodlelib.DoodleShape;
import com.tangzy.doodlelib.util.DrawUtil;

import java.util.Stack;


@SuppressLint("AppCompatCustomView")
public class DoodleView extends ImageView implements IDoodle{
    private static final String TAG = "DoodleVIew";
    private Context context;

    private DoodleShape mShape;


    private Paint mPaint;

    private Stack<Path> stack = new Stack<>();
    private Stack<DoodlePath> paths = new Stack<>();

    public DoodleView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public DoodleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStrokeWidth(6);
        mPaint.setColor(Color.RED);      //设置圆的颜色
        mPaint.setStyle(Paint.Style.STROKE);
    }


    private float sx,sy, tx,ty = -1;
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(TAG, "dispatchTouchEvent");
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                sx = event.getX();
                sy = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                tx = event.getX();
                ty = event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                tx = event.getX();
                ty = event.getY();
                Path path = new Path();
                addPath(path,sx,sy,tx,ty);
//                stack.add(path);
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    private void addPath(Path path, float sx, float sy, float tx, float ty) {
        switch (mShape){
            case HOLLOW_RECT:
                updateRectPath(path,sx,sy,tx,ty);
                break;
            case HOLLOW_CIRCLE:
                updateOvalPath(path,sx,sy,tx,ty);
                break;
            case LINE:
                updateLinePath(path,sx,sy,tx,ty);
                break;
            case ARROW:
//                updateArrowPath(path,sx,sy,tx,ty);
                break;
            case HAND_WRITE:
//                updateRectPath(path,sx,sy,tx,ty);
                break;
        }
        doodlePath = new DoodlePath();
        doodlePath.setmPath(path);
//                doodlePath.setmColor();
//                doodlePath.setmPaint();
//                doodlePath.setmWidth();
        doodlePath.setmDoodleShape(mShape);
        paths.add(doodlePath);
        invalidate();
    }

    private DoodlePath doodlePath;

    //---------计算Path
    private Path mArrowTrianglePath;

    private void updateArrowPath(Path path, float sx, float sy, float ex, float ey, float size) {
        float arrowSize = size*4;
        double H = arrowSize; // 箭头高度
        double L = arrowSize / 2; // 底边的一�?

        double awrad = Math.atan(L / 2 / H); // 箭头角度
        double arraow_len = Math.sqrt(L / 2 * L / 2 + H * H) - 5; // 箭头的长�?
        double[] arrXY_1 = DrawUtil.rotateVec(ex - sx, ey - sy, awrad, true, arraow_len);
        double[] arrXY_2 = DrawUtil.rotateVec(ex - sx, ey - sy, -awrad, true, arraow_len);
        float x_3 = (float) (ex - arrXY_1[0]); // (x3,y3)是第�?端点
        float y_3 = (float) (ey - arrXY_1[1]);
        float x_4 = (float) (ex - arrXY_2[0]); // (x4,y4)是第二端�?
        float y_4 = (float) (ey - arrXY_2[1]);
        // 画线
        path.moveTo(sx, sy);
        path.lineTo(x_3, y_3);
        path.lineTo(x_4, y_4);
        path.close();

        awrad = Math.atan(L / H); // 箭头角度
        arraow_len = Math.sqrt(L * L + H * H); // 箭头的长�?
        arrXY_1 = DrawUtil.rotateVec(ex - sx, ey - sy, awrad, true, arraow_len);
        arrXY_2 = DrawUtil.rotateVec(ex - sx, ey - sy, -awrad, true, arraow_len);
        x_3 = (float) (ex - arrXY_1[0]); // (x3,y3)是第�?端点
        y_3 = (float) (ey - arrXY_1[1]);
        x_4 = (float) (ex - arrXY_2[0]); // (x4,y4)是第二端�?
        y_4 = (float) (ey - arrXY_2[1]);
        if (mArrowTrianglePath == null) {
            mArrowTrianglePath = new Path();
        }
        mArrowTrianglePath.reset();
        mArrowTrianglePath.moveTo(ex, ey);
        mArrowTrianglePath.lineTo(x_4, y_4);
        mArrowTrianglePath.lineTo(x_3, y_3);
        mArrowTrianglePath.close();
        path.addPath(mArrowTrianglePath);
    }

    private void updateLinePath(Path path, float sx, float sy, float ex, float ey) {
        path.moveTo(sx, sy);
        path.lineTo(ex, ey);
    }

    private void updateCirclePath(Path path, float sx, float sy, float dx, float dy) {
        float radius = (float) Math.sqrt((sx - dx) * (sx - dx) + (sy - dy) * (sy - dy));
        path.addCircle(sx, sy, radius, Path.Direction.CCW);

    }

    private void updateOvalPath(Path path, float sx, float sy, float dx, float dy) {
        path.addOval(sx, sy, dx, dy, Path.Direction.CCW);
    }

    private void updateRectPath(Path path, float sx, float sy, float dx, float dy) {
        // 保证　左上角　与　右下角　的对应关系
        if (sx < dx) {
            if (sy < dy) {
                path.addRect(sx, sy, dx, dy, Path.Direction.CCW);
            } else {
                path.addRect(sx, dy, dx, sy, Path.Direction.CCW);
            }
        } else {
            if (sy < dy) {
                path.addRect(dx, sy, sx, dy, Path.Direction.CCW);
            } else {
                path.addRect(dx, dy, sx, sy, Path.Direction.CCW);
            }
        }
    }


    public void setPaintColor(@ColorInt int color){
        if (mPaint.getColor() == color){
            return;
        }
        mPaint.setColor(color);      //设置圆的颜色
    }
    public void setPaintStrokeWidth(float width){
        if (mPaint.getStrokeWidth() == width){
            return;
        }
        mPaint.setStrokeWidth(width);
    }

    //重写onDraw方法
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw");

//            canvas.drawColor(Color.BLUE);      //设置canvas的背景色
//        Paint paint = new Paint();       //实例化一个Paint对象
//        paint.setColor(Color.BLUE);      //设置圆的颜色
//        paint.setStrokeWidth(3);
//        paint.setStyle(Paint.Style.STROKE);

        if (tx > -1){

            Path path = new Path();

//            updateRectPath(path, sx, sy, tx, ty);// addRect

            updateOvalPath(path, sx, sy, tx, ty);//
            canvas.drawPath(path, mPaint);
        }
        if (paths.size() >0){

            for (DoodlePath item :paths){

                canvas.drawPath(item.getmPath(), item.getmPaint());
            }
        }

    }

    @Override
    public void setPen(Paint pen) {

    }

    @Override
    public Paint getPen() {
        return null;
    }

    @Override
    public void setShape(DoodleShape shape) {
        if (shape == null) {
            throw new RuntimeException("Shape can't be null");
        }
        mShape = shape;

    }

    @Override
    public DoodleShape getShape() {
        return null;
    }
}
