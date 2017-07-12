package com.emi.boot.service;

import com.emi.boot.common.enums.ValidState;
import com.emi.boot.common.exception.CommonException;
import com.emi.boot.domain.BaseCommunity;
import com.emi.boot.domain.Community;
import com.emi.boot.domain.Member;
import com.emi.boot.dto.community.CommunityAddRequest;
import com.emi.boot.dto.community.CommunityAddResponse;
import com.emi.boot.dto.community.vo.CommunityVo;
import com.emi.boot.dto.community.vo.CommunityWaitValidVo;
import com.emi.boot.dto.member.vo.MemberVo;
import com.emi.boot.repository.BaseCommunityRepository;
import com.emi.boot.repository.CommunityRepository;
import com.emi.boot.repository.MemberRepository;
import com.emi.boot.repository.dao.CommunityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * Created by emi on 2017/6/13.
 */
@Service
@Transactional
public class CommunityService {

    private final MemberRepository memberRepository;
    private final CommunityRepository communityRepository;
    private final BaseCommunityRepository baseCommunityRepository;
    private final CommunityDao communityDao;
    private final ServiceUtils serviceUtils;

    @Autowired
    public CommunityService(CommunityRepository communityRepository, MemberRepository memberRepository, BaseCommunityRepository baseCommunityRepository, CommunityDao communityDao, ServiceUtils serviceUtils1) {
        this.communityRepository = communityRepository;
        this.memberRepository = memberRepository;
        this.baseCommunityRepository = baseCommunityRepository;
        this.communityDao = communityDao;
        this.serviceUtils = serviceUtils1;
    }


    public CommunityAddResponse addCommunity(String memberId, CommunityAddRequest request) {

        Member member = serviceUtils.fetchMember(memberId);
//        member.checkState(ValidState.Pass.getValue());
        MemberVo memberVo = null;
        if (StringUtils.isEmpty(member.getIdCard()) || StringUtils.isEmpty(member.getRealName())) {
            if (null == request.getMemberVo()) {
                throw new CommonException("首次添加小区，需要验证身份", "用户[" + member.getId() + "]首次添加小区，需要验证身份");
            } else {
                member.verifyIdentity(request.getMemberVo().getRealName(), request.getMemberVo().getIdCard(), request.getMemberVo().getSex());
                memberRepository.save(member);
            }
            memberVo = new MemberVo(member, false);
        }

        BaseCommunity baseCommunity = serviceUtils.fetchBaseCommunity(request.getBaseCommunityId());

        Community community = new Community(member, baseCommunity, request.getBuildingNo());
        communityRepository.save(community);

        return new CommunityAddResponse(new CommunityVo(community.getId(), community.getBaseCommunity().getName(), community.getBuildingNo(), ValidState.Wait.getValue(), community.getCreateTime()), memberVo);
    }

    public List<CommunityVo> listCommunity(String memberId) {
        Member member = serviceUtils.fetchMember(memberId);
        return communityRepository.findListByMemberId(member.getId(), new Sort(Sort.Direction.ASC, "id"));
    }

    public List<CommunityWaitValidVo> listWaitValidCommunity() {
        List<Community> communityList = communityDao.findWaitByValidState(ValidState.Wait);

        return null;
    }

    public void validCommunity(String communityId, int validState) {
        Community community = serviceUtils.fetchCommunity(communityId, "");

        community.setValidState(validState == ValidState.Pass.getValue() ? validState : ValidState.Failed.getValue());
        communityRepository.save(community);
    }
}
