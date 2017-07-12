package com.emi.boot.domain;

import com.emi.boot.common.enums.DomainType;
import com.emi.boot.common.exception.RecordStatusException;
import com.emi.boot.domain.vo.MemberDocVo;

/**
 * Created by emi on 2017/6/8.
 */
public class Community extends BaseDomain {

    private MemberDocVo memberVo;

    private BaseCommunity baseCommunity;

    private String buildingNo;//楼号房号

    private int validState = 1;//1:待验证  2:通过  3:未通过

    public Community() {
    }

    public Community(Member member, BaseCommunity baseCommunity, String buildingNo) {
        this.memberVo = new MemberDocVo(member);
        this.baseCommunity = baseCommunity;
        this.buildingNo = buildingNo;
    }

    public MemberDocVo getMemberVo() {
        return memberVo;
    }

    public void setMemberVo(MemberDocVo memberVo) {
        this.memberVo = memberVo;
    }

    public BaseCommunity getBaseCommunity() {
        return baseCommunity;
    }

    public void setBaseCommunity(BaseCommunity baseCommunity) {
        this.baseCommunity = baseCommunity;
    }

    public String getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }

    public int getValidState() {
        return validState;
    }

    public void setValidState(int validState) {
        this.validState = validState;
    }

    public void checkState(int... states) {
        for (int s : states) {
            if (this.validState == s) {
                return;
            }
        }
        throw new RecordStatusException(validState, DomainType.Community, getId(), states);
    }
}
