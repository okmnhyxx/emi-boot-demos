package com.emi.mongo.common.exception;

import com.emi.mongo.common.ErrorCode;
import org.slf4j.Logger;
import org.springframework.util.StringUtils;

/**
 * Created by emi on 2016/10/31.
 */
public class RestExecuteException extends RestException {

    public RestExecuteException(String message, String detailMessage, Exception e, Logger logger) {
        super(ErrorCode.REST_HTTP, message);
        if (null != logger) {
            logger.error(StringUtils.isEmpty(detailMessage) ? message: message + "，" + detailMessage, e);
        }
    }
}
