package com.haodong.veerdemo.utils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author linghailong
 * @date on 2018/11/7
 * @email 105354999@qq.com
 * @describe :
 */
public class RequestUtil {

    private String mMethodType;
    private String mUrl;

    //回调接口
    private CallBackUtil mCallBack;
    //OKhttpClient对象
    private OkHttpClient mOkHttpClient;
    //请求对象
    private Request mOkHttpRequest;
    //请求对象的构建者
    private Request.Builder mRequestBuilder;

    public RequestUtil(String url, CallBackUtil callBackUtil) {
       this.mUrl=url;
       this.mCallBack=callBackUtil;
       getInstance();
    }

    private void getInstance(){
        mOkHttpClient = new OkHttpClient();
        mRequestBuilder = new Request.Builder();
        mRequestBuilder.url(mUrl);
        mOkHttpRequest = mRequestBuilder.build();
    }

    void execute(){
        mOkHttpClient.newCall(mOkHttpRequest).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                if(mCallBack != null){
                    mCallBack.onError(call,e);
                }
            }
            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                if(mCallBack != null){
                    mCallBack.onSeccess(call,response);
                }
            }

        });
    }




}
