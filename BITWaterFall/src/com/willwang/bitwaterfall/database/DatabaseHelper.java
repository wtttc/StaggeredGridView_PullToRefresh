package com.willwang.bitwaterfall.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private volatile static DatabaseHelper mInstance = null;

    private static final String DATABASE_NAME = "BitHapin.db";
    private static final int DATABASE_VERSION = 1;

    // singleton pattern
    public static DatabaseHelper getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DatabaseHelper.class) {
                if (mInstance == null) {
                    mInstance = new DatabaseHelper(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user(id INTEGER PRIMARY KEY, json TEXT);");
        db.execSQL("CREATE TABLE message(id INTEGER PRIMARY KEY, fromId INTEGER, toId INTEGER, content TEXT, time date, readStatus INTEGER default 0);");
        db.execSQL("CREATE TABLE chatroom(id INTEGER PRIMARY KEY autoincrement, channelId INTEGER, userId INTEGER, content TEXT, time date, readStatus INTEGER default 0);");
        db.execSQL("CREATE TABLE friend(user1 INTEGER, user2 INTEGER)");
        db.execSQL("CREATE TABLE activity(id INTEGER PRIMARY KEY, json TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
