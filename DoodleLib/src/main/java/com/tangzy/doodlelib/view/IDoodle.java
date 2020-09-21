package com.tangzy.doodlelib.view;

import android.graphics.Paint;

import com.tangzy.doodlelib.DoodleShape;

public interface IDoodle {

    /**
     * 设置画笔
     *
     * @param pen
     */
    public void setPen(Paint pen);

    /**
     * 获取画笔
     */
    public Paint getPen();

    /**
     * 设置画笔形状
     *
     * @param shape
     */
    public void setShape(DoodleShape shape);

    /**
     * 获取画笔形状
     */
    public DoodleShape getShape();
}
