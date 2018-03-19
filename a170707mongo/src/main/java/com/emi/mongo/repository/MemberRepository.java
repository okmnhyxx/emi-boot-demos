package com.emi.mongo.repository;

import com.emi.mongo.domain.Member;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by emi on 2017/6/7.
 */
public interface MemberRepository extends MongoRepository<Member, String> {

    Member findByPhone(String phone);

//    todo:
//    @Query("select new com.emi.mongo.dto.member.vo.MemberVo(m.id, m.phone, m.realName, m.idCard, m.sex, m.deposit, m.balance, m.freezeAmounts, m.validState, true) from Member m " +
//            "where m.validState = ?1 and m.active = true order by m.id desc")
//    @Query(value = "{'validState': ?0, 'active' : true}", fields = "{'id':1, 'phone':1, 'realName':1,'idCard':1,'sex':1, 'balance':1, 'freezeAmounts':1, 'validState':1, 'validStateStr': '$validState'}")
    List<Member> findListByValidStateOrderByCreateTimeDesc(int validState);
}
