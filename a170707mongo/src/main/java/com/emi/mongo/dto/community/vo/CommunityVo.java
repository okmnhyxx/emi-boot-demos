package com.emi.mongo.dto.community.vo;

import com.emi.mongo.common.enums.ValidState;
import com.emi.mongo.common.util.DateUtils;
import com.emi.mongo.domain.Community;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by emi on 2017/6/13.
 */
@ApiModel
public class CommunityVo {

    @ApiModelProperty(value = "小区Id")
    private String id;

    @ApiModelProperty(value = "小区名字")
    private String name;

    @ApiModelProperty(value = "楼号/楼层/房号")
    private String buildingNo;

    @ApiModelProperty("小区验证状态int：1:待验证  2:通过  3:未通过")
    private int validState;
    @ApiModelProperty("小区验证状态string：1:待验证  2:通过  3:未通过")
    private String validStateStr;

    @ApiModelProperty("创建时间，格式 yyyy-MM-dd HH:mm:ss")
    private String createTimeStr;

    public CommunityVo() {
    }

    public CommunityVo(String id, String name, String buildingNo, int validState, Date createTime) {
        this.id = id;
        this.name = name;
        this.buildingNo = buildingNo;
        this.validState = validState;
        this.validStateStr = ValidState.valueOf(validState).getDesc();
        this.createTimeStr = DateUtils.sdf1.format(createTime);
    }

    public CommunityVo(Community community) {
        this.id = community.getId();
        this.name = community.getBaseCommunity().getName();
        this.buildingNo = community.getBuildingNo();
        this.validState = community.getValidState();
        this.validStateStr = ValidState.valueOf(community.getValidState()).getDesc();
        this.createTimeStr = DateUtils.sdf1.format(community.getCreateTime());
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

    public String getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
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
