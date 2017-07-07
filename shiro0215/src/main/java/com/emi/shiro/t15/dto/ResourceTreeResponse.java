package com.emi.shiro.t15.dto;

import com.emi.shiro.t15.dto.common.RestResponse;
import com.emi.shiro.t15.dto.vo.ResourceTreeVo;

import java.util.List;

/**
 * Created by emi on 2017/3/3.
 */
public class ResourceTreeResponse extends RestResponse {

    private List<ResourceTreeVo> resourceVoList;

    public ResourceTreeResponse() {
    }

    public ResourceTreeResponse(List<ResourceTreeVo> resourceVoList) {
        this.resourceVoList = resourceVoList;
    }

    public List<ResourceTreeVo> getResourceVoList() {
        return resourceVoList;
    }

    public void setResourceVoList(List<ResourceTreeVo> resourceVoList) {
        this.resourceVoList = resourceVoList;
    }
}
