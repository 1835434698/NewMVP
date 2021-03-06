package com.tangzy.tzymvp.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

import com.tangzy.tzymvp.util.Logger;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author tzy
 * @date 2021/7/8 16:28
 * @discription
 */
class MagnifierView {
    private static final String TAG = "MagnifierView";
    private float mX = -1;
    private float mY = -1;

    private float radius = 100f;//半径
    private float mScale = 1.5f;//半径

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getmScale() {
        return mScale;
    }

    public void setmScale(float mScale) {
        this.mScale = mScale;
    }

    private Paint paint;

    public void setLocation(float x, float y){
        mX = x;
        mY = y;
    }

    public void onDraw(Canvas mCanvas, View view){
        Logger.d(TAG, "onDraw 1 ");
        if (mX >= 0 && mY >= 0){
            Logger.d(TAG, "onDraw 2 ");
            getBitmap(view);
            Logger.d(TAG, "onDraw 3 ");
            if (roundBitmap != null){
                Logger.d(TAG, "onDraw 4 ");
                mCanvas.drawBitmap(roundBitmap, radius,radius, null);
            }
        }
    }
    private  Bitmap originaBitmap;
    private Bitmap roundBitmap;
    private Canvas canvas;

    private void getBitmap(View view) {
        if (mX >= 0 && mY >= 0){

            view.setDrawingCacheEnabled(true);

            if (view.getDrawingCache() != null){
                originaBitmap = Bitmap.createBitmap((int) (2 * radius), (int) (2 * radius), Bitmap.Config.RGB_565);
                canvas = new Canvas(originaBitmap);

                canvas.drawColor(Color.BLACK);
                view.buildDrawingCache();
                canvas.drawBitmap(view.getDrawingCache(), new Rect((int) (mX - radius), (int) (mY - radius), (int) (mX + radius), (int) (mY + radius)), new Rect(0, 0, originaBitmap.getWidth(), originaBitmap.getHeight()),null);

                canvas.save();
                view.destroyDrawingCache();
                view.setDrawingCacheEnabled(false);


                roundBitmap = Bitmap.createBitmap((int) (2*radius*mScale),(int) (2*radius*mScale), Bitmap.Config.ARGB_8888);

                canvas = new Canvas(roundBitmap);
                paint = new Paint();
                Rect src = new Rect(0,0 ,(int) (2*radius),(int) (2*radius));
                Rect dst = new Rect(0,0 ,(int) (2*radius*mScale),(int) (2*radius*mScale));
                RectF rectF = new RectF(dst);
                paint.setAntiAlias(true);

                canvas.drawARGB(0,0,0,0);
                paint.setColor(0xff424242);
                canvas.drawRoundRect(rectF, (int) (radius*mScale), (int) (radius*mScale), paint);

                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                canvas.drawBitmap(originaBitmap, src, dst, paint);
                canvas.save();
                originaBitmap.recycle();
                canvas = null;
            }
        }
    }

    private void saveBitmap(Bitmap bitmap){
        String path = "/storage/emulated/0/Allinmd/linshi/"+System.currentTimeMillis()+".png";
        File f = new File(path);
        try {
            f.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

        }catch (Exception e){
            Logger.d(TAG, "saveBitmap error = "+e.getMessage());

        }
    }
}
