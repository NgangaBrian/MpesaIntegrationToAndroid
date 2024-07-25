package com.example.mpesaintegrationtoandroid.Model;

import com.google.gson.annotations.SerializedName;

public class STKPush {
    @SerializedName("BusinessShortCode")
    private String businessShortCode;
    @SerializedName("Password")
    private String password;
    @SerializedName("Timestamp")
    private String timestamp;
    @SerializedName("TransactionType")
    private String transactionType;
    @SerializedName("Amount")
    private String amount;
    @SerializedName("PartyA")
    private String partyA;
    @SerializedName("PartyB")
    private String partyB;
    @SerializedName("PhoneNumber")
    private String phoneNumber;
    @SerializedName("CallBackURL")
    private String callBackUrl;
    @SerializedName("AccountReference")
    private String accountReference;
    @SerializedName("TransactionDesc")
    private String transactionDesc;

    public STKPush(String businessShortCode, String password, String timestamp, String transactionType, String amount,
                   String partyA, String partyB, String phoneNumber, String callBackUrl, String accountReference,
                   String transactionDesc){
        this.businessShortCode = businessShortCode;
        this.password = password;
        this.timestamp = timestamp;
        this.transactionType = transactionType;
        this.amount = amount;
        this.partyA = partyA;
        this.partyB = partyB;
        this.phoneNumber = phoneNumber;
        this.callBackUrl = callBackUrl;
        this.accountReference = accountReference;
        this.transactionDesc = transactionDesc;
    }

    @Override
    public String toString() {
        return "STKPush{" +
                "businessShortCode='" + businessShortCode + '\'' +
                ", password='" + password + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", amount='" + amount + '\'' +
                ", partyA='" + partyA + '\'' +
                ", partyB='" + partyB + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", callBackUrl='" + callBackUrl + '\'' +
                ", accountReference='" + accountReference + '\'' +
                ", transactionDesc='" + transactionDesc + '\'' +
                '}';
    }
}
