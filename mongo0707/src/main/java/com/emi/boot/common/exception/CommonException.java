package com.emi.boot.common.exception;

import com.emi.boot.common.ErrorCode;

/**
 * Created by emi on 2016/10/31.
 */
public class CommonException extends RestException {

    public CommonException(String message) {
        super(ErrorCode.COMMON_ERROR, message);
    }

    public CommonException(String message, String debugMessage) {
        super(ErrorCode.COMMON_ERROR, message, debugMessage);
    }

}
