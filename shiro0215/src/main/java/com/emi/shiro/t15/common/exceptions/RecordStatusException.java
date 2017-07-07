package com.emi.shiro.t15.common.exceptions;



import com.emi.shiro.t15.common.enums.DomainType;
import com.emi.shiro.t15.configs.ErrorCode;

import java.util.Arrays;

/**
 * Created by emi on 2016/12/1.
 */
public class RecordStatusException extends RestException {
    public RecordStatusException(int realState, DomainType domainType, long domainId, Integer... supposeState) {
        super(ErrorCode.RECORD_STATUS_ERROR, domainType.getDesc() + "当前状态不支持该操作",
                String.format("%s[%d]合理状态：%s，实际传入状态：%d", domainType.getDesc(), domainId, Arrays.toString(supposeState), realState));
    }

    public RecordStatusException(boolean realState, DomainType domainType, long domainId, boolean supposeState) {
        super(ErrorCode.RECORD_STATUS_ERROR, domainType.getDesc() + "当前状态不支持该操作",
                String.format("%s[%d]合理状态：%s，实际传入状态：%s", domainType.getDesc(), domainId, supposeState + "", realState + ""));
    }
}
