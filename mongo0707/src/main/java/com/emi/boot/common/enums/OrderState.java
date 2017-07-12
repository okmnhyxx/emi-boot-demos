package com.emi.boot.common.enums;

import com.emi.boot.common.exception.EnumIllegalArgumentException;

/**
 * Created by emi on 2017/6/12.
 */
public enum OrderState {

    WaitCome(1, "未入库"),
//    WaitPay(2, "待支付"),
    Using(3, "租用中"),
    AlreadyGo(4, "已出库"),
    AlreadyOut(5, "已超时"),
    Cancel(11, "已取消"),
    CancelWithFine(12, "已取消(超时)"),
    MissHire(13, "爽约"),
    ;

    private int value;

    private String desc;

    OrderState(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static OrderState valueOf(int value) {
        switch (value) {
            case 1:
                return OrderState.WaitCome;
//            case 2:
//                return OrderState.WaitPay;
            case 3:
                return OrderState.Using;
            case 4:
                return OrderState.AlreadyGo;
            case 5:
                return OrderState.AlreadyOut;
            case 11:
                return OrderState.Cancel;
            case 12:
                return OrderState.CancelWithFine;
            case 13:
                return OrderState.MissHire;
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
