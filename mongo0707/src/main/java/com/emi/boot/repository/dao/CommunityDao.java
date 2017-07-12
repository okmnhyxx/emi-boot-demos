package com.emi.boot.repository.dao;

import com.emi.boot.common.enums.ValidState;
import com.emi.boot.domain.Community;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * Created by emi on 2017/7/12.
 */
@Repository
public class CommunityDao extends BaseDao<Community>{

    public CommunityDao(Class<Community> domainType) {
        super(domainType);
    }


    public List<Community> findWaitByValidState(ValidState validState) {
        return find(query(where("validState").is(validState.getValue()).and("active").is(true)));
    }
}
