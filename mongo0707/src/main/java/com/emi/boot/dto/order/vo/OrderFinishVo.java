package com.emi.boot.dto.order.vo;

import com.emi.boot.common.enums.OrderState;
import com.emi.boot.common.enums.TicketType;
import com.emi.boot.common.util.DateUtils;
import com.emi.boot.domain.Orders;
import com.emi.boot.domain.Ticket;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by emi on 2017/6/20.
 */
public class OrderFinishVo {

    @ApiModelProperty("订单Id")
    private String orderId;

    @ApiModelProperty("订单号")
    private String orderNo;


    @ApiModelProperty("小区名")
    private String community;

    @ApiModelProperty("车位")
    private String carLocation;

    @ApiModelProperty("承租人车牌号")
    private String lesseeLicense;

    @ApiModelProperty("承租人手机号")
    private String lesseePhone;

    @ApiModelProperty("订单金额")
    private int amounts;

    @ApiModelProperty("实付款，当租用订单时，实付款为订单金额+超时罚金（未超时为罚金0），当取消订单或爽约时，实付款仅为罚金（10分钟内取消，罚金为0）")
    private int realPayAmounts;

    @ApiModelProperty("订单状态int, 4:已出库  11;已取消  12:已取消(超时)  13:爽约")
    private int orderState;
    @ApiModelProperty("订单状态Str,  4:已出库  11;已取消  12:已取消(超时)  13:爽约")
    private String orderStateStr;

    @ApiModelProperty("租用日 example：2017-06-20")
    private String orderDayStr;

    @ApiModelProperty("租用时间 example：07:00-14:00")
    private String orderTimeStr;

    @ApiModelProperty("实际出入库时间，ps：这里不考虑超过一天的情况")
    private String orderRealTimeStr;

    @ApiModelProperty("违约罚款信息，当为null时，无违约情况")
    private TicketVo ticketVo;

    public OrderFinishVo() {
    }

    public OrderFinishVo(Orders orders) {
        this.orderId = orders.getId();
        this.orderNo = orders.getOrderNo();
        this.community = orders.getCommunity();
        this.carLocation = orders.getCarLocation();
        this.lesseeLicense = orders.getLicense();
        this.lesseePhone = orders.getLesseePhone();
        this.amounts = orders.getAmounts();
        this.realPayAmounts = orders.getRealAmounts();
        this.orderState = orders.getOrderState();
        this.orderStateStr = OrderState.valueOf(orders.getOrderState()).getDesc();
        this.orderDayStr = DateUtils.sdf3.format(orders.getOrderBeginTime());
        this.orderTimeStr = orders.getOrderTimeStr();
        this.orderRealTimeStr = DateUtils.generateTimeInterval(orders.getComeTime(), orders.getGoTime());
        this.ticketVo = null;
    }

    public OrderFinishVo(Orders orders, Ticket ticket) {
        this(orders);
        this.ticketVo = new TicketVo(ticket.getId(), ticket.getAmounts(), TicketType.valueOf(ticket.getTicketType()).getDesc());
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

    public int getAmounts() {
        return amounts;
    }

    public void setAmounts(int amounts) {
        this.amounts = amounts;
    }

    public int getRealPayAmounts() {
        return realPayAmounts;
    }

    public void setRealPayAmounts(int realPayAmounts) {
        this.realPayAmounts = realPayAmounts;
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

    public String getOrderDayStr() {
        return orderDayStr;
    }

    public void setOrderDayStr(String orderDayStr) {
        this.orderDayStr = orderDayStr;
    }

    public String getOrderTimeStr() {
        return orderTimeStr;
    }

    public void setOrderTimeStr(String orderTimeStr) {
        this.orderTimeStr = orderTimeStr;
    }

    public String getOrderRealTimeStr() {
        return orderRealTimeStr;
    }

    public void setOrderRealTimeStr(String orderRealTimeStr) {
        this.orderRealTimeStr = orderRealTimeStr;
    }

    public TicketVo getTicketVo() {
        return ticketVo;
    }

    public void setTicketVo(TicketVo ticketVo) {
        this.ticketVo = ticketVo;
    }
}
