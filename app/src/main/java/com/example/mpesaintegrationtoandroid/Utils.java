package com.example.mpesaintegrationtoandroid;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;

public class Utils {
    public static String getTimestamp(){
        return new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date());
    }

    public  static String  sanitizePhoneNumber(String phone){
        if (phone.equals("")){
            return "";
        }

        if (phone.length() < 11 & phone.startsWith("0")){
            String p = phone.replaceFirst("^0", "254");
            return  p;
        }
        if(phone.length() == 13 && phone.startsWith("+")){
            String p = phone.replaceFirst("^+", "");
            return p;
        }
        return phone;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getPassword(String businessShortCode, String passkey, String timestamp){
        String str = businessShortCode + passkey + timestamp;

            return Base64.getEncoder().encodeToString(str.getBytes());

    }
}
