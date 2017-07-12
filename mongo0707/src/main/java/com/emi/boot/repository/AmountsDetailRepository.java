package com.emi.boot.repository;

import com.emi.boot.domain.AmountsDetail;
import com.emi.boot.dto.amounts.vo.AmountsVo;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by emi on 2017/6/22.
 */
public interface AmountsDetailRepository extends MongoRepository<AmountsDetail, String> {

//    @Query("select new com.emi.boot.dto.amounts.vo.AmountsVo(a.id, a.amountsType, a.title, a.income, a.amounts, a.createTime) " +
//            " from AmountsDetail a " +
//            " where a.memberId = ?1 " +
//            " order by  a.id desc")
    @Query(value = "{memberId: ?0}", fields = "{'id':1, 'amountsType':1, 'title':1, 'income':1, 'amounts':1, 'createTime':1}")
    List<AmountsVo> findByMemberId(String memberId, Sort sort);
//    long id, int amountsType, String title, boolean income, int amounts, String createTime
}
