package com.emi.mongo.common.exception;

import com.emi.mongo.common.ErrorCode;
import com.emi.mongo.common.enums.DomainParam;

/**
 * Created by emi on 2016/12/23.
 */
public class ValidateNullException extends RestException {
    public ValidateNullException(DomainParam paramDomain) {
        super(ErrorCode.CODE_400, paramDomain.getDesc() + "参数不能为空", paramDomain.getDesc() + "参数不能为空");
    }

}
