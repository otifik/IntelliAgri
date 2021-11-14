package com.jit.AgriIn.ui.activity.video.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Johnny Tam on 2017/4/27.
 */

public class TokenManager {
    private static TokenService mTokenService;

    public synchronized static TokenService createTokenService() {
        if (mTokenService  == null) {
            Retrofit retrofit = createRetrofit();
            mTokenService = retrofit.create(TokenService.class);
        }

        return mTokenService;
    }

    private static Retrofit createRetrofit() {
        OkHttpClient httpClient;
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient = new OkHttpClient.Builder().addInterceptor(logging).build();

        return new Retrofit.Builder()
                .baseUrl(TokenService.BASE_URL)
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
    }
}
