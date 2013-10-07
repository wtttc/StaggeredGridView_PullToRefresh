package com.willwang.bitwaterfall.database;

import android.content.Context;




public class ActivityDB {

    //private static final String TAG = Logger.makeLogTag(ActivityDB.class);
    private volatile static ActivityDB mInstance = null;

    private DatabaseHelper mHelper = null;

    // singleton pattern
    public static ActivityDB getInstance(Context context) {
        if (mInstance == null) {
            synchronized (ActivityDB.class) {
                if (mInstance == null) {
                    mInstance = new ActivityDB(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    private ActivityDB(Context context) {
        this.mHelper = DatabaseHelper.getInstance(context);
    }

    public void close() {
        if (mHelper != null) {
            mHelper.close();
        }
    }

    /**
     * 以下进行数据库操作
     */



}
