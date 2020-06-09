package com.archi.database.common;

import android.text.TextUtils;

import com.archi.database.DbHelper;
import com.archi.database.info.BaseInfo;
import com.archi.database.table.ITable;

public class CommonTable implements ITable {

    @Override
    public String getCreateSql() {
        return TextUtils.concat(
                DbHelper.CREATE_TABLE_PREFIX + getTableName(),
                "(", BaseInfo.KEY_ID_RECORD, " INTEGER PRIMARY KEY AUTOINCREMENT,",
                BaseInfo.KEY_TIME_RECORD, DbHelper.DATA_TYPE_INTEGER,
                CommonInfo.DBKey.TEXT, DbHelper.DATA_TYPE_TEXT_SUF
        ).toString();
    }

    @Override
    public String getTableName() {
        return "common";
    }
}
