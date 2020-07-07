package com.archi.architecture;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.archi.architecture.lottery.LotteryController;
import com.archi.architecture.lottery.database.SSLotteryTable;
import com.archi.architecture.time.TimeMonitorConfig;
import com.archi.architecture.time.TimeMonitorManager;
import com.archi.architecture.util.DeviceUtil;
import com.archi.database.StorageManager;
import com.archi.database.common.CommonTable;

public class BaseApplication extends MultiDexApplication {
    private static final String TAG = "BaseApplication";
    private static BaseApplication sInstance;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        //只有主进程以及SDK版本5.0以下才走。
        if (isMainProcess(BaseApplication.this)) {
            if (!dexOptDone(base)) {
                preLoadDex(base);
            }
            long startTime = System.currentTimeMillis();
            MultiDex.install(this);
            Log.i(TAG,"MainProcessCostTime:"+(System.currentTimeMillis() - startTime));
        }



        TimeMonitorManager.getInstance().resetTimeMonitor(TimeMonitorConfig.TIME_MONITOR_ID_APPLICATION_START);
        TimeMonitorManager.getInstance().getTimeMonitor(TimeMonitorConfig.TIME_MONITOR_ID_APPLICATION_START).startMonitor();
    }

    /**
     * 判断是不是UI主进程，因为有些东西只能在UI主进程初始化
     */
    public static boolean isMainProcess(Context context) {
        try {
            int pid = android.os.Process.myPid();
            String process = getAppNameByPID(context, pid);
            if (TextUtils.isEmpty(process)) {
                Log.i("QQQ", "true");
                return true;
            } else if (context.getPackageName().equalsIgnoreCase(process)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * 根据Pid得到进程名
     */
    public static String getAppNameByPID(Context context, int pid) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (android.app.ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
            if (processInfo.pid == pid) {
                return processInfo.processName;
            }
        }
        return "";
    }

    /**
     * 当前版本是否进行过DexOpt操作。
     * @param context
     * @return
     */
    private boolean dexOptDone(Context context) {
        SharedPreferences sp = context.getSharedPreferences(
                DeviceUtil.getVersionName(context), MODE_MULTI_PROCESS);
        return sp.getBoolean("dexoptdone", false);
    }

    /**
     * 在单独进程中提前进行DexOpt的优化操作；主进程进入等待状态。
     *
     * @param base
     */
    public void preLoadDex(Context base) {
        Intent intent = new Intent(BaseApplication.this, PreLoadDexActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        base.startActivity(intent);
        while (!dexOptDone(base)) {
            try {
                //主线程开始等待；直到优化进程完成了DexOpt操作。
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
