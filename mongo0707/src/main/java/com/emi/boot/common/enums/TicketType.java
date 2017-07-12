package com.emi.boot.common.enums;

import com.emi.boot.common.exception.EnumIllegalArgumentException;

/**
 * Created by emi on 2017/6/19.
 */
public enum TicketType {

    OutTimeCancel(1, "取消订单超时"),
    OutTimeGo(2, "出库超时"),
    ;

    private int value;

    private String desc;

    TicketType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static TicketType valueOf(int value) {
        switch (value) {
            case 1:
                return TicketType.OutTimeCancel;
            case 2:
                return TicketType.OutTimeGo;
            default:
                throw new EnumIllegalArgumentException(OrderState.class, value);
        }
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
