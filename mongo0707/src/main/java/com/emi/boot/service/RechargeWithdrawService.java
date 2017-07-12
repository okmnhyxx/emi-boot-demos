package com.emi.boot.service;

import com.emi.boot.common.enums.AmountsType;
import com.emi.boot.domain.AmountsDetail;
import com.emi.boot.domain.Member;
import com.emi.boot.domain.Recharge;
import com.emi.boot.dto.member.vo.MemberVo;
import com.emi.boot.dto.recharge.MemberRechargeRequest;
import com.emi.boot.repository.AmountsDetailRepository;
import com.emi.boot.repository.MemberRepository;
import com.emi.boot.repository.RechargeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by yunni on 2017/6/24.
 */
@Service
@Transactional
public class RechargeWithdrawService {


    private final ServiceUtils serviceUtils;
    private final MemberRepository memberRepository;
    private final RechargeRepository rechargeRepository;
    private final AmountsDetailRepository amountsDetailRepository;

    @Autowired
    public RechargeWithdrawService(ServiceUtils serviceUtils, MemberRepository memberRepository, RechargeRepository rechargeRepository, AmountsDetailRepository amountsDetailRepository) {
        this.serviceUtils = serviceUtils;
        this.memberRepository = memberRepository;
        this.rechargeRepository = rechargeRepository;
        this.amountsDetailRepository = amountsDetailRepository;
    }


    public MemberVo recharge(String memberId, MemberRechargeRequest request) {

        Member member = serviceUtils.fetchMember(memberId);
        member.recharge(request.getAmounts(), request.isDeposit());
        memberRepository.save(member);
        Recharge recharge = new Recharge(memberId, request.getAmounts(), request.isDeposit(), request.getPayType());
        rechargeRepository.save(recharge);
        AmountsDetail amountsDetail = new AmountsDetail(memberId, recharge.getId(), true, request.getAmounts(), "", AmountsType.Recharge.getValue());
        amountsDetailRepository.save(amountsDetail);
        return new MemberVo(member, false);
    }
}
