package com.emi.shiro.t15.dto;

import com.emi.shiro.t15.dto.common.RestResponse;
import com.emi.shiro.t15.dto.vo.ResourceVo;

import java.util.List;

/**
 * Created by emi on 2017/2/24.
 */
public class ResourceListResponse extends RestResponse {

    private List<ResourceVo> resourceVoList;

    public ResourceListResponse() {
    }

    public ResourceListResponse(List<ResourceVo> resourceVoList) {
        this.resourceVoList = resourceVoList;
    }

    public List<ResourceVo> getResourceVoList() {
        return resourceVoList;
    }

    public void setResourceVoList(List<ResourceVo> resourceVoList) {
        this.resourceVoList = resourceVoList;
    }
}
