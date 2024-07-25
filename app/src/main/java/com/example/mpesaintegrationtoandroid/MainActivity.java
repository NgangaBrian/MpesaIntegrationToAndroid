package com.example.mpesaintegrationtoandroid;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import static  com.example.mpesaintegrationtoandroid.Constants.BUSINESS_SHORT_CODE;
import static  com.example.mpesaintegrationtoandroid.Constants.CALLBACKURL;
import static  com.example.mpesaintegrationtoandroid.Constants.PARTYB;
import static  com.example.mpesaintegrationtoandroid.Constants.PASSKEY;
import static  com.example.mpesaintegrationtoandroid.Constants.TRANSACTION_TYPE;

import com.example.mpesaintegrationtoandroid.Interceptor.AccessTokenInterceptor;
import com.example.mpesaintegrationtoandroid.Model.AccessToken;
import com.example.mpesaintegrationtoandroid.R;
import static com.example.mpesaintegrationtoandroid.R.id.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.mpesaintegrationtoandroid.Model.STKPush;
import com.example.mpesaintegrationtoandroid.Services.DarajaApiClient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    private DarajaApiClient mApiClient;
    private ProgressDialog mProgressDialog;

    private EditText mAmount;
    private EditText mPhone;
    private Button mPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


            mProgressDialog = new ProgressDialog(this);
            mApiClient = new DarajaApiClient();
            mApiClient.setIsDebug(true);

            mPay = findViewById(R.id.pay);
            mAmount = findViewById(R.id.amount);
            mPhone = findViewById(R.id.phone);

            mPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getAccessToken();
                }
            });


    }

    private void getAccessToken() {
        mProgressDialog.setMessage("Processing Your Request");
        mProgressDialog.setTitle("Please Wait...");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();

        String consumerKey = BuildConfig.CONSUMER_KEY;
        String consumerSecret = BuildConfig.CONSUMER_SECRET;
        String credentials = consumerKey + ":" + consumerSecret;
        String authHeader = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

        byte[] data = credentials.getBytes(StandardCharsets.ISO_8859_1);

        String base = Base64.encodeToString(data, Base64.DEFAULT);
        System.out.println("base code: " + base);
        Log.d("Interceptor code: ", authHeader);


        mApiClient.mpesaService().getAccessToken(authHeader).enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                mProgressDialog.dismiss();
                if (response.isSuccessful()) {
                    String accessToken = response.body().getAccessToken();
                    Log.d("MainActivity", "Access Token: " + accessToken);
                    mApiClient.setAuthToken(accessToken).setGetAccessToken(false);
                    performSTKPush();
                } else {
                    Log.e("MainActivity", "Access Token Error: " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                mProgressDialog.dismiss();
                t.printStackTrace();
            }
        });
    }

    private void performSTKPush() {
        String phone_number = mPhone.getText().toString().trim();
        String amount = mAmount.getText().toString().trim();
        String timestamp = Utils.getTimestamp();
        String password = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            password = Utils.getPassword(BUSINESS_SHORT_CODE, PASSKEY, timestamp);
            Log.d("mainacti", "performSTKPush: password: " + password);
        }

        STKPush stkPush = new STKPush(
                BUSINESS_SHORT_CODE,
                password,
                timestamp,
                TRANSACTION_TYPE,
                amount,
                Utils.sanitizePhoneNumber(phone_number),
                PARTYB,
                Utils.sanitizePhoneNumber(phone_number),
                CALLBACKURL,
                "Mpesa_STKPUSH",
                "Testing"
        );


        System.out.println(stkPush);

        mApiClient.mpesaService().sendPush(stkPush).enqueue(new Callback<STKPush>() {
            @Override
            public void onResponse(Call<STKPush> call, Response<STKPush> response) {
                mProgressDialog.dismiss();
                if (response.isSuccessful()) {
                    Log.d("MainActivity", "Post submitted to API " + response.body());
                } else {
                    try {
                        Log.e("MainActivity", "STKPush Error: " + response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<STKPush> call, Throwable t) {
                mProgressDialog.dismiss();
                t.printStackTrace();
            }
        });
    }


}