package com.emi.boot.dto.hire.vo;

import com.emi.boot.common.util.DateUtils;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by emi on 2017/6/19.
 */
public class HireBaseVo {

    private String id;

    @ApiModelProperty("金额")
    private int amounts;//金额

    @ApiModelProperty("发布的出租时间段")
    private String hireTimeStr;

    @ApiModelProperty("发布时间，格式 yyyy-MM-dd HH:mm:ss")
    private String createTimeStr;

    @ApiModelProperty("是否租出去了， true租出去了  false未租出")
    private boolean hired;

    public HireBaseVo() {
    }

    public HireBaseVo(String id, int amounts, String hireTimeStr, Date createTime, boolean hired) {
        this.id = id;
        this.amounts = amounts;
        this.hireTimeStr = hireTimeStr;
        this.createTimeStr = DateUtils.sdf1.format(createTime);
        this.hired = hired;
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

    public String getHireTimeStr() {
        return hireTimeStr;
    }

    public void setHireTimeStr(String hireTimeStr) {
        this.hireTimeStr = hireTimeStr;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public boolean isHired() {
        return hired;
    }

    public void setHired(boolean hired) {
        this.hired = hired;
    }
}
