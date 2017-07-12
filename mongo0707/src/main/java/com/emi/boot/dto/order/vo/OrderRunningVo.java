package com.emi.boot.dto.order.vo;

import com.emi.boot.common.enums.OrderState;
import com.emi.boot.common.util.DateUtils;
import com.emi.boot.domain.Orders;
import com.emi.boot.service.ServiceUtils;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by emi on 2017/6/20.
 */
public class OrderRunningVo {

    @ApiModelProperty("订单Id")
    private String orderId;

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("小区名")
    private String community;

    @ApiModelProperty("车位")
    private String carLocation;

    @ApiModelProperty("租用时间")
    private String orderTimeStr;

    @ApiModelProperty("承租人车牌号")
    private String lesseeLicense;

    @ApiModelProperty("承租人手机号")
    private String lesseePhone;

    @ApiModelProperty("订单状态int, 1:未入库   3:租用中   5:已超时")
    private int orderState;
    @ApiModelProperty("订单状态Str, 1:未入库   3:租用中   5:已超时")
    private String orderStateStr;

    @ApiModelProperty("订单金额")
    private int amounts;

    @ApiModelProperty("下单时间")
    private String createTime;

    public OrderRunningVo() {
    }

    public OrderRunningVo(Orders orders) {
        this.orderState = ServiceUtils.fetchOrderState(orders.getOrderState(), orders.getOrderEndTime());
        this.orderStateStr = OrderState.valueOf(orderState).getDesc();

        this.orderId = orders.getId();
        this.orderNo = orders.getOrderNo();
        this.community = orders.getCommunity();
        this.carLocation = orders.getCarLocation();
        this.orderTimeStr = orders.getOrderTimeStr();
        this.lesseeLicense = orders.getLicense();
        this.lesseePhone = orders.getLesseePhone();
        this.amounts = orders.getAmounts();
        this.createTime = DateUtils.sdf1.format(orders.getCreateTime());
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getCarLocation() {
        return carLocation;
    }

    public void setCarLocation(String carLocation) {
        this.carLocation = carLocation;
    }

    public String getOrderTimeStr() {
        return orderTimeStr;
    }

    public void setOrderTimeStr(String orderTimeStr) {
        this.orderTimeStr = orderTimeStr;
    }

    public String getLesseeLicense() {
        return lesseeLicense;
    }

    public void setLesseeLicense(String lesseeLicense) {
        this.lesseeLicense = lesseeLicense;
    }

    public String getLesseePhone() {
        return lesseePhone;
    }

    public void setLesseePhone(String lesseePhone) {
        this.lesseePhone = lesseePhone;
    }

    public int getOrderState() {
        return orderState;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }

    public String getOrderStateStr() {
        return orderStateStr;
    }

    public void setOrderStateStr(String orderStateStr) {
        this.orderStateStr = orderStateStr;
    }

    public int getAmounts() {
        return amounts;
    }

    public void setAmounts(int amounts) {
        this.amounts = amounts;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
