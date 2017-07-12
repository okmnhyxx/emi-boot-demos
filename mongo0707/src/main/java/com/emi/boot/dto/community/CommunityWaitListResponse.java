package com.emi.boot.dto.community;

import com.emi.boot.dto.RestResponse;
import com.emi.boot.dto.community.vo.CommunityWaitValidVo;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by emi on 2017/6/13.
 */
public class CommunityWaitListResponse extends RestResponse {

    @ApiModelProperty("小区列表， 包含各种状态的")
    private List<CommunityWaitValidVo> communityVoList;

    public CommunityWaitListResponse() {
    }

    public CommunityWaitListResponse(List<CommunityWaitValidVo> communityVoList) {
        this.communityVoList = communityVoList;
    }

    public List<CommunityWaitValidVo> getCommunityVoList() {
        return communityVoList;
    }

    public void setCommunityVoList(List<CommunityWaitValidVo> communityVoList) {
        this.communityVoList = communityVoList;
    }
}
