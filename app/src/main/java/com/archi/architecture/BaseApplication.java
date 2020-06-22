package com.archi.architecture;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.archi.architecture.lottery.LotteryController;
import com.archi.architecture.lottery.database.SSLotteryTable;
import com.archi.database.StorageManager;
import com.archi.database.common.CommonTable;

public class BaseApplication extends Application {
    private static BaseApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        registerDbs();
        initRouter();
    }

    public static BaseApplication getInstance() {
        return sInstance;
    }

    private void initRouter() {
        if (BuildConfig.DEBUG) {
            //一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
    }

    private void registerDbs() {
        LotteryController.getInstance().init(this.getApplicationContext());

        StorageManager.getInstance().init(this);
        StorageManager.getInstance().register(new CommonTable());
        StorageManager.getInstance().register(new SSLotteryTable());
    }
}
