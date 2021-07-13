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
    private Bitmap squareBitmap;
    private Canvas canvas;

    private void getBitmap(View view) {
        if (mX >= 0 && mY >= 0){
//            if (mX - radius < 0 || mY - radius < 0 || mX + radius > view.getWidth() || mY + radius > view.getHeight() ){
//                Logger.d(TAG, "onDraw return");
//                return;
//            }

            view.setDrawingCacheEnabled(true);

            if (view.getDrawingCache() != null){
                originaBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.RGB_565);
                canvas = new Canvas(originaBitmap);

                canvas.drawColor(Color.BLACK);
                view.buildDrawingCache();
                canvas.drawBitmap(Bitmap.createBitmap(view.getDrawingCache()), null, new Rect(0, 0, view.getWidth(), view.getHeight()),null);

                canvas.save();
                view.destroyDrawingCache();
                view.setDrawingCacheEnabled(false);

                getSquareBitmap(originaBitmap);

                saveBitmap(squareBitmap);
                originaBitmap.recycle();


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
                canvas.drawBitmap(squareBitmap, src, dst, paint);
                canvas.save();
                squareBitmap.recycle();
                canvas = null;
            }
        }
    }

    private void getSquareBitmap(Bitmap bitmap) {
        int sqWidth = (int) (2 * radius);
        int left = (int) (mX - radius);
        int top = (int)(mY - radius);
        int width = sqWidth;
        int heigh = sqWidth;
        if (left < 0){
            width = width + left;
        }
        if (top < 0){
            heigh = heigh + top;
        }
        if (mX +radius > bitmap.getWidth()){
            width = (int) (bitmap.getWidth() - mX + radius);
        }

        if (mY +radius > bitmap.getHeight()){
            heigh = (int) (bitmap.getHeight() - mY + radius);
        }


        Logger.d(TAG, "squareBitmap  left = "+left+", width = "+width+", top = "+top+", heigh = "+heigh);
        squareBitmap = Bitmap.createBitmap(bitmap, left < 0 ? 0: left, top < 0 ? 0: top, width, heigh);
        Logger.d(TAG, "squareBitmap  width = "+squareBitmap.getWidth()+", height = "+squareBitmap.getHeight());
        if (squareBitmap.getWidth() != squareBitmap.getHeight()){
            Bitmap outBitmap = Bitmap.createBitmap(sqWidth, sqWidth, Bitmap.Config.RGB_565);
            canvas = new Canvas(outBitmap);
            final int color = 0xff424242;
            paint = new Paint();
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            Rect dst = new Rect(left < 0 ? -left: 0,top < 0 ? -top: 0 ,left < 0 ?sqWidth: width ,top < 0 ?sqWidth: heigh);
            canvas.drawBitmap(squareBitmap, null, dst, null);
            canvas.save();

            squareBitmap = outBitmap;
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
