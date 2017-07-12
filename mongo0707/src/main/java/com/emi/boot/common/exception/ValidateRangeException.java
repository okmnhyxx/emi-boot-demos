package com.emi.boot.common.exception;

import com.emi.boot.common.ErrorCode;
import com.emi.boot.common.enums.ParamType;

/**
 * Created by emi on 2016/12/26.
 */
public class ValidateRangeException extends RestException {

    public ValidateRangeException(ParamType paramType, int validNum, int minOrMaxRange, boolean ifMin) {
        super(ErrorCode.CODE_400, String.format("%s数值范围异常", paramType.getDesc()), String.format("%s[%d]不得%s%d", paramType.getDesc(), validNum, ifMin ? "小于" : "大于", minOrMaxRange));
    }

    public ValidateRangeException(ParamType paramType, double validNum, double minOrMaxRange, boolean ifMin) {
        super(ErrorCode.CODE_400, String.format("%s数值范围异常", paramType.getDesc()), String.format("%s[%f]不得%s%f", paramType.getDesc(), validNum, ifMin ? "小于" : "大于", minOrMaxRange));
    }
}
