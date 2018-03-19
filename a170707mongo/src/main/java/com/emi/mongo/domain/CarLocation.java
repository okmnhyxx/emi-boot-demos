package com.emi.mongo.domain;

import com.emi.mongo.common.enums.DomainType;
import com.emi.mongo.common.exception.RecordStatusException;
import com.emi.mongo.domain.vo.CommunityDocVo;
import com.emi.mongo.domain.vo.MemberDocVo;

/**
 * Created by emi on 2017/6/12.
 */
public class CarLocation extends BaseDomain {

    private MemberDocVo memberVo;

    private CommunityDocVo communityVo;

    private String name;

    private int validState = 1;//1:待验证  2:通过  3:未通过

    public CarLocation() {
    }

    public CarLocation(Member member, Community community, String name) {
        this.memberVo = new MemberDocVo(member);
        this.communityVo = new CommunityDocVo(community);
        this.name = name;
    }

    public MemberDocVo getMemberVo() {
        return memberVo;
    }

    public void setMemberVo(MemberDocVo memberVo) {
        this.memberVo = memberVo;
    }

    public CommunityDocVo getCommunityVo() {
        return communityVo;
    }

    public void setCommunityVo(CommunityDocVo communityVo) {
        this.communityVo = communityVo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        throw new RecordStatusException(validState, DomainType.CarLocation, getId(), states);
    }
}
