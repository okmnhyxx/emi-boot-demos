package com.emi.boot.dto.hire;

import com.emi.boot.common.exception.CommonException;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.StringUtils;

import java.util.Calendar;

/**
 * Created by emi on 2017/6/16.
 */
public class HireListRequest {

    @ApiModelProperty(value = "基础数据 小区Id", required = true)
    @NotBlank(message = "基础小区不能为空")
    private String baseCommunityId;

    @ApiModelProperty("出租时间起，格式HH:mm, exp: 09:00")
    private String beginTime;

    @ApiModelProperty("出租时间止，格式HH:mm, exp: 18:00")
    private String endTime;


    public String getBaseCommunityId() {
        return baseCommunityId;
    }

    public void setBaseCommunityId(String baseCommunityId) {
        this.baseCommunityId = baseCommunityId;
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

    public void checkTimes() {
        if (!StringUtils.isEmpty(beginTime) && !StringUtils.isEmpty(endTime)) {
            int timeCheck = beginTime.compareTo(endTime);
            if (timeCheck == 0) {
                throw new CommonException("出租起止时间不能相同");
            } else if (timeCheck == 1){
                String tmp = beginTime;
                beginTime = endTime;
                endTime = tmp;
            }
        }
        if (!StringUtils.isEmpty(endTime)) {
            if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= Integer.parseInt(endTime.substring(0, endTime.indexOf(":")))) {
                throw new CommonException("截止时间不得小于等于当前时间");
            }
        }
    }
}
