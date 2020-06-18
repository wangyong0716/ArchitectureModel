package com.archi.architecture.lottery;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;

public class LotteryController {
    private static final String SP_NAME = "sp_latest_date";
    private static final String KEY_LATEST_DATE = "latest_date";
    private static volatile LotteryController sInstance;

    private SharedPreferences mSharedPreferences;

    private LotteryController() {
    }

    public static LotteryController getInstance() {
        if (sInstance == null) {
            synchronized (LotteryController.class) {
                if (sInstance == null) {
                    sInstance = new LotteryController();
                }
            }
        }
        return sInstance;
    }

    public void init(Context context) {
        mSharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public void updateLatestDate(String date) {
        if (mSharedPreferences != null) {
            mSharedPreferences.edit().putString(KEY_LATEST_DATE, date).apply();
        }
    }

    public String getLatestDate() {
        return mSharedPreferences.getString(KEY_LATEST_DATE, null);
    }

    public boolean needUpdate() {
        String latestDate = getLatestDate();
        if (latestDate == null) {
            return true;
        }
        String[] latest = latestDate.split("-");
        if (latest.length != 3) {
            return true;
        }

        Calendar latestDay = Calendar.getInstance();
        latestDay.set(Integer.parseInt(latest[0]), Integer.parseInt(latest[1]), Integer.parseInt(latest[2]));
        return Calendar.getInstance().getTimeInMillis() - latestDay.getTimeInMillis() > LotteryConstants.TWO_DAY_MILLS;
    }

}
