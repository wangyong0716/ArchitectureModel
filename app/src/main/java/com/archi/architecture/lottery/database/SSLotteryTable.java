package com.archi.architecture.lottery.database;

import android.text.TextUtils;

import com.archi.database.DbHelper;
import com.archi.database.info.BaseInfo;
import com.archi.database.table.ITable;

public class SSLotteryTable implements ITable {
    @Override
    public String getCreateSql() {
        return TextUtils.concat(
                DbHelper.CREATE_TABLE_PREFIX + getTableName(),
                "(", BaseInfo.KEY_ID_RECORD, " INTEGER PRIMARY KEY AUTOINCREMENT,",
                BaseInfo.KEY_TIME_RECORD, DbHelper.DATA_TYPE_INTEGER,
                SSLotteryInfo.DBKey.COLUMN_DATE, DbHelper.DATA_TYPE_TEXT,
                SSLotteryInfo.DBKey.COLUMN_SERIES_NUM, DbHelper.DATA_TYPE_INTEGER,
                SSLotteryInfo.DBKey.COLUMN_REDS, DbHelper.DATA_TYPE_TEXT,
                SSLotteryInfo.DBKey.COLUMN_BLUE, DbHelper.DATA_TYPE_INTEGER,
                SSLotteryInfo.DBKey.COLUMN_POOL_BONUS, DbHelper.DATA_TYPE_INTEGER,
                SSLotteryInfo.DBKey.COLUMN_WIN_1, DbHelper.DATA_TYPE_TEXT,
                SSLotteryInfo.DBKey.COLUMN_WIN_2,DbHelper.DATA_TYPE_TEXT,
                SSLotteryInfo.DBKey.COLUMN_SELL_MONEY, DbHelper.DATA_TYPE_INTEGER_SUF
        ).toString();
    }

    @Override
    public String getTableName() {
        return "ss_lottery";
    }
}
