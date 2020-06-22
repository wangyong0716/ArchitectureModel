package com.archi.architecture.lottery.plan;

import android.util.Log;

import com.archi.architecture.lottery.LotteryConstants;
import com.archi.architecture.lottery.SSLottery;

public class LRUSSPlan extends BaseSSPlan {
    private static final String TAG = "LRUSSPlan";
    private int[] lastOccurs = new int[16];
    private int expectedBlue;
    private long currentCost;
    private int max;

    @Override
    protected int[] getBuyReds() {
        return randomReds();
    }

    @Override
    protected int getBuyBlue() {
        int index = 0;
        max = 0;
        for (int i = 0; i < lastOccurs.length; i++) {
            if (lastOccurs[i] > max) {
                max = lastOccurs[i];
                index = i;
            }
        }
        Log.w("QQQ", "getBuyBlue max = " + max + ", blue = " + (index + 1));
        expectedBlue = index + 1;
        return index + 1;
    }

    @Override
    protected long getBuyCount() {
        if (max < 32) {
            return 0;
        }
        long count = (currentCost + 2) / 3;
        count = count >= 1 ? count : 1;
        count = count >= 50 ? 50 : count;
        Log.w("QQQ", "getBuyCount count = " + count + ", currentCost = " + currentCost);
        return count;
    }

    @Override
    protected void onStarted() {
        int found = 0;
        int size = mActualLotteries.size();
        for (int i = 1; i <= size; i++) {
            int blue = mActualLotteries.get(size - i).getBlue();
            if (lastOccurs[blue - 1] == 0) {
                lastOccurs[blue - 1] = i;
                found++;
            }
            if (found == 16) {
                return;
            }
        }
    }

    @Override
    protected void onLottery(SSLottery lottery) {
        super.onLottery(lottery);
        Log.w("QQQ", "onLottery blue = " + lottery.getBlue());
        for (int i = 0; i < lastOccurs.length; i++) {
            if (i == lottery.getBlue() - 1) {
                lastOccurs[i] = 1;
            } else {
                lastOccurs[i]++;
            }
        }

        if (lottery.getBlue() == expectedBlue) {
            currentCost = 0;
            Log.w("QQQ", "onLottery currentCost = " + currentCost);
        }
    }

    @Override
    protected void onBought(long count) {
        currentCost += LotteryConstants.SS_PRICE * count;
        Log.w("QQQ", "onBought count = " + count + ", currentCost = " + + currentCost);
    }

    @Override
    public void activate() {
        do {
            if (!buy()) {
                break;
            }
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (true);
    }
}
