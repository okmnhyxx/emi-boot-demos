package com.emi.boot.dto.order;

import com.emi.boot.common.enums.TimeUnitType;
import com.emi.boot.common.exception.CommonException;
import com.emi.boot.common.exception.DateFormatException;
import com.emi.boot.common.util.DateUtils;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by emi on 2017/6/22.
 */
public class OrderComeGoRequest {

    @ApiModelProperty(value = "订单Id", required = true)
    @NotBlank(message = "订单id不能为空")
    private String id;

    @Min(value = 1, message = "订单状态不能为空")
    @Max(value = 3, message = "该订单无需扫码")
    @ApiModelProperty(value = "订单状态 1:未入库  3:租用中", allowableValues = "1,3")
    private int state;

    @ApiModelProperty(value = "当前小时分钟，格式HH:mm，example：14:23")
    private String time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void checkTime() {
        Date date = new Date();
        String qrTimeStr = DateUtils.sdf3.format(date) + " " + time;
        Date qrTime = null;
        try {
            qrTime = DateUtils.sdf2.parse(qrTimeStr);
        } catch (ParseException e) {
            throw new DateFormatException(DateUtils.sdf2, qrTimeStr);
        }
        Date qrTime3MinutesAfter = DateUtils.generateTimeOut(qrTime, TimeUnitType.Minute, 3, true);
        if (qrTime3MinutesAfter.after(date)) {
            throw new CommonException("二维码已过期，请重新生成");
        }
    }
}
