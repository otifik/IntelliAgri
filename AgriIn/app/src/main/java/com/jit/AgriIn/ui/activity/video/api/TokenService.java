package com.jit.AgriIn.ui.activity.video.api;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by yuanyuan on 2018/12/5.
 */

public interface TokenService {
//    String BASE_URL = "http://223.2.197.240:8088/YinshiServlet/";
    String BASE_URL = "http://111.229.163.181:8089/";

    @GET("getAccessToken")
    Observable<TokenResponse> getAccessToken(@Query("phone") String phone);

    @POST("getAccessToken")
    Observable<TokenResponse> getAccessTokenByCode(@Body TokenBean tokenBean);
}
