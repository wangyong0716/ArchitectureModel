package com.archi.database;

import com.archi.database.common.CommonStorage;
import com.archi.database.common.CommonTable;
import com.archi.database.storage.IStorage;
import com.archi.database.table.ITable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaticTask {

    public static final String TASK_COMMON = "common";

    public static final int FLAG_COMMON = 0x00000001;

    //每个任务模块为key，模块静态开关为value的Map
    private static Map<String, Integer> sTaskMap;

    public static Map<String, Integer> getTaskMap() {
        if (sTaskMap == null) {
            sTaskMap = new HashMap<String, Integer>();
            sTaskMap.put(TASK_COMMON, FLAG_COMMON);
        }
        return sTaskMap;
    }

    /**
     * Argus APM  数据库查询表名称
     */
    public static final String[] sTableNameList = {
            StaticTask.TASK_COMMON
    };
    /**
     * 数据库查询表Table对象列表
     */
    public static ITable[] sTableList = {
            new CommonTable()
    };
    /**
     * 数据库查询表Storage对象列表
     */
    public static List<IStorage> sAllStorage = Arrays.<IStorage>asList(
            new CommonStorage());
}
