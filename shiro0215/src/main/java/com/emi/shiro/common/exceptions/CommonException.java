package com.emi.shiro.common.exceptions;

/**
 * Created by emi on 2016/12/12.
 */
public class CommonException extends RestException {

    public CommonException(int code, String message, String debugMessage) {
        super(code, message, debugMessage);
    }
}
