package com.emi.boot.domain;

import com.emi.boot.common.enums.DomainType;
import com.emi.boot.common.enums.OrderState;
import com.emi.boot.common.exception.RecordStatusException;
import com.emi.boot.common.util.CommonUtils;

import java.util.Date;

/**
 * Created by emi on 2017/6/12.
 */
public class Orders extends BaseDomain{

    private String orderNo;

    private String hireInfoId;

    private String lessorId;//出租人

    private String lesseeId;//承租人
    private String lesseePhone;//承租人电话

    private int amounts;
    private int realAmounts;
//    private int payType;//支付方式  1：微信 2：支付宝 3：余额

    private int orderState;//1:未入库  2:待支付(descrapt)  3:租用中  4:已出库  5:已超时（虚拟状态）  11;已取消  12:已取消(超时)  13:爽约

    private boolean outTimeGo = false;//是否超时了
    private String ticketId;//罚款单  1：取消订单罚款 + 超时罚款

    private String license;

    private String communityId;
    private String community;

    private String carLocationId;
    private String carLocation;

    private Date cancelTime;// 申请取消/申请退款时间

    private Date orderBeginTime;//订单有效时间起
    private Date orderEndTime;//订单有效时间止
    private String orderTimeStr;

    private Date comeTime;//到达时间
    private Date goTime;//出库时间

    public Orders() {
        this.orderNo = CommonUtils.generateOrderNo();
        this.orderState = OrderState.WaitCome.getValue();
    }

    public Orders(String hireInfoId, String lessorId, String lesseeId, String lesseePhone, int amounts, String license, String communityId, String community, String carLocationId, String carLocation, Date orderBeginTime, Date orderEndTime, String orderTimeStr) {
        this();
        this.hireInfoId = hireInfoId;
        this.lessorId = lessorId;
        this.lesseeId = lesseeId;
        this.lesseePhone = lesseePhone;
        this.amounts = amounts;
        this.license = license;
        this.communityId = communityId;
        this.community = community;
        this.carLocationId = carLocationId;
        this.carLocation = carLocation;
        this.orderBeginTime = orderBeginTime;
        this.orderEndTime = orderEndTime;
        this.orderTimeStr = orderTimeStr;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getHireInfoId() {
        return hireInfoId;
    }

    public void setHireInfoId(String hireInfoId) {
        this.hireInfoId = hireInfoId;
    }

    public String getLessorId() {
        return lessorId;
    }

    public void setLessorId(String lessorId) {
        this.lessorId = lessorId;
    }

    public String getLesseeId() {
        return lesseeId;
    }

    public void setLesseeId(String lesseeId) {
        this.lesseeId = lesseeId;
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

    public int getRealAmounts() {
        return realAmounts;
    }

    public void setRealAmounts(int realAmounts) {
        this.realAmounts = realAmounts;
    }

    public int getOrderState() {
        return orderState;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }

    public boolean isOutTimeGo() {
        return outTimeGo;
    }

    public void setOutTimeGo(boolean outTimeGo) {
        this.outTimeGo = outTimeGo;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getCarLocationId() {
        return carLocationId;
    }

    public void setCarLocationId(String carLocationId) {
        this.carLocationId = carLocationId;
    }

    public String getCarLocation() {
        return carLocation;
    }

    public void setCarLocation(String carLocation) {
        this.carLocation = carLocation;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Date getOrderBeginTime() {
        return orderBeginTime;
    }

    public void setOrderBeginTime(Date orderBeginTime) {
        this.orderBeginTime = orderBeginTime;
    }

    public Date getOrderEndTime() {
        return orderEndTime;
    }

    public void setOrderEndTime(Date orderEndTime) {
        this.orderEndTime = orderEndTime;
    }

    public String getOrderTimeStr() {
        return orderTimeStr;
    }

    public void setOrderTimeStr(String orderTimeStr) {
        this.orderTimeStr = orderTimeStr;
    }

    public Date getComeTime() {
        return comeTime;
    }

    public void setComeTime(Date comeTime) {
        this.comeTime = comeTime;
    }

    public Date getGoTime() {
        return goTime;
    }

    public void setGoTime(Date goTime) {
        this.goTime = goTime;
    }

    public void checkState(int... states) {
        for (int s : states) {
            if (this.orderState == s) {
                return;
            }
        }
        throw new RecordStatusException(orderState, DomainType.Orders, getId(), states);
    }


    public void cancelWithFine(String ticketId, int cancelOutPrice) {
        this.ticketId = ticketId;
        this.realAmounts = cancelOutPrice;
        this.orderState = OrderState.CancelWithFine.getValue();
    }

    public void come(Date date) {
        this.comeTime = date;
        this.orderState = OrderState.Using.getValue();
        this.realAmounts = this.amounts;
    }

    public void miss() {
        this.orderState = OrderState.MissHire.getValue();
        this.realAmounts = this.amounts;
    }

    public void go(Date date) {
        this.orderState = OrderState.AlreadyGo.getValue();
        this.goTime = date;
        this.realAmounts = this.amounts;
    }

    public void goWithFine(Date date, int goOutPrice, String ticketId) {
        this.go(date);
        this.realAmounts = this.amounts + goOutPrice;
        this.ticketId = ticketId;
        this.outTimeGo = true;
    }
}
