package com.emi.boot.dto.order;

import com.emi.boot.dto.RestResponse;
import com.emi.boot.dto.order.vo.OrderRunningVo;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by emi on 2017/6/26.
 */
public class OrderUsingListResponse extends RestResponse {

    @ApiModelProperty("租用中 或者 已超时的订单列表")
    private List<OrderRunningVo> orderVoList;

    public OrderUsingListResponse() {
    }

    public OrderUsingListResponse(List<OrderRunningVo> orderVoList) {
        this.orderVoList = orderVoList;
    }

    public List<OrderRunningVo> getOrderVoList() {
        return orderVoList;
    }

    public void setOrderVoList(List<OrderRunningVo> orderVoList) {
        this.orderVoList = orderVoList;
    }
}
