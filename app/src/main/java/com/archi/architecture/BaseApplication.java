package com.archi.architecture;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

public class BaseApplication extends Application {
    private static  BaseApplication sInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        sInstance=this;
        initRouter();
    }
    public static BaseApplication getInstance(){
        return sInstance;
    }
    private void initRouter(){
        if (BuildConfig.DEBUG) {
            //一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
    }

}
