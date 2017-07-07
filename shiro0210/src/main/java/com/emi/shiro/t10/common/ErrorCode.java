package com.emi.shiro.t10.common;

/**
 * Created by emi on 2016/5/10.
 */
public interface ErrorCode {

    int CODE_400 = 900400;
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

    int COMMON_ERROR = 200000;//通用异常
    int REST_HTTP = 200001;//远程请求异常
    int ENUM_ILLEGAL_ARGS = 200002;//枚举参数异常
    int DATE_FORMAT_EXCEPTION = 200003;//日期格式化出错

    int NO_QUALIFIED_RECORD=200004;//没有符合条件的记录

    int No_THIS_USER=200005;//用户不存在

    int PASSWORD_ERRO=200006;//密码错误

    int ADMIN_NAME_EXISTED=200007;//用户名已存在

    int PHONE_EXISTED=200008;//手机号已存在

    int EMAIL_EXISTED=200009;//邮箱号已存在

    int ADMIN_USER_UNABLE=200010;//账号被禁用

    int UN_SELECTED=200011;//未选中操作对象

    int USER_NAME_NULL=200012;//管理员用户名为空

    int PHONE_NULL=200013;//管理员手机为空

    int EMAIL_NULL=200014;//管理员邮箱为空

    int UN_SUPPORTED_OPERATION=200015;//不支持的非法操作，针对修改商家状态的

    int STORE_NAME_EXISTED=200016;//商家名称已存在

    int STORE_NAME_NULL=200017;//商家名称不能为空

    int STORE_USERNAME_EXIST=200018;//商家账号不能为空

    int AUDIT_REASON_NULL=200019;//审核理由不能为空

    int PASSWORD_NULL=200020;//密码不能为空

    int EASEMOD_ERROR=200021;

}
