package com.emi.shiro.t15.dto.vo;

/**
 * Created by emi on 2017/3/7.
 */
public class RoleBaseVo {

    private long id;

    private String roleName;

    public RoleBaseVo() {
    }

    public RoleBaseVo(long id, String roleName) {
        this.id = id;
        this.roleName = roleName;
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
}
