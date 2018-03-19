package com.emi.mongo.common.exception;

/**
 * Created by emi on 2016/5/11.
 */
public class RestException extends RuntimeException {
    private int code;
    private String debugMessage;

    public RestException() {
    }

    public RestException(int code, String message) {
        super(message);
        this.code = code;
        this.debugMessage = message;
    }

    public RestException(int code, String message, String debugMessage) {
        super(message);
        this.code = code;
        this.debugMessage = debugMessage;
    }

    public int getCode() {
        return this.code;
    }

    public String getDebugMessage() {
        return this.debugMessage;
    }
}
