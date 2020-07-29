package com.tangzy.pdfrecyclerview.view;

@SuppressWarnings("EmptyMethod")
public interface OnAnimationEventListener {

    void onComplete();

    void onInterruptedByUser();

    void onInterruptedByNewAnim();
}