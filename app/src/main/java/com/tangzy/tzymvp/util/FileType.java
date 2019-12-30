package com.tangzy.tzymvp.util;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * FileType class
 *
 * @author Administrator
 * @date 2019/12/30
 */
public class FileType {
    public final String fileType;
    public static final String TYPE_MUSIC = "mp3";
    public static final String TYPE_PHOTO = "png";
    public static final String TYPE_TEXT = "txt";


    //Retention 是元注解，简单地讲就是系统提供的，用于定义注解的“注解”
    @Retention(RetentionPolicy.SOURCE)
    //这里指定int的取值只能是以下范围
    @StringDef({TYPE_MUSIC, TYPE_PHOTO, TYPE_TEXT})
//    @IntDef({})
    @interface FileTypeDef {
    }
    public FileType(@FileTypeDef String fileType) {
        this.fileType = fileType;
    }
}
