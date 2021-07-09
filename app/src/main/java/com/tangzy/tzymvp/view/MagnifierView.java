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

/**
 * @author tzy
 * @date 2021/7/8 16:28
 * @discription
 */
class MagnifierView {
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
        if (mX >= 0 && mY >= 0){
            getBitmap(view);
            if (roundBitmap != null){
                mCanvas.drawBitmap(roundBitmap, radius,radius, null);
            }
        }
    }
    private  Bitmap riginaBitmap;
    private Bitmap roundBitmap;
    private Bitmap squareBitmap;

    private void getBitmap(View view) {
        if (mX >= 0 && mY >= 0){
            view.setDrawingCacheEnabled(true);

            if (view.getDrawingCache() != null){
                view.buildDrawingCache();
                riginaBitmap = Bitmap.createBitmap(view.getDrawingCache());
                view.destroyDrawingCache();
                view.setDrawingCacheEnabled(false);
                squareBitmap = Bitmap.createBitmap(riginaBitmap,(int)(mX - radius), (int)(mY - radius), (int) (2*radius), (int) (2*radius));

                roundBitmap = Bitmap.createBitmap((int) (2*radius*mScale),(int) (2*radius*mScale), Bitmap.Config.ARGB_8888);

                Canvas canvas = new Canvas(roundBitmap);
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
            }
        }
    }
}
