package com.emi.mongo.dto.location.vo;

import com.emi.mongo.common.enums.ValidState;
import com.emi.mongo.common.util.DateUtils;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by emi on 2017/6/14.
 */
public class LocationVo {

    @ApiModelProperty("车位Id")
    private String id;

    @ApiModelProperty("车位号")
    private String name;

    @ApiModelProperty("车位验证状态int:  1:待验证  2:通过  3:未通过")
    private int validState;
    @ApiModelProperty("车位验证状态String:  1:待验证  2:通过  3:未通过")
    private String validStateStr;


    @ApiModelProperty("创建时间，格式 yyyy-MM-dd HH:mm:ss")
    private String createTimeStr;

    public LocationVo() {
    }

    public LocationVo(String id, String name, int validState, Date createTime) {
        this.id = id;
        this.name = name;
        this.validState = validState;
        this.validStateStr = ValidState.valueOf(validState).getDesc();
        this.createTimeStr = DateUtils.sdf1.format(createTime);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }
}
