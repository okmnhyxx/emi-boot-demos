package com.emi.mongo.dto.order.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by emi on 2017/6/20.
 */
public class TicketVo {

    @ApiModelProperty("罚款单Id")
    private String tickerId;

    @ApiModelProperty("罚款金额")
    private int amounts;

    @ApiModelProperty("罚款原因")
    private String reason;

    public TicketVo() {
    }

    public TicketVo(String tickerId, int amounts, String reason) {
        this.tickerId = tickerId;
        this.amounts = amounts;
        this.reason = reason;
    }

    public String getTickerId() {
        return tickerId;
    }

    public void setTickerId(String tickerId) {
        this.tickerId = tickerId;
    }

    public int getAmounts() {
        return amounts;
    }

    public void setAmounts(int amounts) {
        this.amounts = amounts;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
