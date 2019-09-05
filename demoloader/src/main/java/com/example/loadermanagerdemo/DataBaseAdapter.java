package com.example.loadermanagerdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 若兰 on 2016/3/31.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */

public class DataBaseAdapter {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public DataBaseAdapter(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    /**
     * 保存的方法
     *
     * @param person
     */
    public void save(Person person) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PersonMetaData.Person.NAME, person.getName());
        values.put(PersonMetaData.Person.AGE, person.getAge());
        db.insert(PersonMetaData.Person.TABLE_NAME, null, values);
        db.close();
    }

    /**
     * 删除方法
     *
     * @param id
     */
    public void delete(int id) {
        db.delete(PersonMetaData.Person.TABLE_NAME, PersonMetaData.Person._ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    /**
     * 更新的方法
     *
     * @param person
     */
    public void update(Person person) {
        ContentValues values = new ContentValues();
        values.put(PersonMetaData.Person.NAME, person.getName());
        values.put(PersonMetaData.Person.AGE, person.getAge());
        String where = PersonMetaData.Person._ID + "=?";
        String[] args = {String.valueOf(person.getId())};
        db.update(PersonMetaData.Person.TABLE_NAME, values, where, args);
        db.close();
    }

    /**
     * 查询所有
     *
     * @return
     */
    public List<Person> findAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(PersonMetaData.Person.TABLE_NAME, null, null, null, null, null, null);
        List<Person> persons = new ArrayList<>();
        Person person = null;
        while (cursor.moveToNext()) {
            person = new Person();
            person.setId(cursor.getColumnIndex(PersonMetaData.Person._ID));
            person.setAge(cursor.getColumnIndex(PersonMetaData.Person.AGE));
            person.setName(String.valueOf(cursor.getColumnIndex(PersonMetaData.Person.NAME)));
            persons.add(person);
        }
        //要注意关闭
        cursor.close();
        db.close();
        return persons;
    }

    public Cursor list(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(PersonMetaData.Person.TABLE_NAME,null,null,null,null,null,null);
        return cursor;
    }

    /**
     * 查询单个
     *
     * @return
     */
    public Person findById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(PersonMetaData.Person.TABLE_NAME, null, PersonMetaData.Person._ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        Person person = null;
        if (cursor.moveToNext()) {
            person = new Person();
            person.setId(cursor.getColumnIndex(PersonMetaData.Person._ID));
            person.setAge(cursor.getColumnIndex(PersonMetaData.Person.AGE));
            person.setName(String.valueOf(cursor.getColumnIndex(PersonMetaData.Person.NAME)));

        }
        cursor.close();
        db.close();
        return person;
    }

    public static class DatabaseHelper extends SQLiteOpenHelper {

        /**
         * 创建person表
         */
        private static final String CREATE_TABLE = "CREATE TABLE person(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT,age INT)";

        /**
         * 删除person表
         */
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS person";

        private static final String DB_NAME = "hello.db";
        private static final int VERSION = 1;

        public DatabaseHelper(Context context) {
            super(context, DB_NAME, null, VERSION);
        }


        /**
         * 创建数据库
         *
         * @param db
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        /**
         * 更新数据库
         *
         * @param db
         * @param oldVersion
         * @param newVersion
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_TABLE);
            db.execSQL(CREATE_TABLE);
        }
    }
}
