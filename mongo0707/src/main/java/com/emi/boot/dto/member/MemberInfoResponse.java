package com.emi.boot.dto.member;

import com.emi.boot.dto.RestResponse;
import com.emi.boot.dto.member.vo.MemberVo;

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
