package com.emi.mongo.dto.community;

import com.emi.mongo.dto.member.vo.MemberAccomplishReqVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;

/**
 * Created by emi on 2017/6/13.
 */
@ApiModel
public class CommunityAddRequest {

    @ApiModelProperty(value = "基础数据 小区Id", required = true)
    @NotBlank(message = "基础小区不能为空")
    private String baseCommunityId;

//    @ApiModelProperty(value = "小区", required = true)
//    private String communityName;

    @ApiModelProperty(value = "楼号/楼层/房号", required = true)
    @NotBlank(message = "楼号不能为空")
    private String buildingNo;

    @Valid
    @ApiModelProperty(value = "app自行判断用户名身份证是否为空，空的话该vo不能为空，不空的话不需要传入")
    private MemberAccomplishReqVo memberVo;

    public String getBaseCommunityId() {
        return baseCommunityId;
    }

    public void setBaseCommunityId(String baseCommunityId) {
        this.baseCommunityId = baseCommunityId;
    }

    public String getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }

    public MemberAccomplishReqVo getMemberVo() {
        return memberVo;
    }

    public void setMemberVo(MemberAccomplishReqVo memberVo) {
        this.memberVo = memberVo;
    }
}
