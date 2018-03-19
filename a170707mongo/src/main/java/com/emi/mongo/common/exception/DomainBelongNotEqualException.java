package com.emi.mongo.common.exception;

import com.emi.mongo.common.ErrorCode;
import com.emi.mongo.common.enums.DomainType;

/**
 * Created by emi on 2017/6/14.
 */
public class DomainBelongNotEqualException extends RestException  {

    public DomainBelongNotEqualException(DomainType domainType, String domainId, String supposeMemberId, String transMemberId) {
        super(ErrorCode.DOMAIN_NOT_BELONGS, domainType.getDesc() + "用户身份不符", String.format("%s[%s]期望用户[%s]，实际传入用户[%s]", domainType.getDesc(), domainId, supposeMemberId, transMemberId));
    }

    public DomainBelongNotEqualException(DomainType slaveryDomain, String slaveryId, DomainType masterDomain, String supposeMasterId, String transMasterId) {
        super(ErrorCode.DOMAIN_NOT_BELONGS, slaveryDomain + "从属信息异常", String.format("%s[%s]期望%s[%s]，实际传入[%s]", slaveryDomain.getDesc(), slaveryId, masterDomain.getDesc(), supposeMasterId, transMasterId));
    }
}
