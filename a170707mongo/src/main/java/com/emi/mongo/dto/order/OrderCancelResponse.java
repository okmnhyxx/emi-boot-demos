package com.emi.mongo.dto.order;

import com.emi.mongo.dto.RestResponse;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by emi on 2017/6/22.
 */
public class OrderCancelResponse extends RestResponse {

    @ApiModelProperty("取消订单结果通知，比如“取消成功”，“超过10分钟取消订单，余额已扣款10元”")
    private String resultMsg;

    public OrderCancelResponse() {
    }

    public OrderCancelResponse(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}
