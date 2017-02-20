package com.emi.shiro.dto.vo;

import java.util.List;

/**
 * Created by emi on 2017/2/17.
 */
public class UserListVo {

    private long id;

    private String username;

    private boolean locked;

    private List<String> roleNameList;

    public UserListVo() {
    }

    public UserListVo(long id, String username, boolean locked, List<String> roleNameList) {
        this.id = id;
        this.username = username;
        this.locked = locked;
        this.roleNameList = roleNameList;
    }

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

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public List<String> getRoleNameList() {
        return roleNameList;
    }

    public void setRoleNameList(List<String> roleNameList) {
        this.roleNameList = roleNameList;
    }
}
