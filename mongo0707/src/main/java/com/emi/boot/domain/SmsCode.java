package com.emi.boot.domain;

/**
 * Created by emi on 2017/6/12.
 */
public class SmsCode extends BaseDomain {

    private String phone;

    private String code;

    private int smsType;//1:登录

    private boolean realSms;

    public SmsCode() {
    }

    public SmsCode(String phone, String code, int smsType, boolean realSms) {
        this.phone = phone;
        this.code = code;
        this.smsType = smsType;
        this.realSms = realSms;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getSmsType() {
        return smsType;
    }

    public void setSmsType(int smsType) {
        this.smsType = smsType;
    }

    public boolean isRealSms() {
        return realSms;
    }

    public void setRealSms(boolean realSms) {
        this.realSms = realSms;
    }
}
