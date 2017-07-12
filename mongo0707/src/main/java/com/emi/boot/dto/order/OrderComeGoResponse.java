package com.emi.boot.dto.order;

import com.emi.boot.dto.RestResponse;
import com.emi.boot.dto.order.vo.OrderRunningVo;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by emi on 2017/6/22.
 */
public class OrderComeGoResponse extends RestResponse {

    @ApiModelProperty("是否准许进入，或者是否准许出库")
    private boolean canComeGo;

    @ApiModelProperty("扫码结果的语言描述， app可以toast或者展示在扫码结果页面")
    private String resultMsg;

    @ApiModelProperty("该订单的信息")
    private OrderRunningVo orderRunningVo;

    public OrderComeGoResponse() {
    }

    public OrderComeGoResponse(boolean canComeGo, String resultMsg, OrderRunningVo orderRunningVo) {
        this.canComeGo = canComeGo;
        this.resultMsg = resultMsg;
        this.orderRunningVo = orderRunningVo;
    }

    public boolean isCanComeGo() {
        return canComeGo;
    }

    public void setCanComeGo(boolean canComeGo) {
        this.canComeGo = canComeGo;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public OrderRunningVo getOrderRunningVo() {
        return orderRunningVo;
    }

    public void setOrderRunningVo(OrderRunningVo orderRunningVo) {
        this.orderRunningVo = orderRunningVo;
    }
}
