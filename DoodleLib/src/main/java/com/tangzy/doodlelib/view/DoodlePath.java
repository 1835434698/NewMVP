package com.tangzy.doodlelib.view;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import com.tangzy.doodlelib.DoodleShape;


public class DoodlePath {
    private Paint mPaint;
    private Path mPath;
    private int mColor = Color.RED;
    private float mWidth = 6;
    private Paint.Style mStyle = Paint.Style.STROKE;
    private DoodleShape mDoodleShape = DoodleShape.HOLLOW_RECT;

    public Paint getmPaint() {
        return mPaint;
    }

    public void setmPaint(Paint mPaint) {
        this.mPaint = mPaint;
    }

    public Path getmPath() {
        return mPath;
    }

    public void setmPath(Path mPath) {
        this.mPath = mPath;
    }

    public int getmColor() {
        return mColor;
    }

    public void setmColor(int mColor) {
        this.mColor = mColor;
    }

    public float getmWidth() {
        return mWidth;
    }

    public void setmWidth(float mWidth) {
        this.mWidth = mWidth;
    }

    public Paint.Style getmStyle() {
        return mStyle;
    }

    public void setmStyle(Paint.Style mStyle) {
        this.mStyle = mStyle;
    }

    public DoodleShape getmDoodleShape() {
        return mDoodleShape;
    }

    public void setmDoodleShape(DoodleShape mDoodleShape) {
        this.mDoodleShape = mDoodleShape;
    }
}
