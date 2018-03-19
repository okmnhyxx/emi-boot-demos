package com.emi.shiro.t15.common.enums;

/**
 * Created by emi on 2017/2/23.
 */
public enum OperateType {

    Create(1,"新增","create"),
    Retrieve(2, "查询", "retrieve"),
    Update(3,"修改", "update"),
    Delete(4,"删除", "delete"),
    Other(5, "混合其他", "other")
    ;

    private int value;
    private String desc;
    private String code;

    OperateType(int value, String desc, String code) {
        this.value = value;
        this.desc = desc;
        this.code = code;
    }

    public static OperateType valueOf(int value) {
        switch (value) {
            case 1:
                return OperateType.Create;
            case 2:
                return OperateType.Retrieve;
            case 3:
                return OperateType.Update;
            case 4:
                return OperateType.Delete;
            case 5:
                return OperateType.Other;
            default:
                throw new IllegalArgumentException(String.format("value:%s is illegal value", value));
        }
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public String getCode() {
        return code;
    }
}
