package com.example.mpesaintegrationtoandroid.Interceptor;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import android.util.Base64;
import android.util.Log;

import com.example.mpesaintegrationtoandroid.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
public class AccessTokenInterceptor implements  Interceptor{
    public AccessTokenInterceptor(){}
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        String keys = new StringBuilder().append(BuildConfig.CONSUMER_KEY).append(":").append(BuildConfig.CONSUMER_SECRET).toString();
        String encoded = Base64.encodeToString(keys.getBytes(), Base64.NO_WRAP);
        byte[] data = keys.getBytes(StandardCharsets.ISO_8859_1);

        String base = Base64.encodeToString(data, Base64.DEFAULT);
        System.out.println("base code: " + base);
        Log.d("Interceptor code: ", encoded);
        Request request = chain.request().newBuilder()
                .addHeader("Authorization", "Basic " + encoded )
                .build();
        return  chain.proceed(request);
    }
}
