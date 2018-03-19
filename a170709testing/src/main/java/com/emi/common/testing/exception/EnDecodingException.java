package com.emi.common.testing.exception;

/**
 * Created by emi on 2017/6/15.
 */
public class EnDecodingException extends RestException {
    public EnDecodingException(boolean encode, String content, String charset) {
        super(ErrorCode.ENCODE_DECODE_ERROR, encode ? "编码失败" : "解码失败",
                "[" + charset + "][" + content + "]" + (encode ? "编码失败" : "解码失败"));
    }
}
