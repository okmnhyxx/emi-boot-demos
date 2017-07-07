package com.emi.shiro.t15.dto.vo;

import com.emi.shiro.t15.service.utils.ServiceUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by emi on 2017/3/3.
 */
public class RoleListVo {

    private long id;

    private String roleName;

    private String roleDesc;

    private boolean enable;

    private String createTime;

    private List<ResourceBaseVo> resourceVoList;

    public RoleListVo() {
    }

    public RoleListVo(long id, String roleName, String roleDesc, boolean enable, Date createTime) {
        this.id = id;
        this.roleName = roleName;
        this.roleDesc = roleDesc;
        this.enable = enable;
        this.createTime = ServiceUtils.sdf_m.format(createTime);
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

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<ResourceBaseVo> getResourceVoList() {
        return resourceVoList;
    }

    public void setResourceVoList(List<ResourceBaseVo> resourceVoList) {
        this.resourceVoList = resourceVoList;
    }
}
