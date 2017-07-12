package com.emi.boot.domain;

import java.util.Date;

/**
 * Created by emi on 2017/6/19.
 */
public class Withdraw extends BaseDomain {

    private long memberId;

    private int amount;

    private boolean deposit;//是否提现押金

    private Date arrivalTime;

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
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

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
