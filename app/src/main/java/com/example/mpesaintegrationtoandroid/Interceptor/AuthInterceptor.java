package com.example.mpesaintegrationtoandroid.Interceptor;
import android.util.Log;
import androidx.annotation.NonNull;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
public class AuthInterceptor implements Interceptor {
    public  String mAuthToken;
    public AuthInterceptor(String authToken){
        this.mAuthToken = authToken;
    }
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer " + mAuthToken)
                .build();
        Log.d("AuthInterceptor", "Authorization: Bearer " + mAuthToken);
        return chain.proceed(request);
    }
}
