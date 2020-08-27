package com.tangzy.doodlelib;

/**
 * 常用图形
 */
public enum DoodleShape {
    HAND_WRITE, // 手绘
    ARROW, // 箭头
    LINE, // 直线
//    FILL_CIRCLE, // 实心圆
    HOLLOW_CIRCLE, // 空心圆
//    FILL_RECT, // 实心矩形
    HOLLOW_RECT; // 空心矩形

//
//    @Override
//    public void config(IDoodleItem doodleItem, Paint paint) {
//        if (doodleItem.getShape() == DoodleShape.ARROW
//        ) {
//            paint.setStyle(Paint.Style.FILL);
//        } else {
//            paint.setStyle(Paint.Style.STROKE);
//        }
//    }
//
//    @Override
//    public IDoodleShape copy() {
//        return this;
//    }
//
//    @Override
//    public void drawHelpers(Canvas canvas, IDoodle doodle) {
//
//    }
}
