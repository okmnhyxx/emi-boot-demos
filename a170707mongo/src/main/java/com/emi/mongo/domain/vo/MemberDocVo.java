package com.emi.mongo.domain.vo;

import com.emi.mongo.domain.Member;

/**
 * Created by emi on 2017/7/11.
 */
public class MemberDocVo {

    private String id;

    private String realName;

    private int sex;//1:男  2:女

    private String portrait;

    public MemberDocVo() {
    }

    public MemberDocVo(Member member) {
        this.id = member.getId();
        this.realName = member.getRealName();
        this.sex = member.getSex();
        this.portrait = member.getPortrait();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
}
