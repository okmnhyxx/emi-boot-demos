package com.emi.shiro.t15.dto.vo;

/**
 * Created by emi on 2017/3/6.
 */
public class RoleDetailVo {

    private long id;

    private String roleName;

    private String roleDesc;

    public RoleDetailVo() {
    }

    public RoleDetailVo(long id, String roleName, String roleDesc) {
        this.id = id;
        this.roleName = roleName;
        this.roleDesc = roleDesc;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
}
