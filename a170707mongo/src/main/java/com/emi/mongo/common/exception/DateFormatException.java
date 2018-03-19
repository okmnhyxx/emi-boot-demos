package com.emi.mongo.common.exception;

import com.emi.mongo.common.ErrorCode;

import java.text.SimpleDateFormat;

/**
 * Created by emi on 2016/5/17.
 */
public class DateFormatException extends RestException {
    public DateFormatException(SimpleDateFormat sdf, String dateString) {
        super(ErrorCode.DATE_FORMAT_EXCEPTION, "日期格式错误", "日期格式错误,需要格式：" + sdf.toPattern() + "实际格式：" + dateString);
    }
}
