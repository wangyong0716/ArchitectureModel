package com.archi.database.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class FileUtils {
    public static String getSDPath() {
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (!sdCardExist) {
            return "";
        }
        File sdDir = Environment.getExternalStorageDirectory();//获取sd卡根目录
        return sdDir.toString();
    }

    public static String getDataDir(Context context) {
        File dataDir = context.getExternalFilesDir(null);
        if (!dataDir.exists()) {
            return "";
        }
        return dataDir.getAbsolutePath();
    }
}
