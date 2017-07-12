package com.emi.boot.common.exception;


import com.emi.boot.common.ErrorCode;
import com.emi.boot.common.enums.DomainParam;

/**
 * Created by emi on 2016/12/26.
 */
public class ValidateLengthException extends RestException {
    public ValidateLengthException(DomainParam domainParam, String validStr, int length, boolean greaterThen) {
        super(ErrorCode.CODE_400, String.format("%s长度不%s%d位", domainParam.getDesc(), greaterThen?"超过":"少于", length) ,
                String.format("%s[%s]长度不得%s%d位", domainParam.getDesc(), greaterThen?"超过":"少于", validStr, length));
    }
}
