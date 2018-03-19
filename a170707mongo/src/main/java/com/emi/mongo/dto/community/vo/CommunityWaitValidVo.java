package com.emi.mongo.dto.community.vo;

import com.emi.mongo.domain.Community;
import com.emi.mongo.dto.member.vo.MemberBaseVo;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by emi on 2017/6/19.
 */
public class CommunityWaitValidVo {

    @ApiModelProperty("小区信息")
    private CommunityVo communityVo;

    @ApiModelProperty("用户信息")
    private MemberBaseVo memberVo;

    public CommunityWaitValidVo() {
    }

    public CommunityWaitValidVo(Community community) {
        this.communityVo = new CommunityVo(community);
        this.memberVo = new MemberBaseVo(community.getMemberVo(), true);
    }

    public CommunityVo getCommunityVo() {
        return communityVo;
    }

    public void setCommunityVo(CommunityVo communityVo) {
        this.communityVo = communityVo;
    }

    public MemberBaseVo getMemberVo() {
        return memberVo;
    }

    public void setMemberVo(MemberBaseVo memberVo) {
        this.memberVo = memberVo;
    }
}
