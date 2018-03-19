package com.emi.common.testing.exception;

/**
 * Created by jackie on 17-3-21.
 */
public abstract class RestException extends RuntimeException {

    private int code;
    private String debugMessage;

    public RestException(int code, String message, String debugMessage) {
        super(message);
        this.code = code;
        this.debugMessage = debugMessage;
    }

    public int getCode() {
        return code;
    }

    public String getDebugMessage() {
        return debugMessage;
    }
}
