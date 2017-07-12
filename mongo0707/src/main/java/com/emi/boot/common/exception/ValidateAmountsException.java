package com.emi.boot.common.exception;


import com.emi.boot.common.ErrorCode;

/**
 * Created by emi on 2017/1/6.
 */
public class ValidateAmountsException extends RestException {
    public ValidateAmountsException(double amounts) {
        super(ErrorCode.AMOUNTS_PATTERN_ERROR, "金额格式异常", "金额[" + amounts + "]格式异常");
    }
}
