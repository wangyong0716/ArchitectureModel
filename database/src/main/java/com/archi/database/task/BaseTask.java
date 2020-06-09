package com.archi.database.task;

import com.archi.database.info.IInfo;
import com.archi.database.storage.IStorage;
import com.archi.log.ArchiLog;

import java.util.List;

public abstract class BaseTask implements ITask {
    public static final String BASE_TAG = "BaseTask";

    protected IStorage mStorage;


    public BaseTask() {
        mStorage = getStorage();
    }

    protected abstract IStorage getStorage();

    @Override
    public void start() {
        ArchiLog.i(BASE_TAG, "start task --> " + getTaskName());
    }

    @Override
    public void stop() {
        ArchiLog.i(BASE_TAG, "stop task --> " + getTaskName());
    }

    @Override
    public boolean save(IInfo info) {
        ArchiLog.i(BASE_TAG, "save info task --> " + getTaskName());
        return info != null && mStorage != null && mStorage.save(info);
    }

    @Override
    public boolean save(List<IInfo> infoList) {
        ArchiLog.i(BASE_TAG, "save infos task --> " + getTaskName());
        return infoList != null && !infoList.isEmpty() && mStorage != null && mStorage.save(infoList);
    }
}
