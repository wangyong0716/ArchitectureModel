package com.archi.architecture.lottery;

import android.text.TextUtils;

import java.util.Arrays;
import java.util.List;

public class SSLottery {
    private String mDateString;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mSeriesNum;
    private int[] mReds = new int[LotteryConstants.SS_RED_SIZE];
    private int mBlue;
    private long mPoolBonus;
    private WinPot mFirstPot;
    private WinPot mSecondPot;
    private long mSellMoney;
    private boolean mIsPlan;
    private int mLevel;
    private long mCount;

    public SSLottery() {
    }

    public SSLottery(int[] reds, int blue) {
        mReds = reds;
        mBlue = blue;
        mIsPlan = true;
    }

    public SSLottery(List<String> infoData) {
        mReds = new int[LotteryConstants.SS_RED_SIZE];
        if (infoData.size() == 15) {
            this.mSeriesNum = Integer.parseInt(infoData.get(0));
            this.mReds[0] = Integer.parseInt(infoData.get(1));
            this.mReds[1] = Integer.parseInt(infoData.get(2));
            this.mReds[2] = Integer.parseInt(infoData.get(3));
            this.mReds[3] = Integer.parseInt(infoData.get(4));
            this.mReds[4] = Integer.parseInt(infoData.get(5));
            this.mReds[5] = Integer.parseInt(infoData.get(6));
            this.mBlue = Integer.parseInt(infoData.get(7));
            this.mPoolBonus = Long.parseLong(infoData.get(8));
            mFirstPot = new WinPot(LotteryConstants.WIN_LEVEL_FIRST, Integer.parseInt(infoData.get(9)), Integer.parseInt(infoData.get(10)));
            mSecondPot = new WinPot(LotteryConstants.WIN_LEVEL_SECOND, Integer.parseInt(infoData.get(11)), Integer.parseInt(infoData.get(12)));
            this.mSellMoney = Long.parseLong(infoData.get(13));
            setDateString(infoData.get(14));
        }
    }

    public void setDate(int year, int month, int day) {
        mYear = year;
        mMonth = month;
        mDay = day;
        mDateString = mYear + "-" + mMonth + "-" + mDay;
    }

    public void setDateString(String dateString) {
        this.mDateString = dateString;

        String[] date = dateString.split("-");
        if (date.length == 3) {
            mYear = Integer.parseInt(date[0]);
            mMonth = Integer.parseInt(date[1]);
            mDay = Integer.parseInt(date[2]);
        }
    }

    public String getDateString() {
        return mDateString;
    }

    public int getYear() {
        return mYear;
    }

    public int getMonth() {
        return mMonth;
    }

    public int getDay() {
        return mDay;
    }

    public int getSeriesNum() {
        return mSeriesNum;
    }

    public void setSeriesNum(int seriesNum) {
        mSeriesNum = seriesNum;
    }

    public String getRedsStr() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mReds.length; i++) {
            stringBuilder.append(mReds[i]);
            if (i != mReds.length - 1) {
                stringBuilder.append(LotteryConstants.SEPARATE);
            }
        }
        return stringBuilder.toString();
    }

    public int[] getReds() {
        return mReds;
    }

    public void setReds(String reds) {
        if (TextUtils.isEmpty(reds)) {
            return;
        }
        String[] tmp = reds.split(LotteryConstants.SEPARATE);
        if (tmp.length != LotteryConstants.SS_RED_SIZE) {
            return;
        }
        for (int i = 0; i < LotteryConstants.SS_RED_SIZE; i++) {
            mReds[i] = Integer.parseInt(tmp[i]);
        }
    }

    public int getBlue() {
        return mBlue;
    }

    public void setBlue(int blue) {
        mBlue = blue;
    }

    public long getPoolBonus() {
        return mPoolBonus;
    }

    public void setPoolBonus(long poolBonus) {
        mPoolBonus = poolBonus;
    }

    public WinPot getFirstPot() {
        return mFirstPot;
    }

    public void setFirstPot(WinPot firstPot) {
        mFirstPot = firstPot;
    }

    public WinPot getSecondPot() {
        return mSecondPot;
    }

    public void setSecondPot(WinPot secondPot) {
        mSecondPot = secondPot;
    }

    public long getSellMoney() {
        return mSellMoney;
    }

    public void setSellMoney(long sellMoney) {
        mSellMoney = sellMoney;
    }

    public boolean isDate(int year) {
        return mYear == year;
    }

    public boolean isDate(int year, int month) {
        return mYear == year && mMonth == month;
    }

    public boolean isDate(int year, int month, int day) {
        return mYear == year && mMonth == month && mDay == day;
    }

    public void setPlan(boolean plan) {
        mIsPlan = plan;
    }

    public void setLevel(int level) {
        mLevel = level;
    }

    public void setCount(long count) {
        mCount = count;
    }

    public long getCount() {
        return mCount;
    }

    @Override
    public String toString() {
        return "SSLottery{" +
                "mDateString='" + mDateString + '\'' +
                ", mYear=" + mYear +
                ", mMonth=" + mMonth +
                ", mDay=" + mDay +
                ", mSeriesNum=" + mSeriesNum +
                ", mReds=" + Arrays.toString(mReds) +
                ", mBlue=" + mBlue +
                ", mPoolBonus=" + mPoolBonus +
                ", mFirstPot=" + mFirstPot +
                ", mSecondPot=" + mSecondPot +
                ", mSellMoney=" + mSellMoney +
                '}';
    }
}
