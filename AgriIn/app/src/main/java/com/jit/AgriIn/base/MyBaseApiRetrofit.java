package com.jit.AgriIn.base;



import com.zxl.baselib.BuildConfig;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by 张先磊 on 2018/4/10.
 *
 * 用于 普通请求的retrofit
 */

public class MyBaseApiRetrofit {
    private final OkHttpClient mClient;

    public OkHttpClient getClient() {
        return mClient;
    }

    public MyBaseApiRetrofit(){
        //OkHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            // Log信息拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //设置 Debug Log 模式
            builder.addInterceptor(loggingInterceptor);
            builder.addInterceptor(REWRITE_HEADER_CONTROL_INTERCEPTOR);
        }
        mClient = builder.build();
    }

    //header配置
    Interceptor REWRITE_HEADER_CONTROL_INTERCEPTOR = chain -> {
        Request request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .build();
        return chain.proceed(request);
    };
}
