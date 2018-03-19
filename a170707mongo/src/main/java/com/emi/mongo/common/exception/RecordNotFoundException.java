package com.emi.mongo.common.exception;


import com.emi.mongo.common.ErrorCode;

/**
 * Created by emi on 2016/12/1.
 */
public class RecordNotFoundException extends RestException {
    public RecordNotFoundException(String domainName, Object domainId) {
        super(ErrorCode.RECORD_NOT_FOUND, domainName + "记录不存在", String.format("%s[%s]记录不存在", domainName, domainId));
    }
}
