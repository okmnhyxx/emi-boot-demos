package com.emi.shiro.t15.configs;

/**
 * Created by emi on 2016/5/10.
 */
public interface ErrorCode {

    int CODE_400 = 900400;
    int CODE_403 = 900403;
    int CODE_404 = 900404;
    int CODE_405 = 900405;
    int CODE_408 = 900408;
    int CODE_414 = 900414;//URI（通常为网址）过长
    int CODE_415 = 900415;//媒体类型不支持
    int CODE_500 = 900500;
    int USER_PERMISSION_DENIED = 900401;

    int SERVER_INTERNAL_EXCEPTION = 100000;//服务器内部异常
    int EXCEPTION_IN_EXCEPTION = 100001;//捕获异常中的异常
    int BIND_EXCEPTION = 100002;//端口被占用
    int CONNECT_REFUSE = 100003;//连接拒绝
    int CONNECT_TIMEOUT = 100004;//连接超时

    int COMMON_ERROR = 200000;
    int REST_HTTP = 200001;//远程请求异常
    int ENUM_ILLEGAL_ARGS = 200002;//枚举参数异常
    int DATE_FORMAT_EXCEPTION = 200003;//日期格式化出错
    int RECORD_NOT_FOUND = 200004;//记录不存在
    int RECORD_HAS_DELETE = 200005;//数据已被删除
    int RECORD_NOT_ENABLE = 200006;//数据未启用
    int RECORD_STATUS_ERROR = 200007;//状态异常
    int RECORD_NOT_BELONG = 200008;//归属异常
    int AMOUNTS_ERROR = 200009;//金额异常
    int AMOUNTS_PATTERN_ERROR = 200010;//金额格式异常
    int SMS_NOT_EXIST = 200011;//验证码不存在
    int SMS_TIME_OUT = 200012;//验证码超时
    int SMS_ERROR = 200013;//验证码错误
    int SMS_SHOULD_NOT_NULL = 200014;//验证码错误

    int EASEMOD_ERROR = 230000;
    int OAUTH_WEIXIN_ERROR = 230001;
    int OAUTH_QQ_ERROR = 230002;

}
