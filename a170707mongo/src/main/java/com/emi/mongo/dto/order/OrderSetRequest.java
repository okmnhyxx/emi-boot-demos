package com.emi.mongo.dto.order;

import com.emi.mongo.common.exception.CommonException;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by emi on 2017/6/19.
 */
public class OrderSetRequest {

    @ApiModelProperty(value = "出租商品Id", required = true)
    @NotBlank(message = "出租信息不能为空")
    private String hireId;

    @ApiModelProperty(value = "租用时间起，格式HH:mm, exp: 09:00", required = true)
    @NotBlank(message = "租用开始时间不能为空")
    private String beginTime;

    @ApiModelProperty(value = "租用时间止，格式HH:mm, exp: 18:00", required = true)
    @NotBlank(message = "租用结束时间不能为空")
    private String endTime;

    @ApiModelProperty(value = "车牌号", required = true)
    @NotBlank(message = "车牌号不能为空")
    private String license;

    public String getHireId() {
        return hireId;
    }

    public void setHireId(String hireId) {
        this.hireId = hireId;
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

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }


    /**
     * 判断并纠正时间
     */
    public void checkTimes() {
        int timeCheck = beginTime.compareTo(endTime);
        if (timeCheck == 0) {
            throw new CommonException("租用起止时间不能相同");
        } else if (timeCheck == 1){
            String tmp = beginTime;
            beginTime = endTime;
            endTime = tmp;
        }
    }
}
