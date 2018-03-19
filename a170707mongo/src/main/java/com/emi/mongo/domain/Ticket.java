package com.emi.mongo.domain;

/**
 * Created by emi on 2017/6/19.
 * 罚款单
 */
public class Ticket extends BaseDomain {

    private String payerId;//付款人

    private String payeeId;//收款人

    private String orderId;//订单Id

    private int amounts;//金额

    private int ticketType;//1:超时取消订单   2:出库超时

    public Ticket() {
    }

    public Ticket(String payerId, String payeeId, String orderId, int amounts, int ticketType) {
        this.payerId = payerId;
        this.payeeId = payeeId;
        this.orderId = orderId;
        this.amounts = amounts;
        this.ticketType = ticketType;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public String getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(String payeeId) {
        this.payeeId = payeeId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getAmounts() {
        return amounts;
    }

    public void setAmounts(int amounts) {
        this.amounts = amounts;
    }

    public int getTicketType() {
        return ticketType;
    }

    public void setTicketType(int ticketType) {
        this.ticketType = ticketType;
    }
}
