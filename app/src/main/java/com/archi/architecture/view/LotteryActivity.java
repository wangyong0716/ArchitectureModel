package com.archi.architecture.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;
import androidx.fragment.app.FragmentActivity;

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

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LotteryActivity extends FragmentActivity {
    private static final String TAG = "ss_lottery";

    private SSDisplayFragmentNew mFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new AsyncLayoutInflater(LotteryActivity.this)
                .inflate(R.layout.activity_lottery, null, new AsyncLayoutInflater.OnInflateFinishedListener() {
                    @Override
                    public void onInflateFinished(View view, int resid, ViewGroup parent) {
                        setContentView(view);
                        ButterKnife.bind(LotteryActivity.this);
                        readSSLotteries();

                        mFragment = new SSDisplayFragmentNew();

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, mFragment).commit();
                        getSupportFragmentManager().beginTransaction().show(mFragment);
                    }
                });
    }


    void readSSLotteries() {
        AsyncThreadTask.execute(new Runnable() {
            @Override
            public void run() {
                List<IInfo> infos = SSLotteryCache.getInstance().getAll();
                final List<SSLottery> lotteries = new ArrayList<>();
                for (IInfo info : infos) {
                    if (info instanceof SSLotteryInfo) {
                        lotteries.add(((SSLotteryInfo) info).getSSLottery());
                    }
                }
                LotteryDataKeeper.getInstance().update(lotteries);
            }
        });
    }

    @OnClick(R.id.update)
    void checkUpdateLottery() {
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

    @OnClick(R.id.random_buy)
    void randomBuy() {
        BackgroundHandler.execute(new Runnable() {
            @Override
            public void run() {
                RandomSSPlan randomSSPlan = new RandomSSPlan();
                randomSSPlan.setStart(2018, 1, 1);
                randomSSPlan.activate();
            }
        });
    }

    @OnClick(R.id.lru_buy)
    void lruBuy() {
        BackgroundHandler.execute(new Runnable() {
            @Override
            public void run() {
                LRUSSPlan lrussPlan = new LRUSSPlan();
                lrussPlan.setStart(2018, 1, 1);
                lrussPlan.activate();
            }
        });
    }
}
