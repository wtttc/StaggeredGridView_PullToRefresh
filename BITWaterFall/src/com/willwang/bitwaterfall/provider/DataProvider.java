package com.willwang.bitwaterfall.provider;

import android.content.Context;

public class DataProvider {

    private volatile static DataProvider mInstance = null;

    private final SQLiteDataProvider sqlite;
    private final OAuthDataProvider oauth;

    // singleton pattern
    public static DataProvider getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DataProvider.class) {
                if (mInstance == null) {
                    mInstance = new DataProvider(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    private DataProvider(Context context) {
        this.sqlite = new SQLiteDataProvider(context);
        this.oauth = new OAuthDataProvider(context);
    }

    public SQLiteDataProvider getSQLiteDataProvider() {
        return sqlite;
    }

    public OAuthDataProvider getOAuthDataProvider() {
        return oauth;
    }


}
