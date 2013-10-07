package com.willwang.bitwaterfall.util;

import android.util.Log;

public class Logger {

    public static boolean DEBUG = true;

    @SuppressWarnings("rawtypes")
    public static String makeLogTag(Class cls) {
        return "BitHapin_" + cls.getSimpleName();
    }

    public static int i(String tag, String msg) {
        return DEBUG ? Log.i(tag, msg) : 0;
    }

    public static int d(String tag, String msg) {
        return DEBUG ? Log.d(tag, msg) : 0;
    }

    public static int v(String tag, String msg) {
        return DEBUG ? Log.v(tag, msg) : 0;
    }

    public static int e(String tag, String msg) {
        return DEBUG ? Log.e(tag, msg) : 0;
    }

    public static int w(String tag, String msg) {
        return DEBUG ? Log.w(tag, msg) : 0;
    }

}