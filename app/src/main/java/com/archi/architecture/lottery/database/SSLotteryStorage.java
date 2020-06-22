package com.archi.architecture.lottery.database;

import android.database.Cursor;
import android.util.Log;

import com.archi.architecture.lottery.LotteryConstants;
import com.archi.architecture.lottery.SSLottery;
import com.archi.architecture.lottery.WinPot;
import com.archi.database.StorageManager;
import com.archi.database.info.IInfo;
import com.archi.database.storage.TableStorage;
import com.archi.database.utils.IOStreamUtils;

import java.util.LinkedList;
import java.util.List;

public class SSLotteryStorage extends TableStorage {
    @Override
    public String getName() {
        return "ss_lottery";
    }

    @Override
    public String getTableName() {
        return "ss_lottery";
    }

    @Override
    public List<IInfo> readDb(String selection) {
        Log.i("ss_lottery", "readDb");
        List<IInfo> infos = new LinkedList<IInfo>();
        Cursor cursor = null;
        try {
            cursor = StorageManager.getInstance().query(getTableName(), selection);
            if (null == cursor || !cursor.moveToFirst()) {
                IOStreamUtils.closeQuietly(cursor);
                return infos;
            }
            int indexDateStr = cursor.getColumnIndex(SSLotteryInfo.DBKey.COLUMN_DATE);
            int indexSeriesNum = cursor.getColumnIndex(SSLotteryInfo.DBKey.COLUMN_SERIES_NUM);
            int indexReds = cursor.getColumnIndex(SSLotteryInfo.DBKey.COLUMN_REDS);
            int indexBlue = cursor.getColumnIndex(SSLotteryInfo.DBKey.COLUMN_BLUE);
            int indexPoolBonus = cursor.getColumnIndex(SSLotteryInfo.DBKey.COLUMN_POOL_BONUS);
            int indexWin1 = cursor.getColumnIndex(SSLotteryInfo.DBKey.COLUMN_WIN_1);
            int indexWin2 = cursor.getColumnIndex(SSLotteryInfo.DBKey.COLUMN_WIN_2);
            int indexSellMoney = cursor.getColumnIndex(SSLotteryInfo.DBKey.COLUMN_SELL_MONEY);
            do {
                SSLottery ssLottery = new SSLottery();
                ssLottery.setDateString(cursor.getString(indexDateStr));
                ssLottery.setSeriesNum(cursor.getInt(indexSeriesNum));
                ssLottery.setReds(cursor.getString(indexReds));
                ssLottery.setBlue(cursor.getInt(indexBlue));
                ssLottery.setPoolBonus(cursor.getLong(indexPoolBonus));
                ssLottery.setFirstPot(new WinPot(LotteryConstants.WIN_LEVEL_FIRST, cursor.getString(indexWin1)));
                ssLottery.setSecondPot(new WinPot(LotteryConstants.WIN_LEVEL_SECOND, cursor.getString(indexWin2)));
                ssLottery.setSellMoney(cursor.getLong(indexSellMoney));
                SSLotteryInfo info = new SSLotteryInfo(ssLottery);
                infos.add(0, info);
            } while (cursor.moveToNext());
        } catch (Exception e) {
        } finally {
            IOStreamUtils.closeQuietly(cursor);
        }
        Log.i("ss_lottery", "readDb size = " + infos.size());
        return infos;
    }

    @Override
    public boolean save(List<IInfo> infoList) {
        boolean result = true;
        if (infoList != null) {
            for (IInfo info : infoList) {
                result &= save(info);
            }
        }
        return result;
    }
}
