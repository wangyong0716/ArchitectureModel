package com.archi.architecture.lottery;

import android.text.TextUtils;

public class WinPot {
    private int mLevel;
    private int mCount;
    private int mMoney;

    public WinPot(int level, String pot) {
        mLevel = level;
        if (!TextUtils.isEmpty(pot)) {
            String[] tmp = pot.split(LotteryConstants.SEPARATE);
            if (tmp.length == 2) {
                mCount = Integer.parseInt(tmp[0]);
                mMoney = Integer.parseInt(tmp[1]);
            }
        }
    }

    public WinPot(int level, int count, int money) {
        mLevel = level;
        mCount = count;
        mMoney = money;
    }

    public int getLevel() {
        return mLevel;
    }

    public void setLevel(int level) {
        mLevel = level;
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        mCount = count;
    }

    public int getMoney() {
        return mMoney;
    }

    public void setMoney(int money) {
        mMoney = money;
    }

    public String getWinData() {
        return mCount + LotteryConstants.SEPARATE + mMoney;
    }

    @Override
    public String toString() {
        return "WinPot{" +
                "mLevel=" + mLevel +
                ", mCount=" + mCount +
                ", mMoney=" + mMoney +
                '}';
    }
}
