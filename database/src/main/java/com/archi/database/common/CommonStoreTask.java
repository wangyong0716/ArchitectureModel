package com.archi.database.common;

import com.archi.database.async.AsyncThreadTask;
import com.archi.database.info.IInfo;
import com.archi.database.storage.IStorage;
import com.archi.database.task.BaseTask;

import java.util.List;

public class CommonStoreTask extends BaseTask {
    private static volatile CommonStoreTask sInstance;

    private CommonStoreTask() {
    }

    public static CommonStoreTask getInstance() {
        if (sInstance == null) {
            synchronized (CommonStoreTask.class) {
                if (sInstance == null) {
                }
                sInstance = new CommonStoreTask();
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
                CommonStoreTask.getInstance().save(commonInfo);
            }
        });
    }

    public List<IInfo> getAll() {
        return getStorage().getAll();
    }
}
