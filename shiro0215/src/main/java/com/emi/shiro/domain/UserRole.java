package com.emi.shiro.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by emi on 2017/2/16.
 */
@Entity
@Table(name = "t15_user_role")
public class UserRole extends BaseDomain {

    private long userId;

    private long roleId;

    public UserRole() {
    }

    public UserRole(long userId, long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }
}
