package com.emi.boot.domain;

/**
 * Created by emi on 2017/6/19.
 */
public class BaseCommunity extends BaseDomain {

    private String name;

    private String area;

    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
