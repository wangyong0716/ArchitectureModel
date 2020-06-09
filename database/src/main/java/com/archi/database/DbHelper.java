package com.archi.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.archi.database.table.ITable;
import com.archi.database.utils.FileUtils;
import com.archi.log.ArchiLog;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.archi.database.StorageConfig.DEBUG;
import static com.archi.database.StorageConfig.TAG;

public class DbHelper extends SQLiteOpenHelper {
    private static final String SUB_TAG = "DbHelper";
    public static final String DATA_TYPE_INTEGER = " INTEGER,";
    public static final String DATA_TYPE_REAL = " REAL,";
    public static final String DATA_TYPE_INTEGER_SUF = " INTEGER);";
    public static final String DATA_TYPE_TEXT = " TEXT,";
    public static final String DATA_TYPE_TEXT_SUF = " TEXT);";
    public static final String CREATE_TABLE_PREFIX = "CREATE TABLE IF NOT EXISTS ";

    private Context appContext;
    private SQLiteDatabase mDb;
    private boolean isInSdcard;
    private String mDbPath;


    public DbHelper(Context context, boolean isInSdcard) {
        super(context, StorageConfig.DB_NAME, null, StorageConfig.DB_VERSION);
        this.isInSdcard = isInSdcard;

        init(context);

        mDbPath = getDbPath();
    }

    private void init(Context context) {
        appContext = context.getApplicationContext();
    }

    private String getDbPath() {
        String path = "";
        if (!isInSdcard) {
            String filesDir = appContext.getDatabasePath(StorageConfig.DB_NAME).getAbsolutePath();
            path = filesDir;
            ArchiLog.i(TAG, "path1 = " + path);
        } else {
            String sdDir = FileUtils.getDataDir(appContext) + StorageConfig.BASE_DIR_PATH;
            path = sdDir + StorageConfig.DB_NAME;
            ArchiLog.i(TAG, "path2 = " + path);
        }
        return path;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        List<ITable> tableList = StorageManager.getInstance().getTableList();
        ArchiLog.d(SUB_TAG, "创建数据库 onCreate: " + (tableList == null ? null : tableList.size()));

        if (null == tableList) return;

        for (ITable table : tableList) {
            db.execSQL(table.getCreateSql());
            ArchiLog.d(SUB_TAG, table.getTableName() + " :" + table.getCreateSql());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        ArchiLog.d(SUB_TAG, "升级数据库:" + newVersion);
        deleteDBByName(StorageConfig.DB_NAME);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        ArchiLog.d(SUB_TAG, "数据库降级:" + newVersion);
        deleteDBByName(StorageConfig.DB_NAME);
//        onCreate(db);
    }

    public SQLiteDatabase getDatabase() {
        if (mDb == null) {
            try {
                if (isInSdcard) {
                    newDbFile(mDbPath);
                    mDb = SQLiteDatabase.openOrCreateDatabase(mDbPath, null);
                    onCreate(mDb);
                } else {
                    mDb = getWritableDatabase();
                }
            } catch (Exception e) {
                if (DEBUG) {
                    ArchiLog.e(SUB_TAG, "getDatabase ex : " + Log.getStackTraceString(e));
                }
            }
        }
        return mDb;
    }

    private void newDbFile(String path) {
        if (DEBUG) {
            ArchiLog.d(TAG, SUB_TAG, "db path = " + path);
        }
        if (TextUtils.isEmpty(path)) {
            return;
        }

        ArchiLog.i(TAG, "path = " + path);
        try {
            File file = new File(path);
            if (!file.getParentFile().exists()) {
                boolean result = file.getParentFile().mkdirs();
                ArchiLog.i(TAG, "mkdirs result = " + result);
            }

            if (!file.exists()) {
                file.createNewFile();
            }
            ArchiLog.i(TAG, "createNewFile");
        } catch (IOException e) {
            if (DEBUG) {
                ArchiLog.d(TAG, SUB_TAG, "newDbFile ioException : " + Log.getStackTraceString(e));
            }
        }
    }

    public boolean deleteDBByName(String DBName) {
        try {
            if (isInSdcard) {
                File f = new File(mDbPath);
                if (f.exists() && f.isFile()) {
                    f.delete();
                }
            } else {
                appContext.deleteDatabase(DBName);
            }
            ArchiLog.d(SUB_TAG, "删除数据库:" + DBName);
        } catch (Exception e) {
            ArchiLog.d(SUB_TAG, "清理数据库失败: " + e.toString());
        }
        return true;
    }
}
