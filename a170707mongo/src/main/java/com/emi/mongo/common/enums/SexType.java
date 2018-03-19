package com.emi.mongo.common.enums;

import com.emi.mongo.common.exception.EnumIllegalArgumentException;

/**
 * Created by emi on 2017/6/13.
 */
public enum SexType {

    UnCertain(0, "未知"),
    Boy(1, "男"),
    Girl(2, "女");

    private int value;

    private String desc;

    SexType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static SexType valueOf(int value) {
        switch (value) {
            case 0:
                return SexType.UnCertain;
            case 1:
                return SexType.Boy;
            case 2:
                return SexType.Girl;
            default:
                throw new EnumIllegalArgumentException(SexType.class, value);
        }
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
