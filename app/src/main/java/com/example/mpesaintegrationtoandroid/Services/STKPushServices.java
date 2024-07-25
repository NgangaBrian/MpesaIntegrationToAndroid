package com.example.mpesaintegrationtoandroid.Services;

import com.example.mpesaintegrationtoandroid.Model.AccessToken;
import com.example.mpesaintegrationtoandroid.Model.STKPush;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface STKPushServices {
    @POST("mpesa/stkpush/v1/processrequest")
    Call<STKPush> sendPush(@Body STKPush stkPush);

    @GET("oauth/v1/generate?grant_type=client_credentials")
    Call<AccessToken> getAccessToken(@Header("Authorization") String authHeader);
}
