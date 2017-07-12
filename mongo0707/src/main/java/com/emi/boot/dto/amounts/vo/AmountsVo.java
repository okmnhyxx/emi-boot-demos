package com.emi.boot.dto.amounts.vo;

import com.emi.boot.common.enums.AmountsType;
import com.emi.boot.common.util.DateUtils;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by emi on 2017/6/27.
 */
public class AmountsVo {

    @ApiModelProperty("明细id")
    private String id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("true:进账   false:出账")
    private boolean income;

    @ApiModelProperty("金额")
    private int amounts;

    @ApiModelProperty("生成时间")
    private String createTime;

    public AmountsVo() {
    }

    public AmountsVo(String id, int amountsType, String title, boolean income, int amounts, Date createTime) {
        this.id = id;
        this.title = AmountsType.valueOf(amountsType).getDesc() + " " + title;
        this.income = income;
        this.amounts = amounts;
        this.createTime = DateUtils.sdf2.format(createTime);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
