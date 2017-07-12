package com.emi.boot.common.enums;

import com.emi.boot.common.exception.EnumIllegalArgumentException;

/**
 * Created by emi on 2017/6/8.
 */
public enum  SmsType {
    Login(1, "登录"),
    ;

    private int value;

    private String desc;

    public static SmsType valueOf(int value) {
        switch (value) {
            case 1:
                return Login;
            default:
                throw new EnumIllegalArgumentException(SmsType.class, value);
        }
    }

    SmsType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
