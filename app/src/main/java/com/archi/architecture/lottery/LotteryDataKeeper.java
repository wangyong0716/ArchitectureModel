package com.archi.architecture.lottery;

import com.archi.log.ArchiLog;

import java.util.ArrayList;
import java.util.List;

public class LotteryDataKeeper {
    private static volatile LotteryDataKeeper sInstance;
    private List<SSLottery> mLotteries = new ArrayList<>();
    private List<Integer> mDisplayIndex = new ArrayList<>();
    private int mTotalCount;

    private LotteryDataKeeper() {
    }

    public static LotteryDataKeeper getInstance() {
        if (sInstance == null) {
            synchronized (LotteryDataKeeper.class) {
                if (sInstance == null) {
                    sInstance = new LotteryDataKeeper();
                }
            }
        }
        return sInstance;
    }

    public void update(List<SSLottery> list) {
        mLotteries.clear();
        mLotteries.addAll(list);
        mTotalCount = mLotteries.size();
        for (int i = 0; i < mTotalCount; i++) {
            mDisplayIndex.add(i);
        }
    }

    public void setDuration(int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {
        if (startYear * 10000 + startMonth * 100 + startDay > endYear * 10000 + endMonth * 100 + endDay) {
            throw new IllegalStateException("start date can't be later than end date");
        }
        mDisplayIndex.clear();
        boolean find = false;
        for (int i = 0; i < mTotalCount; i++) {
            if (mLotteries.get(i).isDate(endYear, endMonth, endDay)) {
                mDisplayIndex.add(i);
                return;
            }
            if (find) {
                mDisplayIndex.add(i);
            }
            if (mLotteries.get(i).isDate(startYear, startMonth, startDay)) {
                find = true;
                mDisplayIndex.add(i);
            }
        }
    }

    public SSLottery find(int year, int month, int day) {
        for (int i = 0; i < mTotalCount; i++) {
            if (mLotteries.get(i).isDate(year, month, day)) {
                return mLotteries.get(i);
            }
        }
        return null;
    }

    public List<SSLottery> find(int year, int month) {
        List<SSLottery> lotteries = new ArrayList<>();
        boolean found = false;
        for (int i = 0; i < mTotalCount; i++) {
            if (mLotteries.get(i).isDate(year, month)) {
                lotteries.add(mLotteries.get(i));
                found = true;
            } else if (found) {
                return lotteries;
            }
        }
        return lotteries;
    }

    public List<SSLottery> find(int year) {
        List<SSLottery> lotteries = new ArrayList<>();
        boolean found = false;
        for (int i = 0; i < mTotalCount; i++) {
            if (mLotteries.get(i).isDate(year)) {
                lotteries.add(mLotteries.get(i));
                found = true;
            } else if (found) {
                return lotteries;
            }
        }
        return lotteries;
    }

    /**
     * 找到第一个比给定日期大的
     * @param year
     * @param month
     * @param day
     * @return
     */
    public SSLottery findNext(int year, int month, int day) {
        for (int i = 0; i < mTotalCount; i++) {
            if (mLotteries.get(i).getYear() > year
                    || mLotteries.get(i).getYear() == year && mLotteries.get(i).getMonth() > month
                    || mLotteries.get(i).getYear() == year && mLotteries.get(i).getMonth() == month && mLotteries.get(i).getDay() > day) {
                return mLotteries.get(i);
            }
        }
        return null;
    }

    public SSLottery findNext(int seriesNum) {
        for (int i = 0; i < mTotalCount - 1; i++) {
            if (mLotteries.get(i).getSeriesNum() == seriesNum) {
                return mLotteries.get(i + 1);
            }
        }
        return null;
    }

    public List<SSLottery> getBefore(int year, int month, int day) {
        List<SSLottery> lotteries = new ArrayList<>();
        for (int i = 0; i < mTotalCount; i++) {
            if (mLotteries.get(i).getYear() < year
                    || (mLotteries.get(i).getYear() == year && mLotteries.get(i).getMonth() < month)
                    || (mLotteries.get(i).getYear() == year && mLotteries.get(i).getMonth() == month
                    && mLotteries.get(i).getDay() <= day)) {
                lotteries.add(mLotteries.get(i));
            } else {
                return lotteries;
            }
        }
        return lotteries;
    }
}
