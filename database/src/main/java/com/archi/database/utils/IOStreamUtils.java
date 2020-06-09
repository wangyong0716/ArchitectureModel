package com.archi.database.utils;

import android.database.Cursor;

public class IOStreamUtils {
    public static void closeQuietly(Cursor c) {
        if (null == c) {
            return;
        }
        c.close();
    }
}
