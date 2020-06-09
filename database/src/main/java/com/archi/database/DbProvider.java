package com.archi.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.util.SparseArray;

import com.archi.database.table.ITable;
import com.archi.log.ArchiLog;

import static com.archi.database.StorageConfig.DEBUG;
import static com.archi.database.StorageConfig.TAG;

public class DbProvider extends ContentProvider {
    private static final String SUB_TAG = "ApmProvider";

    private DbHelper mDbHelper; //数据库处理
    private final UriMatcher mTableMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private SparseArray<ITable> mTableMap;

    private DbCache mDbCache;

    @Override
    public boolean onCreate() {
        initTable();
        mDbHelper = new DbHelper(getContext(), StorageConfig.DB_IN_SDCARD);
        mDbCache = new DbCache(mDbHelper);
        return true;
    }

    /**
     * 初始化数据库查询表
     */
    private void initTable() {
        ArchiLog.i(TAG, "authority = " + DbUtil.getAuthority(getContext().getPackageName()));
        mTableMap = new SparseArray<ITable>();
        int num = StaticTask.sTableNameList.length;
        for (int i = 0; i < num; i++) {
            mTableMap.append(i, StaticTask.sTableList[i]);
            DbHelper.addTable(StaticTask.sTableList[i]);
            mTableMatcher.addURI(DbUtil.getAuthority(getContext().getPackageName()), StaticTask.sTableNameList[i], i);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        ITable table = mTableMap.get(mTableMatcher.match(uri));
        if (null == table) return null;
        try {
            Cursor cursor = mDbHelper.getDatabase().rawQuery(selection, null);
            if (cursor != null) {
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
            }
            return cursor;
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        ITable table = mTableMap.get(mTableMatcher.match(uri));
        if (null == values || null == table) return null;
        if (!DbSwitch.getSwitchState(table.getTableName())) {
            if (DEBUG) {
                ArchiLog.d(TAG, "数据库禁止写入数据（" + table.getTableName() + "）");
            }
            return null;
        }
        boolean ret = mDbCache.saveDataToDB(new DbCache.InfoHolder(values, table.getTableName()));
        return ret ? uri : null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        ITable table = mTableMap.get(mTableMatcher.match(uri));
        if (null == table) return -1;
        int count = -1;
        try {
            count = mDbHelper.getDatabase().delete(table.getTableName(), selection, selectionArgs);
            if (DEBUG) {
                ArchiLog.d(TAG, "数据库成功删除表（" + table.getTableName() + "）: " + count + "条数据");
            }
        } catch (Exception e) {
            if (DEBUG) {
                ArchiLog.e(TAG, "数据库删除表（" + table.getTableName() + "）数据失败: " + e.toString());
            }
            return count;
        }
        notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        ITable table = mTableMap.get(mTableMatcher.match(uri));
        if (null == values || null == table) return 0;
        int count = 0;
        try {
            count = mDbHelper.getDatabase().update(table.getTableName(), values, selection, selectionArgs);
        } catch (Exception e) {
            if (DEBUG) {
                ArchiLog.e(TAG, "数据库更新失败: " + e.toString());
            }
            return count;
        }
        notifyChange(uri, null);
        return count;
    }

    private void notifyChange(Uri uri, ContentObserver observer) {
        try {
            getContext().getContentResolver().notifyChange(uri, observer);
        } catch (Exception e) {
            if (DEBUG) {
                ArchiLog.d(TAG, "notifyChange ex : " + Log.getStackTraceString(e));
            }
        }
    }
}