package com.emi.mongo.common.enums;

import com.emi.mongo.common.exception.EnumIllegalArgumentException;

/**
 * Created by emi on 2017/6/13.
 */
public enum ValidState {

    UnCertain(0, "未知"),
    Wait(1, "待验证"),
    Pass(2, "通过"),
    Failed(3, "未通过"),
    UserInit(4, "新手");

    private int value;

    private String desc;

    ValidState(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static ValidState valueOf(int value) {
        switch (value) {
            case 0:
                return ValidState.UnCertain;
            case 1:
                return ValidState.Wait;
            case 2:
                return ValidState.Pass;
            case 3:
                return ValidState.Failed;
            case 4:
                return ValidState.UserInit;
            default:
                throw new EnumIllegalArgumentException(ValidState.class, value);
        }
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
