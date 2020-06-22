package com.archi.architecture.lottery.database;

import android.util.Log;

import com.archi.architecture.lottery.SSLottery;
import com.archi.database.async.AsyncThreadTask;
import com.archi.database.info.IInfo;
import com.archi.database.storage.IStorage;
import com.archi.database.task.BaseTask;

import java.util.ArrayList;
import java.util.List;

public class SSLotteryCache extends BaseTask {
    private static volatile SSLotteryCache sInstance;

    private SSLotteryCache() {
    }

    public static SSLotteryCache getInstance() {
        if (sInstance == null) {
            synchronized (SSLotteryCache.class) {
                if (sInstance == null) {
                }
                sInstance = new SSLotteryCache();
            }
        }
        return sInstance;
    }

    @Override
    protected IStorage getStorage() {
        return new SSLotteryStorage();
    }

    @Override
    public String getTaskName() {
        return "ss_lottery";
    }

    public void save(final SSLottery value) {
        AsyncThreadTask.execute(new Runnable() {
            @Override
            public void run() {
                SSLotteryInfo sSlLotteryInfo = new SSLotteryInfo(value);
                SSLotteryCache.getInstance().save(sSlLotteryInfo);
            }
        });
    }

    public void saveList(final List<SSLottery> values) {
        AsyncThreadTask.execute(new Runnable() {
            @Override
            public void run() {
                List<IInfo> infos = new ArrayList<>();
                for (SSLottery value : values) {
                    infos.add(new SSLotteryInfo(value));
                }
                Log.i("ss_lottery", "saveList size = " + infos.size());
                SSLotteryCache.getInstance().save(infos);
            }
        });
    }

    public List<IInfo> getAll() {
        return getStorage().getAll();
    }
}
