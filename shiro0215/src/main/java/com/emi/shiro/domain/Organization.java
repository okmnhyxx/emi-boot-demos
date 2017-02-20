package com.emi.shiro.domain;

import javax.persistence.Entity;

/**
 * Created by emi on 2017/2/15.
 */
@Entity(name = "t15_organization")
public class Organization extends BaseDomain {

    private Long parentId;

    private String name;

    private String ancestorsId;

    private boolean enable;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getAncestorsId() {
        return ancestorsId;
    }

    public void setAncestorsId(String ancestorsId) {
        this.ancestorsId = ancestorsId;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
