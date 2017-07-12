package com.emi.boot.dto.hire.vo;

import com.emi.boot.dto.community.vo.CommunityBaseVo;
import com.emi.boot.dto.location.vo.LocationBaseVo;
import com.emi.boot.dto.member.vo.MemberBaseVo;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by emi on 2017/6/15.
 */
public class HireVo {
    @ApiModelProperty("出租信息")
    private HireBaseVo hireVo;

    @ApiModelProperty("出租人信息")
    private MemberBaseVo lessorVo;

    @ApiModelProperty("小区id和name")
    private CommunityBaseVo communityVo;

    @ApiModelProperty("车位id和name")
    private LocationBaseVo locationVo;

    public HireVo() {
    }

    public HireVo(HireBaseVo hireVo, MemberBaseVo lessorVo, CommunityBaseVo communityVo, LocationBaseVo locationVo) {
        this.hireVo = hireVo;
        this.lessorVo = lessorVo;
        this.communityVo = communityVo;
        this.locationVo = locationVo;
    }

    public HireBaseVo getHireVo() {
        return hireVo;
    }

    public void setHireVo(HireBaseVo hireVo) {
        this.hireVo = hireVo;
    }

    public MemberBaseVo getLessorVo() {
        return lessorVo;
    }

    public void setLessorVo(MemberBaseVo lessorVo) {
        this.lessorVo = lessorVo;
    }

    public CommunityBaseVo getCommunityVo() {
        return communityVo;
    }

    public void setCommunityVo(CommunityBaseVo communityVo) {
        this.communityVo = communityVo;
    }

    public LocationBaseVo getLocationVo() {
        return locationVo;
    }

    public void setLocationVo(LocationBaseVo locationVo) {
        this.locationVo = locationVo;
    }
}
