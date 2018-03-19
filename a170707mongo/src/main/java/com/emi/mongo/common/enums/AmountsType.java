package com.emi.mongo.common.enums;

import com.emi.mongo.common.exception.EnumIllegalArgumentException;

/**
 * Created by emi on 2017/6/12.
 * 1:提现  2:支付订单  3:取消订单违约金  4:超时违约金  5:退还押金
 * 21:充值  22:出租成功  23:收入取消订单违约金  24:收入超时违约金  25:支付押金
 */
public enum AmountsType {

    WithdrawCash(1, "提现"),
    PayOrder(2, "支付订单"),
    PayCancel(3, "支付超时取消订单违约金"),
    PayOutTime(4, "支付超时违约金"),
    GainDeposit(5, "退还押金"),

    Recharge(21, "充值"),
    GainOrder(22, "出租成功"),
    GainCancel(23, "收入超时取消订单违约金"),
    GainOutTime(24, "收入超时违约金"),
    PayDeposit(25, "充值押金"),
    ;

    private int value;

    private String desc;

    public static AmountsType valueOf(int value) {
        switch (value) {
            case 1:
                return AmountsType.WithdrawCash;
            case 2:
                return AmountsType.PayOrder;
            case 3:
                return AmountsType.PayCancel;
            case 4:
                return AmountsType.PayOutTime;
            case 5:
                return AmountsType.PayDeposit;

            case 21:
                return AmountsType.Recharge;
            case 22:
                return AmountsType.GainOrder;
            case 23:
                return AmountsType.GainCancel;
            case 24:
                return AmountsType.GainOutTime;
            case 25:
                return AmountsType.GainDeposit;

            default:
                throw new EnumIllegalArgumentException(AmountsType.class, value);
        }
    }

    AmountsType(int value, String desc) {
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
