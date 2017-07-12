package com.emi.boot.common.enums;

import com.emi.boot.common.exception.EnumIllegalArgumentException;

/**
 * Created by emi on 2017/6/13.
 */
public enum HireState {

    NotHire(1, "未出租"),
    Ordered(2, "已被下单")
    ;

    private int value;

    private String desc;

    HireState(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static HireState valueOf(int value) {
        switch (value) {
            case 0:
                return HireState.NotHire;
            case 1:
                return HireState.Ordered;
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
