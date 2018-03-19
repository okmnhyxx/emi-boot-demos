package com.emi.shiro.t15.domain;

import com.emi.shiro.t15.common.enums.DomainType;
import com.emi.shiro.t15.common.exceptions.RecordStatusException;

import javax.persistence.*;

/**
 * Created by emi on 2017/2/10.
 */
@Entity
@Table(name = "t15_role")
public class Role extends BaseDomain {

    private long updaterId;

    private String roleName;

    private String roleDesc;

    private boolean enable = Boolean.TRUE;

//    @ManyToMany(fetch=FetchType.EAGER)
//    @JoinTable(name = "t15_role_permission", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = {
//            @JoinColumn(name = "permission_id") })
//    private List<Long> permissionList;// 一个角色对应多个权限


    public Role() {
        this.enable = true;
    }

    public Role(long updaterId, String roleName, String roleDesc) {
        this.updaterId = updaterId;
        this.roleName = roleName;
        this.roleDesc = roleDesc;
    }

    public long getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(long updaterId) {
        this.updaterId = updaterId;
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

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }


    public void checkEnable() {
        if (!this.enable) {
            throw new RecordStatusException(this.enable, DomainType.Role, getId(), true);
        }
    }

    public void update(long updaterId, String roleName, String roleDesc) {
        this.updaterId = updaterId;
        this.roleName = roleName;
        this.roleDesc = roleDesc;
    }
}
