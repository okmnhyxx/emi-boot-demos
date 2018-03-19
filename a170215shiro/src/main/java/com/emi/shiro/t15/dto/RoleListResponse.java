package com.emi.shiro.t15.dto;

import com.emi.shiro.t15.dto.common.RestResponse;
import com.emi.shiro.t15.dto.vo.RoleListVo;

import java.util.List;

/**
 * Created by emi on 2017/3/3.
 */
public class RoleListResponse extends RestResponse {

    private List<RoleListVo> roleVoList;

    public RoleListResponse() {
    }

    public RoleListResponse(List<RoleListVo> roleVoList) {
        this.roleVoList = roleVoList;
    }



    public List<RoleListVo> getRoleVoList() {
        return roleVoList;
    }

    public void setRoleVoList(List<RoleListVo> roleVoList) {
        this.roleVoList = roleVoList;
    }
}
