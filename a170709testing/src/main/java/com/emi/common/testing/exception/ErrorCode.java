package com.emi.common.testing.exception;

/**
 * Created by emi on 2016/5/10.
 */
public interface ErrorCode {

    int CODE_500 = 900500;
    int CODE_400 = 900400;
    int COMMON_ERROR = 100000;

    int REST_BEFORE_HTTP = 200000;//请求发送异常
    int REST_HTTP = 200001;//远程请求异常
    int REST_RESPONSE_ERROR = 200002;//远程请求异常
    int ENUM_ILLEGAL_ARGS = 200004;//枚举参数异常
    int DATE_FORMAT_EXCEPTION = 200005;//日期格式化出错
    int RECORD_NOT_FOUND = 200006;//记录不存在
    int RECORD_STATUS_ERROR = 200007;//状态异常

    int AMOUNTS_ERROR = 200009;//金额异常
    int AMOUNTS_PATTERN_ERROR = 200010;//金额格式异常
    int XML_CONVERT_ERROR = 200011;//xml与java之间转换失败
    int ENCODE_DECODE_ERROR = 200012;//url编码 解码失败

    //System Error.
    int Login_Error = 100100;
    int SMS_Send_Error = 100101;
    int Login_Code_Error = 100102;
    int Login_Code_Empty_Error = 100103;
    int Token_Not_Exists_Error = 100104;
    int Token_Error = 100105;
    int Token_Expired_Error = 100106;

    // Task Error.
    int No_Suitable_Task = 200101;
    int Task_Accept_LoadGroup_Error = 200102;
    int Task_Start_Verification_Code_Error = 200104;
    int Task_Finished_Verification_Code_Error = 200105;
    int Task_Real_Count_Error = 200106;

    //Load Group Error.
    int Load_Group_Dinner_Times_Exception = 300101;
    int Load_Group_Not_Idle_Error = 300102;
    int Load_Group_Mobile_Not_Exists = 300103;

    //Load Exception Error.
    int Load_Exception_Accepted_Error = 400101;
    int Load_Exception_State_Error = 400102;
    int Load_Exception_Processor_Error = 400103;



}
