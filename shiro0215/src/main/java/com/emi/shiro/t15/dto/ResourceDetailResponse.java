package com.emi.shiro.t15.dto;

import com.emi.shiro.t15.dto.common.RestResponse;
import com.emi.shiro.t15.dto.vo.ResourceVisibleVo;

/**
 * Created by emi on 2017/3/1.
 */
public class ResourceDetailResponse extends RestResponse {

    private ResourceVisibleVo resourceVo;

    public ResourceDetailResponse() {
    }

    public ResourceDetailResponse(ResourceVisibleVo resourceVo) {
        this.resourceVo = resourceVo;
    }

    public ResourceVisibleVo getResourceVo() {
        return resourceVo;
    }

    public void setResourceVo(ResourceVisibleVo resourceVo) {
        this.resourceVo = resourceVo;
    }
}
