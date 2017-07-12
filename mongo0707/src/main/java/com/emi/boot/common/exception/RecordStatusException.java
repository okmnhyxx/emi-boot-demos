package com.emi.boot.common.exception;

import com.emi.boot.common.ErrorCode;
import com.emi.boot.common.enums.DomainType;

import java.util.Arrays;

/**
 * Created by emi on 2016/12/1.
 */
public class RecordStatusException extends RestException {
    public RecordStatusException(int realState, DomainType domainType, String domainId, int[] supposeState) {
        super(ErrorCode.RECORD_STATUS_ERROR, domainType.getDesc() + "当前状态不支持该操作",
                String.format("%s[%s]合理状态：%s，实际传入状态：%d", domainType.getDesc(), domainId, Arrays.toString(supposeState), realState));
    }
}
