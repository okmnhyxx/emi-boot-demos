package com.emi.mongo.common.enums;

import com.emi.mongo.common.exception.EnumIllegalArgumentException;

/**
 * Created by emi on 2017/6/26.
 */
public enum PayType {

    WinXin(1, "微信"),
    Alipay(2, "支付宝")
    ;

    private int value;

    private String desc;

    PayType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static PayType valueOf(int value) {
        switch (value) {
            case 0:
                return PayType.WinXin;
            case 1:
                return PayType.Alipay;
            default:
                throw new EnumIllegalArgumentException(HireState.class, value);
        }
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
