package com.emi.boot.service;

import com.emi.boot.common.enums.AmountsType;
import com.emi.boot.common.enums.DomainType;
import com.emi.boot.common.enums.OrderState;
import com.emi.boot.common.enums.TicketType;
import com.emi.boot.common.exception.DomainBelongNotEqualException;
import com.emi.boot.common.exception.RecordNotFoundException;
import com.emi.boot.config.SystemProperties;
import com.emi.boot.domain.*;
import com.emi.boot.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * Created by emi on 2017/6/13.
 */
@Component
public class ServiceUtils {

    private final SystemProperties properties;
    private final MemberRepository memberRepository;
    private final CommunityRepository communityRepository;
    private final CarLocationRepository carLocationRepository;
    private final BaseCommunityRepository baseCommunityRepository;
    private final HireInfoRepository hireInfoRepository;
    private final OrdersRepository ordersRepository;
    private final AmountsDetailRepository amountsDetailRepository;
    private final TicketRepository ticketRepository;

    @Autowired
    public ServiceUtils(SystemProperties properties, MemberRepository memberRepository, CommunityRepository communityRepository, CarLocationRepository carLocationRepository, BaseCommunityRepository baseCommunityRepository, HireInfoRepository hireInfoRepository, OrdersRepository ordersRepository, AmountsDetailRepository amountsDetailRepository, TicketRepository ticketRepository) {
        this.properties = properties;
        this.memberRepository = memberRepository;
        this.communityRepository = communityRepository;
        this.carLocationRepository = carLocationRepository;
        this.baseCommunityRepository = baseCommunityRepository;
        this.hireInfoRepository = hireInfoRepository;
        this.ordersRepository = ordersRepository;
        this.amountsDetailRepository = amountsDetailRepository;
        this.ticketRepository = ticketRepository;
    }


    public void checkNull(DomainType domainType, BaseDomain baseDomain, String domainId) {
        if (null == baseDomain) {
            throw new RecordNotFoundException(domainType.getDesc(), domainId);
        }
    }

//    public void checkNull(DomainType domainType, BaseDomain baseDomain, String domainId) {
//        if (null == baseDomain) {
//            throw new RecordNotFoundException(domainType.getDesc(), domainId);
//        }
//    }

    public Member fetchMember(String memberId) {
        Member member = memberRepository.findOne(memberId);
        this.checkNull(DomainType.Member, member, memberId);
        return member;
    }

    public Community fetchCommunity(String communityId, String memberId) {
        Community community = communityRepository.findOne(communityId);
        this.checkNull(DomainType.Community, community, communityId);
        this.checkDomainRelation(DomainType.Community, communityId, DomainType.Member, community.getMemberVo().getId(), memberId);
        return community;
    }

    public CarLocation fetchCarLocation(String carLocationId, String communityId) {
        CarLocation carLocation = carLocationRepository.findOne(carLocationId);
        this.checkNull(DomainType.CarLocation, carLocation, carLocationId);
        this.checkDomainRelation(DomainType.CarLocation, carLocationId, DomainType.Community, carLocation.getCommunityVo().getId(), communityId);
        return carLocation;
    }


    public HireInfo fetchHireInfo(String hireId) {
        HireInfo hireInfo = hireInfoRepository.findOne(hireId);
        this.checkNull(DomainType.HireInfo, hireInfo, hireId);
        return hireInfo;
    }

    public Orders fetchOrders(String orderId, String memberId) {
        Orders orders = ordersRepository.findOne(orderId);
        checkNull(DomainType.Orders, orders, orderId);
        checkDomainRelation(DomainType.Orders, orderId, DomainType.Member, orders.getLesseeId(), memberId);
        return orders;
    }

    public void checkDomainRelation(DomainType slaveryDomain, String slaveryId, DomainType masterDomain, String supposeMasterId, String transMasterId) {
        if (!StringUtils.isEmpty(transMasterId)) {//当传入的Id不为空时才验证
            if (!supposeMasterId.equals(transMasterId)) {
                throw new DomainBelongNotEqualException(slaveryDomain, slaveryId, masterDomain, supposeMasterId, transMasterId);
            }
        }
    }

    public BaseCommunity fetchBaseCommunity(String baseCommunityId) {
        BaseCommunity baseCommunity = baseCommunityRepository.findOne(baseCommunityId);
        this.checkNull(DomainType.BaseCommunity, baseCommunity, baseCommunityId);
        return baseCommunity;
    }

    public void orderMiss(Orders orders, Member lessee) {
        lessee.payOrder(orders.getAmounts());
        memberRepository.save(lessee);
        Member lessor = this.fetchMember(orders.getLessorId());
        lessor.addBalance(orders.getAmounts());
        memberRepository.save(lessor);
        orders.miss();
        ordersRepository.save(orders);
        String title = String.format("%s %s车位%s", orders.getCommunity(), orders.getOrderTimeStr(), orders.getCarLocation());
        AmountsDetail payerDetail = new AmountsDetail(orders.getLesseeId(), orders.getId(), false, orders.getAmounts(), title, AmountsType.PayOrder.getValue());
        AmountsDetail payeeDetail = new AmountsDetail(orders.getLessorId(), orders.getId(), true, orders.getAmounts(), title, AmountsType.GainOrder.getValue());
        amountsDetailRepository.save(payerDetail);
        amountsDetailRepository.save(payeeDetail);
    }

    public boolean orderOutTime(Orders orders, Member member) {
        boolean canComeGo = true;
        String resultMsg = "准许出库";
        Ticket ticket = new Ticket(orders.getLesseeId(), orders.getLessorId(), orders.getId(), properties.getGoOutPrice(), TicketType.OutTimeGo.getValue());
        ticketRepository.save(ticket);
        String title = String.format("%s %s车位%s", orders.getCommunity(), orders.getOrderTimeStr(), orders.getCarLocation());
        AmountsDetail payerDetail = new AmountsDetail(orders.getLesseeId(), orders.getId(), false, properties.getGoOutPrice(), title, AmountsType.PayOutTime.getValue());
        AmountsDetail payeeDetail = new AmountsDetail(orders.getLessorId(), orders.getId(), true, properties.getGoOutPrice(), title, AmountsType.GainOutTime.getValue());
        amountsDetailRepository.save(payerDetail);
        amountsDetailRepository.save(payeeDetail);
        if (member.getDeposit() < properties.getGoOutPrice()) {
            canComeGo = false;
//            resultMsg = "用户押金不足抵扣罚款，请现金支付罚款后再出库";
        } else {
            member.payOutTimeGo(properties.getGoOutPrice());
            memberRepository.save(member);
//            resultMsg = "超时罚款已从押金中扣除，" + resultMsg;
        }
        Member lessor = this.fetchMember(orders.getLessorId());
        lessor.addBalance(properties.getGoOutPrice());
        memberRepository.save(lessor);
        orders.goWithFine(new Date(), properties.getGoOutPrice(), ticket.getId());
        ordersRepository.save(orders);
        return canComeGo;
    }

    public static boolean checkOrderRunning(int orderState) {
        return orderState == OrderState.WaitCome.getValue() || orderState == OrderState.Using.getValue() || orderState == OrderState.AlreadyOut.getValue();
    }

    public static int fetchOrderState(int orderState, Date orderEndTime) {
        if (orderState == OrderState.Using.getValue()) {
            if (orderEndTime.getTime() < System.currentTimeMillis()) {
                return OrderState.AlreadyOut.getValue();
            } else {
                return orderState;
            }
        } else {
            return orderState;
        }
    }
}
