package com.archi.database;

public class TaskManager {
    private static volatile TaskManager sInstance;

    private TaskManager() {

    }

    public static TaskManager getInstance() {
        if (sInstance == null) {
            synchronized (TaskManager.class) {
                if (sInstance == null) {
                    sInstance = new TaskManager();
                }
            }
        }
        return sInstance;
    }


}
