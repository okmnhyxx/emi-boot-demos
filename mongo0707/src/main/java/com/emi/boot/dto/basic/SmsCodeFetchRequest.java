package com.emi.boot.dto.basic;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;

/**
 * Created by emi on 2017/6/8.
 */
@ApiModel
public class SmsCodeFetchRequest {

    @ApiModelProperty(value = "短信类型 1：登录", allowableValues = "1", required = true)
    @Min(value = 1, message = "短信类型不能为空")
    private int smsType;

    @ApiModelProperty(value = "手机号", required = true)
    @NotBlank(message = "手机号码不能为空")
    private String phone;

    public int getSmsType() {
        return smsType;
    }

    public void setSmsType(int smsType) {
        this.smsType = smsType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
