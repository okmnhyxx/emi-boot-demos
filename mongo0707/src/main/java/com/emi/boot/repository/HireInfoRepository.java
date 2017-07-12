package com.emi.boot.repository;

import com.emi.boot.domain.HireInfo;
import com.emi.boot.vo.HireSqlVo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by emi on 2017/6/16.
 */
public interface HireInfoRepository extends MongoRepository<HireInfo, String> {

    //todo:
//    @Query("select new com.emi.boot.vo.HireSqlVo(h.id, h.amounts, h.hired, h.hireTimeStr, h.createTime, h.memberId, h.communityId, h.community, h.carLocationId, h.carLocation) " +
//            " from HireInfo h" +
//            " where h.memberId = ?1 " +
//            " and h.active = true")
    @Query(value = "{'memberId': ?0, 'active': true}", fields = "{'id':1, 'amounts':1, 'hired': 1, 'hireTimeStr':1, 'createTime':1, 'memberId':1, 'communityId':1, 'community':1, 'carLocationId':1, 'carLocation':1}")
    List<HireSqlVo> fetchMemberPublishedHireInfoOrderByIdDesc(String memberId);

}
