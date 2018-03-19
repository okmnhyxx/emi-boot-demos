package com.emi.mongo.dto.order;

import com.emi.mongo.dto.RestResponse;
import com.emi.mongo.dto.order.vo.OrderVo;

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
