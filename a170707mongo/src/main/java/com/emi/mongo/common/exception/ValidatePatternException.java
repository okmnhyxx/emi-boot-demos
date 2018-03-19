package com.emi.mongo.common.exception;

import com.emi.mongo.common.ErrorCode;
import com.emi.mongo.common.enums.DomainParam;

/**
 * Created by emi on 2016/12/23.
 */
public class ValidatePatternException extends RestException {
    public ValidatePatternException(DomainParam paramDomain, String validStr) {
        super(ErrorCode.CODE_400, paramDomain.getDesc() + "参数格式异常", String.format("%s参数[%s]格式异常", paramDomain.getDesc(), validStr));
    }
}
