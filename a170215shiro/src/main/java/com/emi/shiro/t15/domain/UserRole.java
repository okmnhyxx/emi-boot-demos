package com.emi.shiro.t15.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by emi on 2017/2/16.
 */
@Entity
@Table(name = "t15_user_role")
public class UserRole {


    @Id
    @GeneratedValue
    private long id;

    private long userId;

    private long roleId;

    public UserRole() {
    }

    public UserRole(long userId, long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
