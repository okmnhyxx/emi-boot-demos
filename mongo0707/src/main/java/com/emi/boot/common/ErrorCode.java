package com.emi.boot.common;

/**
 * Created by emi on 2016/5/10.
 */
public interface ErrorCode {

    int CODE_400 = 900400;
    int CODE_404 = 900404;
    int CODE_405 = 900405;
    int CODE_500 = 900500;
    int USER_PERMISSION_DENIED = 900401;
    int COMMON_ERROR = 900000;

    int REST_BEFORE_HTTP = 200000;//请求发送异常
    int REST_HTTP = 200001;//远程请求异常
    int ENUM_ILLEGAL_ARGS = 200002;//枚举参数异常
    int DATE_FORMAT_EXCEPTION = 200003;//日期格式化出错
    int RECORD_NOT_FOUND = 200004;//记录不存在
    int RECORD_STATUS_ERROR = 200007;//状态异常
    int DOMAIN_NOT_BELONGS = 200008;//用户身份不符 或 从属关系不符

    int AMOUNTS_ERROR = 200009;//金额异常
    int AMOUNTS_PATTERN_ERROR = 200010;//金额格式异常



}
