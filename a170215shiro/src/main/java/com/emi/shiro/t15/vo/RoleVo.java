package com.emi.shiro.t15.vo;

import java.util.Set;

/**
 * Created by emi on 2017/2/16.
 */
public class RoleVo {

    private Set<Long> roleIds;

    private Set<String> roleNames;

    public RoleVo() {
    }

    public RoleVo(Set<Long> roleIds, Set<String> roleNames) {
        this.roleIds = roleIds;
        this.roleNames = roleNames;
    }

    public Set<Long> getRoleIds() {
        return roleIds;
    }

    public Set<String> getRoleNames() {
        return roleNames;
    }
}
