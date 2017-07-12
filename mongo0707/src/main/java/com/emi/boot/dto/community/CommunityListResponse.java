package com.emi.boot.dto.community;

import com.emi.boot.dto.RestResponse;
import com.emi.boot.dto.community.vo.CommunityVo;
import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * Created by emi on 2017/6/19.
 */
@ApiModel()
public class CommunityListResponse extends RestResponse{

    private List<CommunityVo> communityVoList;

    public CommunityListResponse() {
    }

    public CommunityListResponse(List<CommunityVo> communityVoList) {
        this.communityVoList = communityVoList;
    }

    public List<CommunityVo> getCommunityVoList() {
        return communityVoList;
    }

    public void setCommunityVoList(List<CommunityVo> communityVoList) {
        this.communityVoList = communityVoList;
    }
}
