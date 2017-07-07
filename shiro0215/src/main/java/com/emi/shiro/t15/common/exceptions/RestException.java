package com.emi.shiro.t15.common.exceptions;

/**
 * Created by emi on 2017/2/18.
 */
public abstract class RestException extends RuntimeException {
    private int code;
    private String debugMessage;

    public RestException(int code, String message, String debugMessage) {
        super(message);
        this.code = code;
        this.debugMessage = null != debugMessage && !"".equals(debugMessage)?debugMessage:message;
    }

    public int getCode() {
        return this.code;
    }

    public String getDebugMessage() {
        return this.debugMessage;
    }
}
