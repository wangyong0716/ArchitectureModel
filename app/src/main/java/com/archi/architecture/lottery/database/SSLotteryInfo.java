package com.archi.architecture.lottery.database;

import android.content.ContentValues;

import com.archi.architecture.lottery.SSLottery;
import com.archi.database.info.BaseInfo;

public class SSLotteryInfo extends BaseInfo {

    public static class DBKey {
        public static final String COLUMN_DATE = "date_str";
        public static final String COLUMN_SERIES_NUM = "series_num";
        public static final String COLUMN_REDS = "reds";
        public static final String COLUMN_BLUE = "blue";
        public static final String COLUMN_POOL_BONUS = "pool_bonus";
        public static final String COLUMN_WIN_1 = "win_1";
        public static final String COLUMN_WIN_2 = "win_2";
        public static final String COLUMN_SELL_MONEY = "sell_money";
    }

    private SSLottery mSSLottery;

    public SSLotteryInfo(SSLottery SSLottery) {
        mSSLottery = SSLottery;
    }

    public SSLottery getSSLottery() {
        return mSSLottery;
    }

    @Override
    public ContentValues toContentValues() {
        if (mSSLottery == null) {
            return null;
        }
        ContentValues values = new ContentValues();
        try {
            values.put(DBKey.COLUMN_DATE, mSSLottery.getDateString());
            values.put(DBKey.COLUMN_SERIES_NUM, mSSLottery.getSeriesNum());
            values.put(DBKey.COLUMN_REDS, mSSLottery.getRedsStr());
            values.put(DBKey.COLUMN_BLUE, mSSLottery.getBlue());
            values.put(DBKey.COLUMN_POOL_BONUS, mSSLottery.getPoolBonus());
            values.put(DBKey.COLUMN_WIN_1, mSSLottery.getFirstPot().getWinData());
            values.put(DBKey.COLUMN_WIN_2, mSSLottery.getSecondPot().getWinData());
            values.put(DBKey.COLUMN_SELL_MONEY, mSSLottery.getSellMoney());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }
}
