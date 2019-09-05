package com.example.loadermanagerdemo;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.Nullable;

/**
 * Created by 若兰 on 2016/3/31.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */

public class PersonContentProvider extends ContentProvider {

    private static final String AUTHORITY = "com.example.contentproviderdemo.personcontentprovider";


    private static final int SINGLE_CODE = 2;   //返回单个记录的匹配码
    private static final int MUTIPLE_CODE = 1;   //返回多个的记录的匹配码

    //text/plain
    private static final String SINGLE_TYPE = "vnd.android.cursor.item/person";

    private static final String MUTIPLE_TYPE = "vnd.android.cursor.dir/person";

    //创建一个Uri的UriMatcher(匹配器)
    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        //当你传过来的路径是content://com.example.contentproviderdemo.personcontentprovider/person  匹配下面的
        uriMatcher.addURI(AUTHORITY, "person", MUTIPLE_CODE);
        //如果传过来的路径是content://com.example.contentproviderdemo.personcontentprovider/person/1
        //#号是通配符
        uriMatcher.addURI(AUTHORITY, "person/#", SINGLE_CODE);
    }

    private DataBaseAdapter.DatabaseHelper dbHelper;
    private SQLiteDatabase db;


    @Override
    public boolean onCreate() {
        dbHelper = new DataBaseAdapter.DatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case SINGLE_CODE:

                return SINGLE_TYPE;
            case MUTIPLE_CODE:

                return MUTIPLE_TYPE;
        }
        return null;
    }


    //用于从contentprovider中获取数据
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        switch (uriMatcher.match(uri)) {
            case SINGLE_CODE://查询单个
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                long parseId = ContentUris.parseId(uri);  //拿出id值，相当于4
                selection = PersonMetaData.Person._ID + "=?";
                selectionArgs = new String[]{String.valueOf(parseId)};
                return db.query(PersonMetaData.Person.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
            case MUTIPLE_CODE://查询多个
                db = dbHelper.getReadableDatabase();
                return db.query(PersonMetaData.Person.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        }
        return null;
    }

    //用于往ContentProvider里面添加数据
    //content://com.example.contentproviderdemo.personcontentprovider/person/4
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        //验证uri是否是匹配正确的
        switch (uriMatcher.match(uri)) {
            case SINGLE_CODE:

                break;
            case MUTIPLE_CODE:
                db = dbHelper.getWritableDatabase();
                long id = db.insert(PersonMetaData.Person.TABLE_NAME, null, values);
                uri = ContentUris.withAppendedId(uri, id);
                db.close();
                break;
        }
        return uri;
    }

    //用于往ContentProvider里面删除数据

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            //content://com.example.contentproviderdemo.personcontentprovider/person/4
            case SINGLE_CODE:
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                long parseId = ContentUris.parseId(uri);  //拿出id值，相当于4
                selection = PersonMetaData.Person._ID + "=?";
                selectionArgs = new String[]{String.valueOf(parseId)};
                int row = db.delete(PersonMetaData.Person.TABLE_NAME, selection, selectionArgs);
                db.close();
                return row;
            case MUTIPLE_CODE:
                db = dbHelper.getWritableDatabase();
                row = db.delete(PersonMetaData.Person.TABLE_NAME, selection, selectionArgs);
                db.close();
                return row;
        }
        return 0;
    }

    //用于往ContentProvider里面更新数据
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            //content://com.example.contentproviderdemo.personcontentprovider/person/4
            case SINGLE_CODE:
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                long parseId = ContentUris.parseId(uri);  //拿出id值，相当于4
                selection = PersonMetaData.Person._ID + "=?";
                selectionArgs = new String[]{String.valueOf(parseId)};
                int row = db.update(PersonMetaData.Person.TABLE_NAME, values, selection, selectionArgs);
                db.close();
                return row;
            case MUTIPLE_CODE:
                db = dbHelper.getWritableDatabase();
                row = db.update(PersonMetaData.Person.TABLE_NAME, values, selection, selectionArgs);
                db.close();
                return row;
        }
        return 0;
    }
}
