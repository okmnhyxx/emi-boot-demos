package com.emi.boot.domain;

import com.emi.boot.common.exception.CommonException;

import java.util.Date;

/**
 * Created by emi on 2017/6/8.
 */
public class HireInfo extends BaseDomain {

    private String memberId;

    private String orderId;

    private Date hireBeginTime;
    private Date hireEndTime;
    private String hireTimeStr;

    private boolean hired;

    private int amounts;//金额

    private String baseCommunityId;
    private String communityId;
    private String community;

    private String carLocationId;
    private String carLocation;

    public HireInfo() {
        this.hired = false;
    }

    public HireInfo(String memberId, Date hireBeginTime, Date hireEndTime, String hireTimeStr, int amounts, String baseCommunityId, String communityId, String community, String carLocationId, String carLocation) {
        this();
        this.memberId = memberId;
        this.hireBeginTime = hireBeginTime;
        this.hireEndTime = hireEndTime;
        this.hireTimeStr = hireTimeStr;
        this.amounts = amounts;
        this.baseCommunityId = baseCommunityId;
        this.communityId = communityId;
        this.community = community;
        this.carLocationId = carLocationId;
        this.carLocation = carLocation;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getHireBeginTime() {
        return hireBeginTime;
    }

    public void setHireBeginTime(Date hireBeginTime) {
        this.hireBeginTime = hireBeginTime;
    }

    public Date getHireEndTime() {
        return hireEndTime;
    }

    public void setHireEndTime(Date hireEndTime) {
        this.hireEndTime = hireEndTime;
    }

    public String getHireTimeStr() {
        return hireTimeStr;
    }

    public void setHireTimeStr(String hireTimeStr) {
        this.hireTimeStr = hireTimeStr;
    }

    public boolean isHired() {
        return hired;
    }

    public void setHired(boolean hired) {
        this.hired = hired;
    }

    public int getAmounts() {
        return amounts;
    }

    public void setAmounts(int amounts) {
        this.amounts = amounts;
    }

    public String getBaseCommunityId() {
        return baseCommunityId;
    }

    public void setBaseCommunityId(String baseCommunityId) {
        this.baseCommunityId = baseCommunityId;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getCarLocationId() {
        return carLocationId;
    }

    public void setCarLocationId(String carLocationId) {
        this.carLocationId = carLocationId;
    }

    public String getCarLocation() {
        return carLocation;
    }

    public void setCarLocation(String carLocation) {
        this.carLocation = carLocation;
    }

    public void checkNotHired() {
        if (this.hired) {
            throw new CommonException("sorry，该车位已有人抢先下单了", String.format("出租信息[%s]已被下单了", getId()));
        }
    }

    public void hiredOrder(String ordersId) {
        this.hired = true;
        this.orderId = ordersId;
    }

    public void cancelHire() {
        this.hired = false;
        this.orderId = "";
    }
}
