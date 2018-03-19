package com.emi.mongo.dto.member.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Created by emi on 2017/6/13.
 */
@ApiModel
public class MemberAccomplishReqVo {

    @NotBlank(message = "真实姓名不能为空")
    private String realName;

    @NotBlank(message = "身份证不能为空")
    @ApiModelProperty(value = "身份证", required = true)
    private String idCard;

    @ApiModelProperty(value = "性别 1:男;  2:女", required = true)
    @Min(value = 1, message = "性别错误")
    @Max(value = 2, message = "性别错误")
    private int sex;//1:男  2:女

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
