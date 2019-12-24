package com.tangzy.tzymvp.bean;

import com.tangzy.themvp.model.IModel;

import java.io.Serializable;

/**
 * class
 *
 * @author Administrator
 * @date 2019/12/23
 */
public class RecycleViewBean  implements IModel, Serializable {
    private String name;
    private int img;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
