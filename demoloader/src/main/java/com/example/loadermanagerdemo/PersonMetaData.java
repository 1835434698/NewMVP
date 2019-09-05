package com.example.loadermanagerdemo;

import android.provider.BaseColumns;

/**
 * Created by 若兰 on 2016/3/31.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */

public final class PersonMetaData  {

    public static abstract class Person implements BaseColumns{

        public static final String NAME = "name";
        public static final String AGE = "age";
        public static final String TABLE_NAME = "person";

    }
}
