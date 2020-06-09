package com.archi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.archi.database.provider.DatabaseProvider;
import com.archi.database.table.ITable;

import java.util.ArrayList;
import java.util.List;


public class StorageManager {
    private static volatile StorageManager sInstance;
    private DatabaseProvider mDatabaseProvider;
    private List<ITable> mTableList;
    private boolean mInited;

    private StorageManager() {}

    public void init(Context context) {
        mDatabaseProvider = new DatabaseProvider(context);
        mTableList = new ArrayList<>();
        mInited = true;
    }

    public static StorageManager getInstance() {
        if (sInstance == null) {
            synchronized (StorageManager.class) {
                if (sInstance == null) {
                    sInstance = new StorageManager();
                }
            }
        }
        return sInstance;
    }

    public List<ITable> getTableList() {
        return mTableList;
    }

    public void register(ITable table) {
        if (!mInited) {
            return;
        }
        mTableList.add(table);
    }

    public void unRegister(ITable table) {
        if (!mInited) {
            return;
        }
        mTableList.remove(table);
    }

    public Cursor query(String tableName, String selection) {
        if (!mInited) {
            return null;
        }
        return mDatabaseProvider.query(selection);
    }

    public boolean insert(String tableName, ContentValues values) {
        if (!mInited) {
            return false;
        }
        return mDatabaseProvider.insert(tableName, values);
    }

    public int delete(String tableName, String selection, String[] selectionArgs) {
        if (!mInited) {
            return -1;
        }
        return mDatabaseProvider.delete(tableName, selection, selectionArgs);
    }

    public int update(String tableName, ContentValues values, String selection, String[] selectionArgs) {
        if (!mInited) {
            return -1;
        }
        return mDatabaseProvider.update(tableName, values, selection, selectionArgs);
    }
}
