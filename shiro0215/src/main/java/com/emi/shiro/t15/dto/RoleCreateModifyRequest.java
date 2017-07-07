package com.emi.shiro.t15.dto;

/**
 * Created by emi on 2017/3/3.
 */
public class RoleCreateModifyRequest {

    private String roleName;

    private String roleDesc;

    private String resourceIds;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }
}
