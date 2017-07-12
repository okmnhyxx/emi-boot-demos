package com.emi.boot.dto.community.vo;

import com.emi.boot.dto.member.vo.MemberBaseVo;
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

    public CommunityWaitValidVo(CommunityVo communityVo, MemberBaseVo memberVo) {
        this.communityVo = communityVo;
        this.memberVo = memberVo;
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
