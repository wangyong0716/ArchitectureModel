package com.archi.architecture.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.archi.architecture.R;
import com.archi.architecture.lottery.BackgroundHandler;
import com.archi.architecture.lottery.LotteryController;
import com.archi.architecture.lottery.LotteryDataKeeper;
import com.archi.architecture.lottery.SSLottery;
import com.archi.architecture.lottery.database.SSLotteryCache;
import com.archi.architecture.lottery.database.SSLotteryInfo;
import com.archi.architecture.lottery.net.LotteryFetcher;
import com.archi.architecture.lottery.plan.LRUSSPlan;
import com.archi.architecture.lottery.plan.RandomSSPlan;
import com.archi.database.async.AsyncThreadTask;
import com.archi.database.info.IInfo;

import java.util.ArrayList;
import java.util.List;

public class LotteryActivity extends Activity {
    private static final String TAG = "ss_lottery";
    private Button mUpdateBtn;
    private Button mRefreshBtn;
    private Button mRandomBtn;
    private Button mLRUBtn;
    private ListView mDisplayList;
    private LotteryAdapter mLotteryAdapter;

    private HandlerThread mMonitorThread = new HandlerThread("buy_monitor");
    private Handler mMonitorHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery);
        mUpdateBtn = findViewById(R.id.update);
        mRefreshBtn = findViewById(R.id.refresh);
        mRandomBtn = findViewById(R.id.random_buy);
        mLRUBtn = findViewById(R.id.lru_buy);
        mDisplayList = findViewById(R.id.display);
        mLotteryAdapter = new LotteryAdapter(this);
        mDisplayList.setAdapter(mLotteryAdapter);

        mUpdateBtn.setOnClickListener(mOnClickListener);
        mRefreshBtn.setOnClickListener(mOnClickListener);
        mRandomBtn.setOnClickListener(mOnClickListener);
        mLRUBtn.setOnClickListener(mOnClickListener);

        mMonitorThread.start();
        mMonitorHandler = new Handler(mMonitorThread.getLooper());
    }

    private class LotteryAdapter extends BaseAdapter {

        private Context mContext;
        private List<SSLottery> mLotteries = new ArrayList<>();

        public LotteryAdapter(Context context) {
            mContext = context;
        }

        private void update(List<SSLottery> lotteries) {
            mLotteries.clear();
            mLotteries.addAll(lotteries);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mLotteries.size();
        }

        @Override
        public SSLottery getItem(int position) {
            return mLotteries.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.item_ssl_layout, null);
                viewHolder = new ViewHolder();
                viewHolder.date = convertView.findViewById(R.id.date);
                viewHolder.seriesNum = convertView.findViewById(R.id.series_num);
                viewHolder.reds = convertView.findViewById(R.id.reds);
                viewHolder.blue = convertView.findViewById(R.id.blue);
                viewHolder.poolBonus = convertView.findViewById(R.id.pool_bonus);
                viewHolder.firstCount = convertView.findViewById(R.id.win1_count);
                viewHolder.firstMoney = convertView.findViewById(R.id.win1_money);
                viewHolder.secondCount = convertView.findViewById(R.id.win2_count);
                viewHolder.secondMoney = convertView.findViewById(R.id.win2_money);
                viewHolder.sellMoney = convertView.findViewById(R.id.sell_money);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            SSLottery lottery = getItem(position);
            viewHolder.date.setText(lottery.getDateString());
            viewHolder.seriesNum.setText(String.valueOf(lottery.getSeriesNum()));
            viewHolder.reds.setText(lottery.getRedsStr());
            viewHolder.blue.setText(String.valueOf(lottery.getBlue()));
            viewHolder.poolBonus.setText(String.valueOf(lottery.getPoolBonus()));
            viewHolder.firstCount.setText(String.valueOf(lottery.getFirstPot().getCount()));
            viewHolder.firstMoney.setText(String.valueOf(lottery.getFirstPot().getMoney()));
            viewHolder.secondCount.setText(String.valueOf(lottery.getSecondPot().getCount()));
            viewHolder.secondMoney.setText(String.valueOf(lottery.getSecondPot().getMoney()));
            viewHolder.sellMoney.setText(String.valueOf(lottery.getSellMoney()));
            return convertView;
        }

        class ViewHolder {
            private TextView date;
            private TextView seriesNum;
            private TextView reds;
            private TextView blue;
            private TextView poolBonus;
            private TextView firstCount;
            private TextView firstMoney;
            private TextView secondCount;
            private TextView secondMoney;
            private TextView sellMoney;
        }
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.update:
                    checkUpdateLottery();
                    break;
                case R.id.refresh:
                    displayLotteries();
                    break;
                case R.id.random_buy:
                    mMonitorHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            RandomSSPlan randomSSPlan = new RandomSSPlan();
                            randomSSPlan.setStart(2018, 1, 1);
                            randomSSPlan.activate();
                        }
                    });

                    break;
                case R.id.lru_buy:
                    mMonitorHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            LRUSSPlan lrussPlan = new LRUSSPlan();
                            lrussPlan.setStart(2018, 1, 1);
                            lrussPlan.activate();
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    };

    private void displayLotteries() {
        AsyncThreadTask.execute(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "displayLotteries");
                List<IInfo> infos = SSLotteryCache.getInstance().getAll();
                final List<SSLottery> lotteries = new ArrayList<>();
                Log.i(TAG, "displayLotteries1");
                for (IInfo info : infos) {
                    if (info instanceof SSLotteryInfo) {
                        lotteries.add(((SSLotteryInfo) info).getSSLottery());
                    }
                }
                Log.i(TAG, "displayLotteries2");
                LotteryDataKeeper.getInstance().update(lotteries);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, "displayLotteries3");
                        try {
                            mLotteryAdapter.update(lotteries);
                        }catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void checkUpdateLottery() {
        if (LotteryController.getInstance().needUpdate()) {
            BackgroundHandler.execute(new Runnable() {
                @Override
                public void run() {
                    List<SSLottery> lotteries = LotteryFetcher.getAllSSData();
                    Log.i(TAG, "fetch data size = " + lotteries.size());
                    SSLotteryCache.getInstance().saveList(lotteries);
                }
            });
        }
    }
}
