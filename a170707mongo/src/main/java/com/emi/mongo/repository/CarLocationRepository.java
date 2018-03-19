package com.emi.mongo.repository;

import com.emi.mongo.domain.CarLocation;
import com.emi.mongo.dto.location.vo.LocationVo;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by emi on 2017/6/14.
 */
public interface CarLocationRepository extends MongoRepository<CarLocation, String> {

    //todo:
//    @Query("select new com.emi.mongo.dto.location.vo.LocationVo(l.id, l.name, l.validState, l.createTime) from CarLocation l " +
//            "where l.memberId = ?1 and l.communityId = ?2 and l.active = true order by l.id asc")
    @Query(value = "{'memberId': ?0, 'communityId': ?1, 'active':true}", fields = "{'id':1, 'name':1, 'validState':1, 'createTime':1}")
    List<LocationVo> findListByMemberIdAndCommunityId(String memberId, String communityId, Sort sort);

}
