package com.emi.mongo.repository.dao;

import com.emi.mongo.common.enums.ValidState;
import com.emi.mongo.domain.Community;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * Created by emi on 2017/7/12.
 */
@Repository
public class CommunityDao extends BaseDao<Community>{

    public CommunityDao() {
        super(Community.class);
    }


    public List<Community> findWaitByValidState(ValidState validState) {
        Query q = query(where("validState").is(validState.getValue()).and("active").is(true));
        return find(q.with(new Sort(Sort.Direction.DESC, "createTime")));
    }
}
