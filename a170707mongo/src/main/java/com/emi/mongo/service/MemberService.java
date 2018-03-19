package com.emi.mongo.service;

import com.emi.mongo.common.enums.SmsType;
import com.emi.mongo.common.enums.ValidState;
import com.emi.mongo.common.exception.CommonException;
import com.emi.mongo.domain.Member;
import com.emi.mongo.domain.SmsCode;
import com.emi.mongo.dto.member.MemberInfoResponse;
import com.emi.mongo.dto.member.MemberLoginRequest;
import com.emi.mongo.dto.member.vo.MemberVo;
import com.emi.mongo.repository.MemberRepository;
import com.emi.mongo.repository.SmsCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by emi on 2017/6/7.
 */
@Service
@Transactional
public class MemberService {

    private final ServiceUtils serviceUtils;
    private final MemberRepository memberRepository;
    private final SmsCodeRepository smsCodeRepository;

    @Autowired
    public MemberService(ServiceUtils serviceUtils, MemberRepository memberRepository, SmsCodeRepository smsCodeRepository) {
        this.serviceUtils = serviceUtils;
        this.memberRepository = memberRepository;
        this.smsCodeRepository = smsCodeRepository;
    }

    public MemberInfoResponse login(MemberLoginRequest request) {
        SmsCode smsCode = smsCodeRepository.findFirstByPhoneAndSmsTypeOrderByIdDesc(request.getPhone(), SmsType.Login.getValue());
        if (null == smsCode) {
            throw new CommonException("请先获取验证码");
        }

        if (!smsCode.getCode().equals(request.getSmsCode())) {
            throw new CommonException("验证码错误");
        }

        Member member = memberRepository.findByPhone(request.getPhone());
        if (null == member) {
            member = new Member(request.getPhone());
            memberRepository.save(member);
        }

        return new MemberInfoResponse(new MemberVo(member, false));
    }


    public MemberVo info(String memberId) {
        Member member = serviceUtils.fetchMember(memberId);
        return new MemberVo(member, false);
    }

    public List<MemberVo> listWaitValidMember() {
        List<Member> memberList = memberRepository.findListByValidStateOrderByCreateTimeDesc(ValidState.Wait.getValue());
        List<MemberVo> memberVoList = new ArrayList<>();
        for (Member m : memberList) {
            memberVoList.add(new MemberVo(m, true));
        }
        return memberVoList;
    }

    public void validMember(String memberId, int validState) {

        Member member = serviceUtils.fetchMember(memberId);

        member.setValidState(validState == ValidState.Pass.getValue() ? validState : ValidState.Failed.getValue());
        memberRepository.save(member);
    }

}
