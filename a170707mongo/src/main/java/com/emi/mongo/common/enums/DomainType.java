package com.emi.mongo.common.enums;

/**
 * Created by emi on 2017/3/13.
 */
public enum DomainType implements DomainParam{

    Member("用户"),
    Orders("订单"),
    HireInfo("出租信息"),
    Community("小区"),
    CarLocation("车位"),
    BaseCommunity("小区基础数据")
    ;

    private String desc;

    DomainType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
