package com.archi.database.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.archi.database.DbCache;
import com.archi.database.DbHelper;
import com.archi.database.DbSwitch;
import com.archi.database.StorageConfig;
import com.archi.log.ArchiLog;

import static com.archi.database.StorageConfig.DEBUG;
import static com.archi.database.StorageConfig.TAG;

public class DatabaseProvider {
    private DbHelper mDbHelper; //数据库处理

    private DbCache mDbCache;

    public DatabaseProvider(Context context) {
        mDbHelper = new DbHelper(context.getApplicationContext(), StorageConfig.DB_IN_SDCARD);
        mDbCache = new DbCache(mDbHelper);
    }

    public Cursor query(String selection) {
        try {
            return mDbHelper.getDatabase().rawQuery(selection, null);
        } catch (Exception e) {
        }
        return null;
    }

    public boolean insert(String tableName, ContentValues values) {
        if (!DbSwitch.getSwitchState(tableName)) {
            if (DEBUG) {
                ArchiLog.d(TAG, "数据库禁止写入数据（" + tableName + "）");
            }
            return false;
        }
        return mDbCache.saveDataToDB(new DbCache.InfoHolder(values, tableName));
    }

    public int delete(String tableName, String selection, String[] selectionArgs) {
        int count = -1;
        try {
            count = mDbHelper.getDatabase().delete(tableName, selection, selectionArgs);
            if (DEBUG) {
                ArchiLog.d(TAG, "数据库成功删除表（" + tableName + "）: " + count + "条数据");
            }
        } catch (Exception e) {
            if (DEBUG) {
                ArchiLog.e(TAG, "数据库删除表（" + tableName + "）数据失败: " + e.toString());
            }
            return count;
        }
        return count;
    }

    public int update(String tableName, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;
        try {
            count = mDbHelper.getDatabase().update(tableName, values, selection, selectionArgs);
        } catch (Exception e) {
            if (DEBUG) {
                ArchiLog.e(TAG, "数据库更新失败: " + e.toString());
            }
            return count;
        }
        return count;
    }
}
