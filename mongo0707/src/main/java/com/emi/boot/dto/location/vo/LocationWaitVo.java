package com.emi.boot.dto.location.vo;

import com.emi.boot.dto.community.vo.CommunityBaseVo;
import com.emi.boot.dto.member.vo.MemberBaseVo;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by emi on 2017/6/19.
 */
public class LocationWaitVo {


    @ApiModelProperty("车位 id name 和创建时间")
    private LocationVo locationVo;

    @ApiModelProperty("小区id和name")
    private CommunityBaseVo communityVo;

    @ApiModelProperty("用户信息")
    private MemberBaseVo memberVo;

    public LocationWaitVo() {
    }

    public LocationWaitVo(LocationVo locationVo, CommunityBaseVo communityVo, MemberBaseVo memberVo) {
        this.locationVo = locationVo;
        this.communityVo = communityVo;
        this.memberVo = memberVo;
    }

    public LocationVo getLocationVo() {
        return locationVo;
    }

    public void setLocationVo(LocationVo locationVo) {
        this.locationVo = locationVo;
    }

    public CommunityBaseVo getCommunityVo() {
        return communityVo;
    }

    public void setCommunityVo(CommunityBaseVo communityVo) {
        this.communityVo = communityVo;
    }

    public MemberBaseVo getMemberVo() {
        return memberVo;
    }

    public void setMemberVo(MemberBaseVo memberVo) {
        this.memberVo = memberVo;
    }
}
