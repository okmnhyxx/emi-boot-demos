package com.emi.boot.dto.member.vo;

import com.emi.boot.common.util.CommonUtils;
import com.emi.boot.domain.Member;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by emi on 2017/6/15.
 */
public class MemberBaseVo {

    private String id;

    private String realName;

    @ApiModelProperty("根据性别 决定头像，0:未知  1:男   2:女")
    private int sex;

    public MemberBaseVo(String id, String realName, int sex, boolean showRealName) {
        this.id = id;
        this.realName = showRealName ? realName : CommonUtils.fetchNameCall(realName, sex);
        this.sex = sex;
    }

    public MemberBaseVo(Member member, boolean showRealName) {
        this.id = member.getId();
        this.realName = showRealName ? member.getRealName() : CommonUtils.fetchNameCall(member.getRealName(), member.getSex());
        this.sex = member.getSex();
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
}
