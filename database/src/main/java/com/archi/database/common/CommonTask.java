package com.archi.database.common;

import com.archi.database.DbHelper;
import com.archi.database.async.AsyncThreadTask;
import com.archi.database.info.IInfo;
import com.archi.database.storage.IStorage;
import com.archi.database.task.BaseTask;

import java.util.List;

public class CommonTask extends BaseTask {
    private static volatile CommonTask sInstance;

    private CommonTask(){}

    public static CommonTask getInstance() {
        if (sInstance ==null) {
            synchronized (CommonTask.class) {
                if (sInstance ==null) {}
                sInstance = new CommonTask();
            }
        }
        return sInstance;
    }

    @Override
    protected IStorage getStorage() {
        return new CommonStorage();
    }

    @Override
    public String getTaskName() {
        return "common";
    }

    public void save(final String value) {
        AsyncThreadTask.execute(new Runnable() {
            @Override
            public void run() {
                CommonInfo commonInfo = new CommonInfo();
                commonInfo.text = value;
                CommonTask.getInstance().save(commonInfo);
            }
        });
    }

    public List<IInfo> getAll() {
        return getStorage().getAll();
    }

    public void init() {
        DbHelper.addTable(new CommonTable());
    }
}
