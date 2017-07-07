package com.emi.shiro.t15.common.enums;

/**
 * Created by emi on 2016/12/1.
 */
public enum DomainType implements DomainParam {

    User("用户"),
    Permission("资源"),
    Role("角色")
    ;

    private String desc;

    DomainType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

}
