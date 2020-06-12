package com.archi.architecture.lottery.net;


import android.text.TextUtils;

import com.archi.architecture.lottery.BigLottoBall;
import com.archi.architecture.lottery.DoubleColorBall;
import com.archi.architecture.lottery.LotteryConstants;
import com.archi.architecture.lottery.SSLottery;
import com.archi.log.ArchiLog;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yy on 2017/10/16.
 * 获取彩票开奖信息工具类
 * 1、获取传入期号之后开奖的双色球数据
 * 2、获取全部双色球的开奖数据
 * 3、获取传入期号之后大乐透的开奖数据
 * 4、获取全部大乐透开奖信息数据
 */
public class LotteryFetcher {
    private static final String TAG = "LotteryFetcher";

    /**
     * 获取全部双色球的开奖信息数据
     *
     * @return list对象
     * @throws Exception
     */
    public static List<DoubleColorBall> getAllDCB() throws Exception {
        List<DoubleColorBall> list = new ArrayList<DoubleColorBall>();
        //获取url信息
        String data = GetUrlData.getString(LotteryConstants.SS_URL);
        Pattern pattern = Pattern.compile(LotteryConstants.LIST_REGEX);
        Matcher matcher = pattern.matcher(data);
        //遍历获取的数据
        while (matcher.find()) {
            String info = matcher.group();
            List<String> infoData = new ArrayList<String>();
            Pattern patternInfo = Pattern.compile(LotteryConstants.INFO_REGEX);
            Matcher matcherInfo = patternInfo.matcher(info);
            //获取具体的数据
            while (matcherInfo.find()) {
                infoData.add(matcherInfo.group().replaceAll(">|<|,", ""));
            }
            //创建对象
            list.add(new DoubleColorBall(infoData));
        }
        return list;

    }

    /**
     * 根据传进来的的开奖期号获取这个开奖期号自后的开奖信息
     *
     * @param period_Id 开奖期号
     * @return List对象
     * @throws Exception
     */
    public static List<DoubleColorBall> getBeforeDCB(int period_Id) throws Exception {
        List<DoubleColorBall> list = new ArrayList<DoubleColorBall>();
        //获取url信息
        String data = GetUrlData.getString(LotteryConstants.SS_URL);

        Pattern pattern = Pattern.compile(LotteryConstants.LIST_REGEX);
        Matcher matcher = pattern.matcher(data);
        //遍历获取的数据
        while (matcher.find()) {
            String info = matcher.group();
            List<String> infoData = new ArrayList<String>();
            Pattern patternInfo = Pattern.compile(LotteryConstants.INFO_REGEX);
            Matcher matcherInfo = patternInfo.matcher(info);
            //获取具体的数据
            while (matcherInfo.find()) {
                infoData.add(matcherInfo.group().replaceAll(">|<|,", ""));
            }
            //要是获取的期号小于或者等于传进来的期号则跳出
            if (Integer.parseInt(infoData.get(0)) <= period_Id) {
                break;
            } else {
                //创建对象
                list.add(new DoubleColorBall(infoData));
            }
        }
        return list;
    }

    /**
     * 获取全部大乐透的开奖信息
     *
     * @return List对象
     * @throws Exception
     */
    public static List<BigLottoBall> getAllBLB() throws Exception {
        List<BigLottoBall> list = new ArrayList<BigLottoBall>();
        //获取url信息
        String data = GetUrlData.getString(LotteryConstants.DL_URL);

        Pattern pattern = Pattern.compile(LotteryConstants.LIST_REGEX);
        Matcher matcher = pattern.matcher(data);
        //遍历获取的数据
        while (matcher.find()) {
            String info = matcher.group();
            List<String> infoData = new ArrayList<String>();
            Pattern patternInfo = Pattern.compile(LotteryConstants.INFO_REGEX);
            Matcher matcherInfo = patternInfo.matcher(info);
            //获取具体的数据
            while (matcherInfo.find()) {
                infoData.add(matcherInfo.group().replaceAll(">|<|,", ""));
            }
            //创建对象
            list.add(new BigLottoBall(infoData));
        }
        return list;

    }

    /**
     * 根据传入的期号来获取这个期号之前的开奖信息
     *
     * @param period_Id 开奖期号
     * @return
     * @throws Exception
     */
    public static List<BigLottoBall> getBeforeBLB(int period_Id) throws Exception {
        List<BigLottoBall> list = new ArrayList<BigLottoBall>();
        //获取url信息
        String data = GetUrlData.getString(LotteryConstants.DL_URL);

        Pattern pattern = Pattern.compile(LotteryConstants.LIST_REGEX);
        Matcher matcher = pattern.matcher(data);
        //遍历获取的数据
        while (matcher.find()) {
            String info = matcher.group();
            List<String> infoData = new ArrayList<String>();
            Pattern patternInfo = Pattern.compile(LotteryConstants.INFO_REGEX);
            Matcher matcherInfo = patternInfo.matcher(info);
            //获取具体的数据
            while (matcherInfo.find()) {
                infoData.add(matcherInfo.group().replaceAll(">|<|,", ""));
            }
            //要是获取的期号小于或者等于传进来的期号则跳出
            if (Integer.parseInt(infoData.get(0)) <= period_Id) {
                break;
            } else {
                //创建对象
                list.add(new BigLottoBall(infoData));
            }
        }
        return list;
    }

    public static void testSSData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<DoubleColorBall> list = LotteryFetcher.getAllDCB();
                    ArchiLog.e(TAG, "got size = " + list.size());
                    for (int i = 0; i < list.size(); i++) {
                        ArchiLog.e(TAG, list.get(i).toString());
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public static List<SSLottery> getAllSSData() {
        List<SSLottery> list = new ArrayList<SSLottery>();
        //获取url信息
        String data = null;
        try {
            data = GetUrlData.getString(LotteryConstants.SS_URL);
        }catch (Exception e) {
            e.printStackTrace();
        }
        if (data == null) {
            return list;
        }
        ArchiLog.e(TAG, "get data is " + (TextUtils.isEmpty(data)? "" : "not") + " null");
        Pattern pattern = Pattern.compile(LotteryConstants.LIST_REGEX);
        Matcher matcher = pattern.matcher(data);
        //遍历获取的数据
        while (matcher.find()) {
            String info = matcher.group();
            List<String> infoData = new ArrayList<String>();
            Pattern patternInfo = Pattern.compile(LotteryConstants.INFO_REGEX);
            Matcher matcherInfo = patternInfo.matcher(info);
            //获取具体的数据
            while (matcherInfo.find()) {
                infoData.add(matcherInfo.group().replaceAll(">|<|,", ""));
            }
            //创建对象
            list.add(new SSLottery(infoData));
        }
        return list;

    }



//    public static void main(String[] args){
//        long star=System.currentTimeMillis();
//        String url="http://datachart.500.com/ssq/history/newinc/history.php?limit=100000&sort=0";
//        try {
//            //测试获取url信息
//            //System.out.println(LogeryFetcher.getString(url));
//            //测试获取全部的双色球开奖信息
//            List<DoubleColorBall> list= LogeryFetcher.getAllDCB();
//            //测试获取全部的大乐透开奖信息
//            //List<BigLottoBall> list= LogeryFetcher.getAllBLB();
//            //测试获取这个双色球16120期号自后的开奖数据
//            //List<DoubleColorBall> list= LogeryFetcher.getBeforeDCB(16120);
//            //测试获取这个双色球17120期号自后的开奖数据
//            //List<DoubleColorBall> list= LogeryFetcher.getBeforeDCB(17120);
//            //测试获取这个大乐透16120期号自后的开奖数据
//            //List<BigLottoBall> list= LogeryFetcher.getBeforeBLB(16120);
//            //测试获取这个大乐透17110期号自后的开奖数据
////            List<BigLottoBall> list= LogeryFetcher.getBeforeBLB(17110);
//
//            for(int i=0;i<list.size();i++){
//                System.out.println(list.get(i).toString());
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        System.out.println(System.currentTimeMillis()-star);
//    }
}