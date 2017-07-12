package com.emi.boot.repository;

import com.emi.boot.domain.Community;
import com.emi.boot.dto.community.vo.CommunityVo;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by emi on 2017/6/13.
 */
public interface CommunityRepository extends MongoRepository<Community, String> {

    //todo:
//    @Query("select new com.emi.boot.dto.community.vo.CommunityVo(c.id, c.name, c.buildingNo, c.validState, c.createTime) from Community c " +
//            "where c.memberId = ?1 and c.active = true order by c.id asc")
    @Query(value = "{'memberId': ?0, 'active': true}", fields = "{'id':1, 'name':1, 'buildingNo':1, 'validState':1, 'createTime':1}")
    List<CommunityVo> findListByMemberId(String memberId, Sort sort);

//    @Query("select new com.emi.boot.dto.community.vo.CommunityVo(c.id, c.name, c.buildingNo, c.validState) from Community c " +
//            "where c.validState = ?1 and c.active = true order by c.id desc")
//    List<CommunityVo> findListByValidStateOrderByCreateTimeDesc(int validState);
}
