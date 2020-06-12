package com.archi.architecture.lottery.net;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

public class GetUrlData {
    /**
     * 根据传入的url将结果以String的数据返回
     *
     * @param url 需要请求的url
     * @return
     * @throws Exception
     */
    public static String getString(String url) throws Exception {
        //当传入的url返回不为空的时候，读取数据
        BufferedReader reader = null;
        InputStreamReader input = null;
        StringBuilder data = null;//提高字符数据的生成
        if (!TextUtils.isEmpty(url)) {
            try {
                //设置请求的头信息
                URL urlInfo = new URL(url);
                URLConnection connection = urlInfo.openConnection();
                connection.addRequestProperty("Host", urlInfo.getHost());//设置头信息
                connection.addRequestProperty("Connection", "keep-alive");
                connection.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
                connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36");
                connection.connect();
                //获取请求回来的信息
                input = new InputStreamReader(connection.getInputStream(), "UTF-8");//定义返回数据的格式
                reader = new BufferedReader(input);
                data = new StringBuilder();
                String str;
                while ((str = reader.readLine()) != null) {
                    data.append(str);
                }
            } catch (Exception e) {
                throw new Exception("读取Url数据失败：" + url, e);
            } finally {
                if (reader != null) {
                    reader.close();//关闭操作流
                }
                if (input != null) {
                    input.close();
                }
            }
        }
        return data.toString();
    }

    /**
     * 获取url链接数据,以GZIP解压
     * 如第一个方法返回乱码，请使用本方法
     *
     * @param url
     * @return
     */
    public static String unGZIPGetString(String url) throws Exception {
        //当传入的url返回不为空的时候，读取数据
        if (!TextUtils.isEmpty(url)) {
            try {
                //设置请求的头信息
                URL urlInfo = new URL(url);
                URLConnection connection = urlInfo.openConnection();
                connection.addRequestProperty("Host", urlInfo.getHost());//设置头信息
                connection.addRequestProperty("Connection", "keep-alive");
                connection.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
                connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36");
                connection.connect();
                //获取请求回来的信息
                //设置解压信息
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                GZIPInputStream gzip = new GZIPInputStream(connection.getInputStream());
                byte[] buffer = new byte[256];
                int n;
                while ((n = gzip.read(buffer)) >= 0) {
                    out.write(buffer, 0, n);
                }
                out.close();
                gzip.close();
                return out.toString("UTF-8");

            } catch (Exception e) {
                throw new Exception("读取Url数据失败：" + url, e);
            }
        }
        return null;
    }
}
