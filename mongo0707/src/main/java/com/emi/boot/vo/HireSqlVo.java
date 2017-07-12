package com.emi.boot.vo;

import java.util.Date;

/**
 * Created by emi on 2017/6/16.
 */
public class HireSqlVo {

    private String id;

    private int amounts;//金额

    private boolean hired;

    private String hireTimeStr;

    private Date createTime;

    private String memberId;
    private String realName;
    private int sex;

    private String communityId;
    private String community;

    private String carLocationId;
    private String carLocation;

    public HireSqlVo() {
    }

    public HireSqlVo(String id, int amounts, boolean hired, String hireTimeStr, Date createTime, String memberId, String communityId, String community, String carLocationId, String carLocation) {
        this.id = id;
        this.amounts = amounts;
        this.hired = hired;
        this.hireTimeStr = hireTimeStr;
        this.createTime = createTime;
        this.memberId = memberId;
        this.communityId = communityId;
        this.community = community;
        this.carLocationId = carLocationId;
        this.carLocation = carLocation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAmounts() {
        return amounts;
    }

    public void setAmounts(int amounts) {
        this.amounts = amounts;
    }

    public boolean isHired() {
        return hired;
    }

    public void setHired(boolean hired) {
        this.hired = hired;
    }

    public String getHireTimeStr() {
        return hireTimeStr;
    }

    public void setHireTimeStr(String hireTimeStr) {
        this.hireTimeStr = hireTimeStr;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
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
}
