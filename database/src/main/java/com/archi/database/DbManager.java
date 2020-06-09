package com.archi.database;

import android.content.Context;
import android.text.TextUtils;

import com.archi.database.utils.FileUtils;

public class DbManager {
    private static volatile DbManager sInstance;

    private DbConfig mDbConfig;

    private DbManager() {}

    public static DbManager getInstance(){
        if (sInstance == null) {
           synchronized (DbManager.class) {
               if (sInstance == null) {
                   sInstance = new DbManager();
               }
           }
        }
        return sInstance;
    }

    public DbConfig getDbConfig() {
        return mDbConfig;
    }

    public void setDbConfig(DbConfig mDbConfig) {
        this.mDbConfig = mDbConfig;
    }

    public String getBasePath(Context context) {
        if (TextUtils.isEmpty(FileUtils.getDataDir(context))) {
            return "";
        }
        return FileUtils.getDataDir(context) + StorageConfig.BASE_DIR_PATH;
    }
}
