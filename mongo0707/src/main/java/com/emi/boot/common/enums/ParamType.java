package com.emi.boot.common.enums;

/**
 * Created by emi on 2016/12/23.
 */
public enum ParamType implements DomainParam {

    Phone("手机"),
    Amounts("金额"),
    CarLocation("车位号"),
    OrderNo("订单号")
    ;

    private String desc;

    ParamType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
