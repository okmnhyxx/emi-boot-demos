package com.emi.mongo.dto.member;

import com.emi.mongo.dto.RestResponse;
import com.emi.mongo.dto.member.vo.MemberVo;

/**
 * Created by emi on 2017/6/7.
 */
public class MemberInfoResponse extends RestResponse {

    private MemberVo memberVo;


    public MemberInfoResponse() {
    }

    public MemberInfoResponse(MemberVo memberVo) {
        this.memberVo = memberVo;
    }

    public MemberVo getMemberVo() {
        return memberVo;
    }

    public void setMemberVo(MemberVo memberVo) {
        this.memberVo = memberVo;
    }
}
