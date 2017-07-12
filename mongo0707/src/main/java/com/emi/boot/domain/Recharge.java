package com.emi.boot.domain;

/**
 * Created by emi on 2017/6/19.
 */
public class Recharge extends BaseDomain {

    private String memberId;

    private int amount;

    private boolean deposit;

    private int payType;// 1：微信 2：支付宝

    public Recharge() {
    }

    public Recharge(String memberId, int amount, boolean deposit, int payType) {
        this.memberId = memberId;
        this.amount = amount;
        this.deposit = deposit;
        this.payType = payType;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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
