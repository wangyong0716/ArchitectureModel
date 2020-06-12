package com.archi.architecture.lottery;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BackgroundHandler {

    private static ExecutorService sSingleBackgroundHandler = Executors.newSingleThreadExecutor();

    public static void execute(Runnable runnable) {
        sSingleBackgroundHandler.execute(runnable);
    }
}
