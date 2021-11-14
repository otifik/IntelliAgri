package com.jit.AgriIn.ui.activity.video.api;

import com.google.gson.Gson;
import com.zxl.baselib.api.BaseApiRetrofit;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author zxl on 2018/8/31.
 *         discription: 请求封装
 */

public class TokenRetrofit extends BaseApiRetrofit {
    public TokenService mApi;
    private TokenRetrofit(){
        //在构造方法中完成对Retrofit接口的初始化
        mApi = new Retrofit.Builder()
                .baseUrl(TokenService.BASE_URL)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(TokenService.class);
    }

    private static class  ApiRetrofitHolder{
        private static final TokenRetrofit M_INSTANCE =  new TokenRetrofit();
    }

    public static TokenRetrofit getInstance(){
        return ApiRetrofitHolder.M_INSTANCE;
    }



    // 获取Token
    public Observable<TokenResponse>getAccessToken(String phoneNum){
        return mApi.getAccessToken(phoneNum);
    }

    // 获取Token  SMS
    public Observable<TokenResponse>getAccessTokenByCode(TokenBean tokenBean){
        return mApi.getAccessTokenByCode(tokenBean);
    }







    /**
     * bean 转body
     * @param obj
     * @return
     */
    private RequestBody getRequestBody(Object obj) {
        String route = new Gson().toJson(obj);
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"),route);
    }

    private MultipartBody.Part getMultipartBody(File file, String type){
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part multipartBody =
                MultipartBody.Part.createFormData(type, file.getName(), requestFile);
        return multipartBody;
    }

    private MultipartBody.Part getMultipartBody(File file){
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part multipartBody =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        return multipartBody;
    }



}
