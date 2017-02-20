package com.emi.shiro.common.enums;

/**
 * Created by emi on 2017/2/15.
 */
public enum ViewType {

    NotCertain(0,"未知"),
    Root(1,"根节点"),
    Menu(2,"菜单"),
    Button(3,"按钮")
    ;

    private int value;
    private String decs;

    private ViewType(int value, String decs) {
        this.value = value;
        this.decs = decs;
    }

    public static ViewType valueOf(int value) {
        switch (value) {
            case 0:
                return ViewType.NotCertain;
            case 1:
                return ViewType.Root;
            case 2:
                return ViewType.Menu;
            case 3:
                return ViewType.Button;
            default:
                throw new IllegalArgumentException(String.format("value:%s is illegal value", value));
        }
    }

    public int getValue() {
        return value;
    }

    public String getDecs() {
        return decs;
    }
}
