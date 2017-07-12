package com.emi.boot.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by emi on 2017/6/7.
 */
@ApiModel
public class RestResponse {

    @ApiModelProperty(value = "是否成功")
    private boolean success;

    private int errorCode;

    private String errorMsg;

    public RestResponse() {
        super();
        this.success = true;
        this.errorCode = 0;
        this.errorMsg = "";
    }

    public RestResponse(int errorCode, String errorMsg) {
        super();
        this.success = false;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
