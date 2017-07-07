package com.emi.shiro.t15.dto.vo;

import java.util.List;

/**
 * Created by emi on 2017/2/17.
 */
public class UserListVo {

    private long id;

    private String username;

    private String organizationName;

    private String createTime;

    private boolean locked;

    private List<RoleBaseVo> roleVoList;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public List<RoleBaseVo> getRoleVoList() {
        return roleVoList;
    }

    public void setRoleVoList(List<RoleBaseVo> roleVoList) {
        this.roleVoList = roleVoList;
    }
}
