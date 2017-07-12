package com.emi.boot.dto.order;

import com.emi.boot.dto.RestResponse;
import com.emi.boot.dto.order.vo.OrderVo;

import java.util.List;

/**
 * Created by emi on 2017/6/26.
 */
public class OrderMemberListResponse extends RestResponse {

    private List<OrderVo> orderVoList;

    public OrderMemberListResponse() {
    }

    public OrderMemberListResponse(List<OrderVo> orderVoList) {
        this.orderVoList = orderVoList;
    }

    public List<OrderVo> getOrderVoList() {
        return orderVoList;
    }

    public void setOrderVoList(List<OrderVo> orderVoList) {
        this.orderVoList = orderVoList;
    }
}
