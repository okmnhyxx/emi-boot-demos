package com.emi.mongo.repository.dao;

import com.emi.mongo.domain.BaseDomain;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * Created by emi on 2017/7/12.
 */
public class BaseDao<T extends BaseDomain>{

    @Autowired
    private MongoTemplate mongoTemplate;

    private final Class<T> domainType;
    public BaseDao(Class<T> domainType) {
        this.domainType = domainType;
    }

    public WriteResult remove(Query query) {
        return mongoTemplate.remove(query, this.domainType);
    }

    public List<T> find(Query query) {
//        mongoTemplate.aggregate(newAggregation(unwind("")), )
        return mongoTemplate.find(query, this.domainType);
    }
}
