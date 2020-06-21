package com.archi.architecture.util;

import com.archi.architecture.BaseApplication;

public class ContextUtil {
    public static BaseApplication getAppContext(){
        return BaseApplication.getInstance();
    }
}
