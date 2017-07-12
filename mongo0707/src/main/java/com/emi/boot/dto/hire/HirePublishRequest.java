package com.emi.boot.dto.hire;

import com.emi.boot.common.exception.CommonException;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by emi on 2017/6/15.
 */
public class HirePublishRequest {

    @ApiModelProperty(value = "小区Id", required = true)
    @NotBlank(message = "小区不能为空")
    private String communityId;

    @ApiModelProperty(value = "车位Id", required = true)
    @NotBlank(message = "车位不能为空")
    private String carLocationId;

    @ApiModelProperty(value = "出租时间起，格式HH:mm, exp: 09:00", required = true)
    @NotBlank(message = "出租开始时间不能为空")
    private String beginTime;

    @ApiModelProperty(value = "出租时间止，格式HH:mm, exp: 18:00", required = true)
    @NotBlank(message = "出租结束时间不能为空")
    private String endTime;

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getCarLocationId() {
        return carLocationId;
    }

    public void setCarLocationId(String carLocationId) {
        this.carLocationId = carLocationId;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * 判断并纠正时间
     */
    public void checkTimes() {
        int timeCheck = beginTime.compareTo(endTime);
        if (timeCheck == 0) {
            throw new CommonException("出租起止时间不能相同");
        } else if (timeCheck == 1){
            String tmp = beginTime;
            beginTime = endTime;
            endTime = tmp;
        }
    }
}
