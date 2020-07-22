package com.tangzy.myannotation;

import javax.swing.text.View;

/**
 * ui提供者接口
 */
public interface ViewFinder {
    View findView(Object object, int id);
}
