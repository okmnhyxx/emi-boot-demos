package com.emi.boot.domain;

/**
 * Created by emi on 2017/6/12.
 */
public class AmountsDetail extends BaseDomain {

    private String memberId;

    private String domainId;

    private boolean income;//收入

    private int amounts;

    private String title;

    private int amountsType;//- 1:提现  2:支付订单  3:取消订单违约金  4:超时违约金  5:退还押金
                            //+ 21:充值  22:出租成功  23:收入取消订单违约金  24:收入超时违约金  25:支付押金


    public AmountsDetail() {
    }

    public AmountsDetail(String memberId, String domainId, boolean income, int amounts, String title, int amountsType) {
        this.memberId = memberId;
        this.domainId = domainId;
        this.income = income;
        this.amounts = amounts;
        this.title = title;
        this.amountsType = amountsType;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    public boolean isIncome() {
        return income;
    }

    public void setIncome(boolean income) {
        this.income = income;
    }

    public int getAmounts() {
        return amounts;
    }

    public void setAmounts(int amounts) {
        this.amounts = amounts;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAmountsType() {
        return amountsType;
    }

    public void setAmountsType(int amountsType) {
        this.amountsType = amountsType;
    }
}
