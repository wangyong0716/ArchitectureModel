package com.archi.database.task;

import com.archi.database.info.IInfo;

import java.util.List;

public interface ITask {

    String getTaskName();

    void start();

    void stop();

    boolean save(IInfo info);

    boolean save(List<IInfo> infoList);
}
