package com.archi.architecture.lottery.plan;

import com.archi.architecture.lottery.LotteryConstants;
import com.archi.architecture.lottery.LotteryDataKeeper;
import com.archi.architecture.lottery.SSLottery;
import com.archi.log.ArchiLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class BaseSSPlan {
    private static final String TAG = "BaseSSPlan";
    protected List<SSLottery> mActualLotteries = new ArrayList<>();
    private List<SSLotteryRecord> mBuyLotteries = new ArrayList<>();
    protected long mCost;
    protected long mBonus;
    private int mCurYear;
    private int mCurMonth;
    private int mCurDay;

    protected abstract int[] getBuyReds();

    protected abstract int getBuyBlue();

    protected abstract long getBuyCount();

    public abstract void activate();

    protected void onStarted() {}

    /**
     * 开奖
     */
    protected void onLottery(SSLottery lottery) {
        mCurYear = lottery.getYear();
        mCurMonth = lottery.getMonth();
        mCurDay = lottery.getDay();
        mActualLotteries.add(lottery);
    }

    /**
     * 购票成功
     * @param count
     */
    protected void onBought(long count) {}

    protected boolean buy() {
        SSLottery nextLottery = findNext();
        if (nextLottery == null) {
            return false;
        }
        List<SSLottery> buyLotteries = buyLotteries();
        onLottery(nextLottery);
        for (SSLottery lottery : buyLotteries) {
            lottery.setDate(mCurYear, mCurMonth, mCurDay);
        }
        judge(buyLotteries, nextLottery);
        ArchiLog.e(TAG, "date = " + nextLottery.getDateString()
                + ", buy = [count = " + buyLotteries.size()
                + "], target = [" + nextLottery.getRedsStr() + " | " + nextLottery.getBlue()
                + "], cost = " + mCost + ", bonus " + mBonus);
        return true;
    }

    /**
     * 买票
     *
     * @return
     */
    protected List<SSLottery> buyLotteries() {
        int blue = getBuyBlue();
        long buyCount = getBuyCount();
        List<SSLottery> lotteries = new ArrayList<>();
        for (int i=0;i<buyCount;i++) {
            lotteries.add(new SSLottery(getBuyReds(), blue));
        }
        mCost += LotteryConstants.SS_PRICE * buyCount;
        onBought(buyCount);
        return lotteries;
    }

    protected SSLottery findNext() {
        return LotteryDataKeeper.getInstance().findNext(mCurYear, mCurMonth, mCurDay);
    }

    /**
     * 设置起始日期
     */
    public void setStart(int year, int month, int day) {
        List<SSLottery> lotteries = LotteryDataKeeper.getInstance().getBefore(year, month, day);
        mActualLotteries.clear();
        mActualLotteries.addAll(lotteries);
        mCurYear = year;
        mCurMonth = month;
        mCurDay = day;
        onStarted();
    }

    protected void judge(List<SSLottery> buyLotteries, SSLottery actualLottery) {
        SSLotteryRecord record = new SSLotteryRecord();
        for (SSLottery lottery : buyLotteries) {
            mBonus += judgeLottery(lottery, actualLottery);
            record.addLottery(lottery);
        }
        record.setCost(mCost);
        record.setBonus(mBonus);
        mBuyLotteries.add(record);
    }

    private long judgeLottery(SSLottery buyLottery, SSLottery actualLottery) {
        int redHit = 0;
        boolean blueHit;
        int[] buyReds = buyLottery.getReds();
        int[] actualReds = actualLottery.getReds();
        for (int i = 0; i < buyReds.length; i++) {
            for (int j = 0; j < actualReds.length; j++) {
                if (buyReds[i] == actualReds[j]) {
                    redHit++;
                }
            }
        }

        blueHit = buyLottery.getBlue() == actualLottery.getBlue();
        int level = 0;
        if (redHit == 6 && blueHit) {
            level = 1;
        } else if (redHit == 6) {
            level = 2;
        } else if (redHit == 5 && blueHit) {
            level = 3;
        } else if (redHit == 4 && blueHit || redHit == 5) {
            level = 4;
        } else if (redHit == 3 && blueHit || redHit == 4) {
            level = 5;
        } else if (blueHit) {
            level = 6;
        }
        buyLottery.setLevel(level);
        return LotteryConstants.SS_BONUS.get(level);
    }

    protected int[] randomReds() {
        return random(1, 34, 6);
    }

    /**
     * 在[start, end)里产生num个不重复随机数
     *
     * @param start
     * @param end
     * @param num
     * @return
     */
    protected int[] random(int start, int end, int num) {
        int size = end - start;
        if (size < num) {
            return null;
        }

        int[] result = new int[num];
        int[] data = new int[size];
        for (int i = 0; i < size; i++) {
            data[i] = start + i;
        }

        Random random = new Random();
        for (int i = 0; i < num; i++) {
            int tmp = random.nextInt(size);
            result[i] = data[tmp];
            if (tmp != size - 1) {
                data[tmp] = data[size - 1];
            }
            size--;
        }
        quickSort(result);
        return result;
    }

    private void quickSort(int[] array) {
        int len;
        if (array == null
                || (len = array.length) == 0
                || len == 1) {
            return;
        }
        sort(array, 0, len - 1);
    }

    /**
     * 快排核心算法，递归实现
     *
     * @param array
     * @param left
     * @param right
     */
    private void sort(int[] array, int left, int right) {
        if (left > right) {
            return;
        }
        // base中存放基准数
        int base = array[left];
        int i = left, j = right;
        while (i != j) {
            // 顺序很重要，先从右边开始往左找，直到找到比base值小的数
            while (array[j] >= base && i < j) {
                j--;
            }
            // 再从左往右边找，直到找到比base值大的数
            while (array[i] <= base && i < j) {
                i++;
            }
            // 上面的循环结束表示找到了位置或者(i>=j)了，交换两个数在数组中的位置
            if (i < j) {
                int tmp = array[i];
                array[i] = array[j];
                array[j] = tmp;
            }
        }

        // 将基准数放到中间的位置（基准数归位）
        array[left] = array[i];
        array[i] = base;

        // 递归，继续向基准的左右两边执行和上面同样的操作
        // i的索引处为上面已确定好的基准值的位置，无需再处理
        sort(array, left, i - 1);
        sort(array, i + 1, right);
    }

    public class SSLotteryRecord {
        List<SSLottery> lotteries = new ArrayList<>();
        long cost;
        long bonus;

        public SSLotteryRecord() {
        }

        public List<SSLottery> getLotteries() {
            return lotteries;
        }

        public void addLottery(SSLottery lottery) {
            this.lotteries.add(lottery);
        }

        public long getCost() {
            return cost;
        }

        public void setCost(long cost) {
            this.cost = cost;
        }

        public long getBonus() {
            return bonus;
        }

        public void setBonus(long bonus) {
            this.bonus = bonus;
        }
    }

}
