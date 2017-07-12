package com.emi.boot.service;

import com.emi.boot.common.enums.AmountsType;
import com.emi.boot.common.enums.OrderState;
import com.emi.boot.common.enums.TicketType;
import com.emi.boot.common.enums.TimeUnitType;
import com.emi.boot.common.exception.CommonException;
import com.emi.boot.common.util.DateUtils;
import com.emi.boot.config.SystemProperties;
import com.emi.boot.domain.*;
import com.emi.boot.dto.order.OrderComeGoResponse;
import com.emi.boot.dto.order.OrderSetRequest;
import com.emi.boot.dto.order.vo.OrderRunningVo;
import com.emi.boot.dto.order.vo.OrderVo;
import com.emi.boot.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by emi on 2017/6/20.
 */
@Service
@Transactional
public class OrderService {

    private final SystemProperties properties;
    private final ServiceUtils serviceUtils;
    private final MemberRepository memberRepository;
    private final OrdersRepository ordersRepository;
    private final TicketRepository ticketRepository;
    private final AmountsDetailRepository amountsDetailRepository;
    private final HireInfoRepository hireInfoRepository;

    @Autowired
    public OrderService(SystemProperties properties, ServiceUtils serviceUtils, MemberRepository memberRepository, OrdersRepository ordersRepository, TicketRepository ticketRepository,
                        AmountsDetailRepository amountsDetailRepository, HireInfoRepository hireInfoRepository) {
        this.properties = properties;
        this.serviceUtils = serviceUtils;
        this.memberRepository = memberRepository;
        this.ordersRepository = ordersRepository;
        this.ticketRepository = ticketRepository;
        this.amountsDetailRepository = amountsDetailRepository;
        this.hireInfoRepository = hireInfoRepository;
    }

    public OrderRunningVo setOrder(String memberId, OrderSetRequest request) {

        HireInfo hireInfo = serviceUtils.fetchHireInfo(request.getHireId());
        hireInfo.checkNotHired();

        request.checkTimes();
        int beginHour = Integer.parseInt(request.getBeginTime().substring(0, request.getBeginTime().indexOf(":")));
        int endHour = Integer.parseInt(request.getEndTime().substring(0, request.getEndTime().indexOf(":")));
        Date dateBegin = DateUtils.appointTodayHour(beginHour);
        Date dateEnd = DateUtils.appointTodayHour(endHour);
        if (dateBegin.before(hireInfo.getHireBeginTime()) || dateEnd.after(hireInfo.getHireEndTime())) {
            throw new CommonException("租用时间超出出租范围", String.format("租用时间[%s - %s]超出出租[%s - %s]范围", DateUtils.sdf1.format(dateBegin), DateUtils.sdf1.format(dateEnd),
                    DateUtils.sdf1.format(hireInfo.getHireBeginTime()), DateUtils.sdf1.format(hireInfo.getHireEndTime())));
        }
        int orderAmounts = (endHour - beginHour) * properties.getUnitPrice();

        Member member = serviceUtils.fetchMember(memberId);
        member.checkDeposit();
        member.freezeBalance(orderAmounts);
        memberRepository.save(member);

        Orders orders = new Orders(hireInfo.getId(), hireInfo.getMemberId(), memberId, member.getPhone(), orderAmounts, request.getLicense(), hireInfo.getCommunityId(), hireInfo.getCommunity(),
                hireInfo.getCarLocationId(), hireInfo.getCarLocation(), dateBegin, dateEnd, String.format("%s-%s", request.getBeginTime(), request.getEndTime()));
        ordersRepository.save(orders);

        hireInfo.hiredOrder(orders.getId());
        hireInfoRepository.save(hireInfo);

        return new OrderRunningVo(orders);
    }

    public String cancelOrder(String memberId, String orderId) {

        Member member = serviceUtils.fetchMember(memberId);
        Orders orders = serviceUtils.fetchOrders(orderId, member.getId());
        HireInfo hireInfo = serviceUtils.fetchHireInfo(orders.getHireInfoId());
        String resultMsg = "取消成功";

        Date dateLimit = DateUtils.generateTimeOut(orders.getCreateTime(), TimeUnitType.Minute, 10, true);
        if (dateLimit.before(new Date())) {
            resultMsg = "超过10分钟取消订单，余额已扣款10元";
            Ticket ticket = new Ticket(memberId, hireInfo.getMemberId(), orders.getId(), properties.getCancelOutPrice(), TicketType.OutTimeCancel.getValue());
            ticketRepository.save(ticket);
            member.cancelOrderWithFine(properties.getCancelOutPrice(), orders.getAmounts());
            memberRepository.save(member);
            Member lessor = serviceUtils.fetchMember(orders.getLessorId());
            lessor.addBalance(properties.getCancelOutPrice());
            memberRepository.save(lessor);
            orders.cancelWithFine(ticket.getId(), properties.getCancelOutPrice());
            ordersRepository.save(orders);
            String title = String.format("%s %s车位%s", orders.getCommunity(), orders.getOrderTimeStr(), orders.getCarLocation());
            AmountsDetail payerDetail = new AmountsDetail(memberId, ticket.getId(), false, properties.getCancelOutPrice(), title, AmountsType.PayCancel.getValue());
            AmountsDetail payeeDetail = new AmountsDetail(hireInfo.getMemberId(), ticket.getId(), true, properties.getCancelOutPrice(), title, AmountsType.GainCancel.getValue());
            amountsDetailRepository.save(payerDetail);
            amountsDetailRepository.save(payeeDetail);
        } else {
            orders.setOrderState(OrderState.Cancel.getValue());
            ordersRepository.save(orders);
            member.cancelOrder(orders.getAmounts());
            memberRepository.save(member);
        }
        hireInfo.cancelHire();
        hireInfoRepository.save(hireInfo);
        return resultMsg;
    }

    public OrderComeGoResponse scanner(String orderNo) {

        Orders orders = ordersRepository.findByOrderNoAndActiveTrue(orderNo);
        Member member = serviceUtils.fetchMember(orders.getLesseeId());

        Date date = new Date();

        //入库
        if (orders.getOrderState() == OrderState.WaitCome.getValue()) {
            if (orders.getOrderBeginTime().after(date)) {
                return new OrderComeGoResponse(false, "当前时间还未到入库时间，不准入库", new OrderRunningVo(orders));
            }
            if (orders.getOrderEndTime().before(date)) {
                serviceUtils.orderMiss(orders, member);
                return new OrderComeGoResponse(false, "当前时间已超出订单截止时间，不准入库", new OrderRunningVo(orders));
            }

            member.payOrder(orders.getAmounts());
            memberRepository.save(member);
            Member lessor = serviceUtils.fetchMember(orders.getLessorId());
            lessor.addBalance(orders.getAmounts());
            memberRepository.save(lessor);
            orders.come(date);
            ordersRepository.save(orders);
            String title = String.format("%s %s车位%s", orders.getCommunity(), orders.getOrderTimeStr(), orders.getCarLocation());
            AmountsDetail payerDetail = new AmountsDetail(orders.getLesseeId(), orders.getId(), false, orders.getAmounts(), title, AmountsType.PayOrder.getValue());
            AmountsDetail payeeDetail = new AmountsDetail(orders.getLessorId(), orders.getId(), true, orders.getAmounts(), title, AmountsType.GainOrder.getValue());
            amountsDetailRepository.save(payerDetail);
            amountsDetailRepository.save(payeeDetail);
            return new OrderComeGoResponse(true, "支付成功，准许入库", new OrderRunningVo(orders));
        } else if (orders.getOrderState() == OrderState.Using.getValue()) {//出库
            boolean canComeGo = true;
            String resultMsg = "准许出库";
            if (date.after(orders.getOrderEndTime())) {
                if (serviceUtils.orderOutTime(orders, member)) {
                    resultMsg = "超时罚款已从押金中扣除，" + resultMsg;
                } else {
                    canComeGo = false;
                    resultMsg = "用户押金不足抵扣罚款，请现金支付罚款后再出库";
                }
            } else {
                orders.go(date);
                ordersRepository.save(orders);
            }
            return new OrderComeGoResponse(canComeGo, resultMsg, new OrderRunningVo(orders));
        } else if (orders.getOrderState() == OrderState.MissHire.getValue()) {
            return new OrderComeGoResponse(false, "当前时间已超出订单截止时间，不准入库", new OrderRunningVo(orders));
        } else if (orders.getOrderState() == OrderState.AlreadyGo.getValue()) {
            return new OrderComeGoResponse(false, "车已出库，二维码失效", new OrderRunningVo(orders));
        } else {
            return new OrderComeGoResponse(false, "订单已取消，无法出入库", new OrderRunningVo(orders));
        }
    }

    public List<OrderRunningVo> communityOrderList(long communityId) {
        List<Orders> ordersList = ordersRepository.findByCommunityIdAndOrderStateAndActiveTrueOrderByIdDesc(communityId, OrderState.Using.getValue());
        List<OrderRunningVo> orderVoList = new ArrayList<>();
        for (Orders o : ordersList) {
            orderVoList.add(new OrderRunningVo(o));
        }
        return orderVoList;
    }

    public List<OrderVo> memberPublishedOrderList(long memberId) {
        List<OrderVo> orderVoList = new ArrayList<>();
        List<Orders> ordersList = ordersRepository.findByLessorIdAndActiveTrueOrderByIdDesc(memberId);
        for (Orders o : ordersList) {
            if (StringUtils.isEmpty(o.getTicketId())) {
                orderVoList.add(new OrderVo(o, null));
            } else {
                orderVoList.add(new OrderVo(o, ticketRepository.findOne(o.getTicketId())));
            }
        }
        return orderVoList;
    }

    public List<OrderVo> memberHiredOrderList(long memberId) {
        List<OrderVo> orderVoList = new ArrayList<>();
        List<Orders> ordersList = ordersRepository.findByLesseeIdAndActiveTrueOrderByIdDesc(memberId);
        for (Orders o : ordersList) {
            if (StringUtils.isEmpty(o.getTicketId())) {
                orderVoList.add(new OrderVo(o, null));
            } else {
                orderVoList.add(new OrderVo(o, ticketRepository.findOne(o.getTicketId())));
            }
        }
        return orderVoList;
    }
}
