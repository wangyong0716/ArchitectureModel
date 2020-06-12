package com.archi.architecture.lottery;

public class LotteryConstants {
    public static final int SS_RED_SIZE = 6;

    public static final int DL_RED_SIZE = 5;

    public static final int WIN_LEVEL_FIRST = 1;
    public static final int WIN_LEVEL_SECOND = 2;

    //大乐透请求url
    public static String DL_URL = "https://datachart.500.com/dlt/history/newinc/history.php?limit=10000&sort=0";
    //双色球请求url
    public static String SS_URL = "https://datachart.500.com/ssq/history/newinc/history.php?limit=100000&sort=0";
    //匹配开奖正则
    public static String LIST_REGEX = "--><(.*?)</tr>";
    //匹配开奖明细正则
    public static String INFO_REGEX = ">[,\\d-]+?<";

    public static long TWO_DAY_MILLS = 172800000L;
}
