package com.emi.shiro.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by emi on 2017/2/16.
 */
@Entity
@Table(name = "t15_role_permission")
public class RolePermission extends BaseDomain {

    private long roleId;

    private long permissionId;

    public RolePermission() {
    }

    public RolePermission(long roleId, long permissionId) {
        this.roleId = roleId;
        this.permissionId = permissionId;
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
