package com.emi.shiro.t15.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by emi on 2017/2/16.
 */
@Entity
@Table(name = "t15_role_permission")
public class RolePermission {


    @Id
    @GeneratedValue
    private long id;

    private long roleId;

    private long permissionId;

    public RolePermission() {
    }

    public RolePermission(long roleId, long permissionId) {
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(long permissionId) {
        this.permissionId = permissionId;
    }
}
