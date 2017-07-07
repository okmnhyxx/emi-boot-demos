package com.emi.shiro.t15.dto;

import com.emi.shiro.t15.dto.common.RestResponse;
import com.emi.shiro.t15.dto.vo.ResourceTreeVo;
import com.emi.shiro.t15.dto.vo.RoleDetailVo;

import java.util.List;

/**
 * Created by emi on 2017/3/6.
 */
public class RoleDetailResponse extends RestResponse {

    private RoleDetailVo roleVo;

    private List<ResourceTreeVo> resourceVoList;

    public RoleDetailResponse() {
    }

    public RoleDetailResponse(RoleDetailVo roleVo, List<ResourceTreeVo> resourceVoList) {
        this.roleVo = roleVo;
        this.resourceVoList = resourceVoList;
    }

    public RoleDetailVo getRoleVo() {
        return roleVo;
    }

    public void setRoleVo(RoleDetailVo roleVo) {
        this.roleVo = roleVo;
    }

    public List<ResourceTreeVo> getResourceVoList() {
        return resourceVoList;
    }

    public void setResourceVoList(List<ResourceTreeVo> resourceVoList) {
        this.resourceVoList = resourceVoList;
    }
}
