package com.emi.boot.dto.basic;

import com.emi.boot.dto.RestResponse;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by emi on 2017/6/7.
 */
public class SmsCodeFetchResponse extends RestResponse {

    @ApiModelProperty("可供该手机登录的验证码")
    private String smsCode;

    public SmsCodeFetchResponse() {
    }

    public SmsCodeFetchResponse(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
}
