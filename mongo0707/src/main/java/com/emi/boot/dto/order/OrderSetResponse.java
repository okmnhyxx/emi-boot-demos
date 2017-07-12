package com.emi.boot.dto.order;

import com.emi.boot.dto.RestResponse;
import com.emi.boot.dto.order.vo.OrderRunningVo;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by emi on 2017/6/19.
 */
public class OrderSetResponse extends RestResponse {

    @ApiModelProperty("未完成的订单展示信息（不管超不超时，只要未出库都算未完成，）")
    private OrderRunningVo orderVo;

    public OrderSetResponse() {
    }

    public OrderSetResponse(OrderRunningVo orderVo) {
        this.orderVo = orderVo;
    }

    public OrderRunningVo getOrderVo() {
        return orderVo;
    }

    public void setOrderVo(OrderRunningVo orderVo) {
        this.orderVo = orderVo;
    }
}
