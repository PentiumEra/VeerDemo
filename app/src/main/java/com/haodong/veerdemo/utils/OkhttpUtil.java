package com.haodong.veerdemo.utils;

/**
 * @author linghailong
 * @date on 2018/11/7
 * @email 105354999@qq.com
 * @describe :
 */
public class OkhttpUtil {
    public static final String METHOD_GET = "GET";

    /**
     * get请求
     * @param url：url
     * @param callBack：
     */
    public static void okHttpGet(String url, CallBackUtil callBack) {
        new RequestUtil(url,callBack).execute();
    }


}
