package com.archi.architecture;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.archi.architecture.lottery.LotteryController;
import com.archi.architecture.lottery.database.SSLotteryTable;
import com.archi.architecture.time.TimeMonitorConfig;
import com.archi.architecture.time.TimeMonitorManager;
import com.archi.database.StorageManager;
import com.archi.database.common.CommonTable;

public class BaseApplication extends Application {
    private static BaseApplication sInstance;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        TimeMonitorManager.getInstance().resetTimeMonitor(TimeMonitorConfig.TIME_MONITOR_ID_APPLICATION_START);
        TimeMonitorManager.getInstance().getTimeMonitor(TimeMonitorConfig.TIME_MONITOR_ID_APPLICATION_START).startMonitor();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        TimeMonitorManager.getInstance().getTimeMonitor(TimeMonitorConfig.TIME_MONITOR_ID_APPLICATION_START).recordingTimeTag("Application-onCreate");
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
