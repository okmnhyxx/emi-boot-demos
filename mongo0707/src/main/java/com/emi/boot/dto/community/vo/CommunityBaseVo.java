package com.emi.boot.dto.community.vo;

import com.emi.boot.domain.Community;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by emi on 2017/6/15.
 */
public class CommunityBaseVo {

    @ApiModelProperty(value = "小区Id")
    private String id;

    @ApiModelProperty(value = "小区")
    private String name;

    public CommunityBaseVo() {
    }

    public CommunityBaseVo(String id, String name) {
        this.id = id;
        this.name = name;
    }
    public CommunityBaseVo(Community community) {
        this.id = community.getId();
        this.name = community.getBaseCommunity().getName();
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
}
