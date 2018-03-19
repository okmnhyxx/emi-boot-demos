package com.emi.mongo.common.exception;


import com.emi.mongo.common.ErrorCode;

/**
 * Created by emi on 2016/11/29.
 */
public class EnumIllegalArgumentException extends RestException {

    public EnumIllegalArgumentException(String className, int value) {
        super(ErrorCode.ENUM_ILLEGAL_ARGS, "枚举参数不存在", String.format("枚举%s不存在该值%d", className, value));
    }
    public EnumIllegalArgumentException(Class className, String value) {
        super(ErrorCode.ENUM_ILLEGAL_ARGS, "枚举参数不存在", String.format("枚举%s不存在该值%s", className.getName(), value));
    }

    public EnumIllegalArgumentException(Class className, int value) {
        super(ErrorCode.ENUM_ILLEGAL_ARGS, "枚举参数不存在", String.format("枚举%s不存在该值%d", className.getName(), value));
    }

}
