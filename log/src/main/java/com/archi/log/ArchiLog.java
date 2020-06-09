package com.archi.log;

import android.util.Log;

public class ArchiLog {

    public static void v(String tag, String content) {
        Log.v(tag, content);
    }

    public static void d(String tag, String content) {
        Log.d(tag, content);
    }

    public static void i(String tag, String content) {
        Log.i(tag, content);
    }

    public static void w(String tag, String content) {
        Log.w(tag, content);
    }

    public static void e(String tag, String content) {
        Log.e(tag, content);
    }

    public static void v(String tag, String subTag, String content) {
        Log.v(tag, content);
    }

    public static void d(String tag, String subTag, String content) {
        Log.d(tag, content);
    }

    public static void i(String tag, String subTag, String content) {
        Log.i(tag, content);
    }

    public static void w(String tag, String subTag, String content) {
        Log.w(tag, content);
    }

    public static void e(String tag, String subTag, String content) {
        Log.e(tag, content);
    }
}
