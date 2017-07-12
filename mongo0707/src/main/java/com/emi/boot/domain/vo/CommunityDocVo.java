package com.emi.boot.domain.vo;

import com.emi.boot.domain.Community;

/**
 * Created by emi on 2017/7/11.
 */
public class CommunityDocVo {

    private String id;

    private String name;

    private String address;

    public CommunityDocVo(Community community) {
        this.id = community.getId();
        this.name = community.getBaseCommunity().getName();
        this.address = community.getBaseCommunity().getAddress();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
