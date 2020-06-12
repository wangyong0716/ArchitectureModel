package com.archi.architecture.lottery;

import java.util.Arrays;
import java.util.List;

public class SSLottery {
    private String mDateString;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mSeriesNum;
    private int[] mReds;
    private int mBlue;
    private long mPoolBonus;
    private WinPot mFirstPot;
    private WinPot mSecondPot;
    private long mSellMoney;

    public SSLottery() {
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

    private void setDateString(String dateString) {
        this.mDateString = dateString;

        String[] date = dateString.split("-");
        if (date.length == 3) {
            mYear = Integer.parseInt(date[0]);
            mMonth = Integer.parseInt(date[1]);
            mDay = Integer.parseInt(date[2]);
        }
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
