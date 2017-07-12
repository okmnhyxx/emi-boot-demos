package com.emi.boot.dto.member;

import com.emi.boot.dto.RestResponse;
import com.emi.boot.dto.member.vo.MemberVo;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by emi on 2017/6/14.
 */
public class MemberListResponse extends RestResponse {

    @ApiModelProperty("待审核的用户列表")
    private List<MemberVo> memberVoList;

    public MemberListResponse() {
    }

    public MemberListResponse(List<MemberVo> memberVoList) {
        this.memberVoList = memberVoList;
    }

    public List<MemberVo> getMemberVoList() {
        return memberVoList;
    }

    public void setMemberVoList(List<MemberVo> memberVoList) {
        this.memberVoList = memberVoList;
    }
}
