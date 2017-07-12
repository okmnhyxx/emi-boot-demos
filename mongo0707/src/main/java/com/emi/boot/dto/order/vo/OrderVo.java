package com.emi.boot.dto.order.vo;

import com.emi.boot.domain.Orders;
import com.emi.boot.domain.Ticket;
import com.emi.boot.service.ServiceUtils;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by emi on 2017/6/26.
 */
public class OrderVo {

    @ApiModelProperty("订单状态, 1:未入库   3:租用中  4:已出库   5:已超时  11;已取消  12:已取消(超时)  13:爽约")
    private int orderState;

    @ApiModelProperty("当订单未结束时（即状态为1:未入库  3:租用中  5:已超时），返回该Vo字段")
    private OrderRunningVo orderRunningVo;

    @ApiModelProperty("当订单结束后（即状态为4:已出库  11;已取消  12:取消订单并支付罚金  13:爽约 时），返回该Vo字段")
    private OrderFinishVo orderFinishVo;

    public OrderVo() {
    }

    public OrderVo(int orderState, OrderRunningVo orderRunningVo, OrderFinishVo orderFinishVo) {
        this.orderState = orderState;
        this.orderRunningVo = orderRunningVo;
        this.orderFinishVo = orderFinishVo;
    }

    public OrderVo(Orders orders, Ticket ticket) {
        this.orderState = ServiceUtils.fetchOrderState(orders.getOrderState(), orders.getOrderEndTime());
        if (ServiceUtils.checkOrderRunning(orderState)) {
            this.orderRunningVo = new OrderRunningVo(orders);
        } else {
            if (null ==ticket) {
                this.orderFinishVo = new OrderFinishVo(orders);
            } else {
                this.orderFinishVo = new OrderFinishVo(orders, ticket);
            }
        }
    }


    public int getOrderState() {
        return orderState;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }

    public OrderRunningVo getOrderRunningVo() {
        return orderRunningVo;
    }

    public void setOrderRunningVo(OrderRunningVo orderRunningVo) {
        this.orderRunningVo = orderRunningVo;
    }

    public OrderFinishVo getOrderFinishVo() {
        return orderFinishVo;
    }

    public void setOrderFinishVo(OrderFinishVo orderFinishVo) {
        this.orderFinishVo = orderFinishVo;
    }
}
