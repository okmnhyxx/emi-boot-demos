package com.emi.mongo.dto.recharge;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Created by emi on 2017/6/26.
 */
public class MemberRechargeRequest {

    @ApiModelProperty("充值金额")
    @Min(value = 1, message = "充值金额不能低于1元")
    private int amounts;

    @ApiModelProperty("是否充值押金")
    private boolean deposit;

    @ApiModelProperty("支付方式   1：微信 2：支付宝")
    @Min(value = 1, message = "支付方式错误")
    @Max(value = 2, message = "支付方式错误")
    private int payType;

    public int getAmounts() {
        return amounts;
    }

    public void setAmounts(int amounts) {
        this.amounts = amounts;
    }

    public boolean isDeposit() {
        return deposit;
    }

    public void setDeposit(boolean deposit) {
        this.deposit = deposit;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }
}
