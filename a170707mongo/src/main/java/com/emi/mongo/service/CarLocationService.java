package com.emi.mongo.service;

import com.emi.mongo.common.enums.ValidState;
import com.emi.mongo.domain.CarLocation;
import com.emi.mongo.domain.Community;
import com.emi.mongo.domain.Member;
import com.emi.mongo.dto.location.vo.LocationVo;
import com.emi.mongo.dto.location.vo.LocationWaitVo;
import com.emi.mongo.repository.CarLocationRepository;
import com.emi.mongo.repository.CommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by emi on 2017/6/14.
 */
@Service
@Transactional
public class CarLocationService {

    private final ServiceUtils serviceUtils;
    private final CommunityRepository communityRepository;
    private final CarLocationRepository carLocationRepository;
//    private final LocationMapper locationMapper;

    @Autowired
    public CarLocationService(ServiceUtils serviceUtils, CommunityRepository communityRepository, CarLocationRepository carLocationRepository) {
        this.serviceUtils = serviceUtils;
        this.communityRepository = communityRepository;
        this.carLocationRepository = carLocationRepository;
    }


    public LocationVo addLocation(String memberId, String communityId, String carLocation) {

        Community community = serviceUtils.fetchCommunity(communityId, memberId);
        community.checkState(ValidState.Pass.getValue());

        Member member = serviceUtils.fetchMember(memberId);

        CarLocation location = new CarLocation(member, community, carLocation);
        carLocationRepository.save(location);
        return new LocationVo(location.getId(), carLocation, ValidState.Wait.getValue(), location.getCreateTime());
    }

    public List<LocationVo> listLocation(String memberId, String communityId) {

        serviceUtils.fetchCommunity(communityId, memberId);
        return carLocationRepository.findListByMemberIdAndCommunityId(memberId, communityId, new Sort(Sort.Direction.DESC, "id"));
    }

    public List<LocationWaitVo> listWaitValidLocation() {
//        List<LocationWaitValidSqlVo> locationSqlList =  locationMapper.findListByValidState(ValidState.Wait.getValue());
//        List<LocationWaitVo> locationVoList = new ArrayList<>();
//        for (LocationWaitValidSqlVo l : locationSqlList) {
//            locationVoList.add(new LocationWaitVo(new LocationVo(l.getId(), l.getName(), l.getValidState(), l.getCreateTime()), new CommunityBaseVo(l.getCommunityId(), l.getCommunity()),
//                    new MemberDocVo(l.getMemberId(), l.getRealName(), l.getSex(), true)));
//        }
//        return locationVoList;
        return null;
    }

    public void validLocation(String locationId, int validState) {
        CarLocation carLocation = serviceUtils.fetchCarLocation(locationId, "");
        carLocation.setValidState(validState == ValidState.Pass.getValue() ? validState : ValidState.Failed.getValue());
        carLocationRepository.save(carLocation);
    }
}
