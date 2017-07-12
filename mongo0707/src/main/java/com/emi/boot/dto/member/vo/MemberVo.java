package com.emi.boot.dto.member.vo;

import com.emi.boot.common.enums.ValidState;
import com.emi.boot.common.util.CommonUtils;
import com.emi.boot.domain.Member;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by emi on 2017/6/13.
 */
@ApiModel
public class MemberVo {

    private String id;

    private String phone;

    @ApiModelProperty("性别，0:未知；1:男；2:女")
    private int sex;

    @ApiModelProperty("姓氏，exmp：张先生 吴女士")
    private String realName;

    @ApiModelProperty("身份证")
    private String idCard;

    @ApiModelProperty("押金")
    private int deposit;

    @ApiModelProperty("余额")
    private int balance;

    @ApiModelProperty("冻结金额")
    private int freezeAmounts;

    @ApiModelProperty("姓名身份证验证状态int类型：1:待验证  2:通过  3:未通过  4:新手(仅注册，还未去填写身份证和真实姓名)")
    private int validState;

//    @Transient
    @ApiModelProperty("姓名身份证验证状态string类型：1:待验证  2:通过  3:未通过 4:新手(仅注册，还未去填写身份证和真实姓名)")
    private String validStateStr;

    public MemberVo() {
    }

    public MemberVo(Member member, boolean showRealName) {
        this.id = member.getId();
        this.phone = member.getPhone();
        this.realName = showRealName ? member.getRealName() : CommonUtils.fetchNameCall(member.getRealName(), member.getSex());
        this.idCard = member.getIdCard();
        this.sex = member.getSex();
        this.deposit = member.getDeposit();
        this.balance = member.getBalance();
        this.freezeAmounts = member.getFreezeAmounts();
        this.validState = member.getValidState();
        this.validStateStr = ValidState.valueOf(validState).getDesc();
    }

    public MemberVo(String id, String phone, String realName, String idCard, int sex, int deposit, int balance, int freezeAmounts, int validState) {
        this.id = id;
        this.phone = phone;
        System.out.println("??????????????????????????????????");
        this.realName = true ? realName : CommonUtils.fetchNameCall(realName, sex);
        this.idCard = idCard;
        this.sex = sex;
        this.deposit = deposit;
        this.balance = balance;
        this.freezeAmounts = freezeAmounts;
        this.validState = validState;
        this.validStateStr = ValidState.valueOf(validState).getDesc();
    }

//    public MemberVo(String id, String phone, String realName, String idCard, int sex, int deposit, int balance, int freezeAmounts, int validState, boolean showRealName) {
//        this.id = id;
//        this.phone = phone;
//        this.realName = showRealName ? realName : CommonUtils.fetchNameCall(realName, sex);
//        this.idCard = idCard;
//        this.sex = sex;
//        this.deposit = deposit;
//        this.balance = balance;
//        this.freezeAmounts = freezeAmounts;
//        this.validState = validState;
//        this.validStateStr = ValidState.valueOf(validState).getDesc();
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

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

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getFreezeAmounts() {
        return freezeAmounts;
    }

    public void setFreezeAmounts(int freezeAmounts) {
        this.freezeAmounts = freezeAmounts;
    }

    public int getValidState() {
        return validState;
    }

    public void setValidState(int validState) {
        this.validState = validState;
    }

    public String getValidStateStr() {
        return validStateStr;
    }

    public void setValidStateStr(String validStateStr) {
        this.validStateStr = validStateStr;
    }
}
