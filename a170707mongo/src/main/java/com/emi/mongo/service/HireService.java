package com.emi.mongo.service;

import com.emi.mongo.common.enums.ValidState;
import com.emi.mongo.common.util.DateUtils;
import com.emi.mongo.config.SystemProperties;
import com.emi.mongo.domain.CarLocation;
import com.emi.mongo.domain.Community;
import com.emi.mongo.domain.HireInfo;
import com.emi.mongo.domain.Member;
import com.emi.mongo.dto.community.vo.CommunityBaseVo;
import com.emi.mongo.dto.hire.HireListRequest;
import com.emi.mongo.dto.hire.HirePublishRequest;
import com.emi.mongo.dto.hire.vo.HireBaseVo;
import com.emi.mongo.dto.hire.vo.HireVo;
import com.emi.mongo.dto.location.vo.LocationBaseVo;
import com.emi.mongo.dto.member.vo.MemberBaseVo;
import com.emi.mongo.repository.HireInfoRepository;
import com.emi.mongo.vo.HireSqlVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by emi on 2017/6/16.
 */
@Service
@Transactional
public class HireService {

    private final SystemProperties properties;
    private final ServiceUtils serviceUtils;
    private final HireInfoRepository hireInfoRepository;
//    private final HireMapper hireMapper;

    @Autowired
    public HireService(SystemProperties properties, ServiceUtils serviceUtils, HireInfoRepository hireInfoRepository) {
        this.properties = properties;
        this.serviceUtils = serviceUtils;
        this.hireInfoRepository = hireInfoRepository;
    }

    public HireVo publishHire(String memberId, HirePublishRequest request) {

        Member member = serviceUtils.fetchMember(memberId);
        request.checkTimes();//判断并纠正时间
        int beginHour = Integer.parseInt(request.getBeginTime().substring(0, request.getBeginTime().indexOf(":")));
        int endHour = Integer.parseInt(request.getEndTime().substring(0, request.getEndTime().indexOf(":")));
        Date dateBegin = DateUtils.appointTodayHour(beginHour);
        Date dateEnd = DateUtils.appointTodayHour(endHour);

        Community community = serviceUtils.fetchCommunity(request.getCommunityId(), memberId);
        community.checkState(ValidState.Pass.getValue());
        CarLocation carLocation = serviceUtils.fetchCarLocation(request.getCarLocationId(), community.getId());
        carLocation.checkState(ValidState.Pass.getValue());

        HireInfo hireInfo = new HireInfo(memberId, dateBegin, dateEnd, String.format("%s-%s", request.getBeginTime(), request.getEndTime()), properties.getUnitPrice()*(endHour - beginHour), community.getBaseCommunity().getId(),
                community.getId(), community.getBaseCommunity().getName(), request.getCarLocationId(), carLocation.getName());
        hireInfoRepository.save(hireInfo);

        return new HireVo(new HireBaseVo(hireInfo.getId(), hireInfo.getAmounts(), hireInfo.getHireTimeStr(), hireInfo.getCreateTime(), hireInfo.isHired()), new MemberBaseVo(member, false), new CommunityBaseVo(community), new LocationBaseVo(carLocation));
    }

    public List<HireVo> searchList(HireListRequest request) {

//        request.checkTimes();
//        Date todayBegin = DateUtils.fetchInitDay(0), todayEnd = DateUtils.fetchInitDay(1);
//        Date dateBegin = null;
//        Date dateEnd = null;
//        if (!StringUtils.isEmpty(request.getBeginTime())) {
//            int beginHour = Integer.parseInt(request.getBeginTime().substring(0, request.getBeginTime().indexOf(":")));
//            dateBegin = DateUtils.appointTodayHour(beginHour);
//        }
//        if (!StringUtils.isEmpty(request.getEndTime())) {
//            int endHour = Integer.parseInt(request.getEndTime().substring(0, request.getEndTime().indexOf(":")));
//            dateEnd = DateUtils.appointTodayHour(endHour);
//        }
//        List<HireSqlVo> hireSqlVoList = hireMapper.fetchPublishedHireInfo(request.getBaseCommunityId(), todayBegin, todayEnd, dateBegin, dateEnd);
//        List<HireVo> hireVoList = new ArrayList<>();
//        for (HireSqlVo h : hireSqlVoList) {
//            hireVoList.add(new HireVo(new HireBaseVo(h.getId(), h.getAmounts(), h.getHireTimeStr(), h.getCreateTime(), h.isHired()), new MemberDocVo(h.getMemberId(), h.getRealName(), h.getSex(), false),
//                    new CommunityBaseVo(h.getCommunityId(), h.getCommunity()), new LocationBaseVo(h.getCarLocationId(), h.getCarLocation())));
//        }
//        return hireVoList;

        return null;
    }

    public List<HireVo> myHireList(String memberId) {
        Member member = serviceUtils.fetchMember(memberId);
        List<HireSqlVo> hireSqlVoList = hireInfoRepository.fetchMemberPublishedHireInfoOrderByIdDesc(memberId);
        List<HireVo> hireVoList = new ArrayList<>();
        for (HireSqlVo h : hireSqlVoList) {
            hireVoList.add(new HireVo(new HireBaseVo(h.getId(), h.getAmounts(), h.getHireTimeStr(), h.getCreateTime(), h.isHired()), new MemberBaseVo(memberId, member.getRealName(), member.getSex(), false),
                    new CommunityBaseVo(h.getCommunityId(), h.getCommunity()), new LocationBaseVo(h.getCarLocationId(), h.getCarLocation())));
        }
        return hireVoList;
    }
}
