package com.emi.mongo.dto.community;

import com.emi.mongo.dto.RestResponse;
import com.emi.mongo.dto.community.vo.CommunityVo;
import com.emi.mongo.dto.member.vo.MemberVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by emi on 2017/6/13.
 */
@ApiModel
public class CommunityAddResponse extends RestResponse {

    @ApiModelProperty("小区信息")
    private CommunityVo communityVo;

    @ApiModelProperty("用户基础信息，首次添加时，memberVo不为空，app要更新本地用户信息")
    private MemberVo memberVo;

    public CommunityAddResponse() {
    }

    public CommunityAddResponse(CommunityVo communityVo, MemberVo memberVo) {
        this.communityVo = communityVo;
        this.memberVo = memberVo;
    }

    public CommunityVo getCommunityVo() {
        return communityVo;
    }

    public void setCommunityVo(CommunityVo communityVo) {
        this.communityVo = communityVo;
    }

    public MemberVo getMemberVo() {
        return memberVo;
    }

    public void setMemberVo(MemberVo memberVo) {
        this.memberVo = memberVo;
    }
}
