package com.emi.boot.repository.dao;

import com.emi.boot.domain.BaseDomain;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by emi on 2017/7/12.
 */
@Repository
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
        return mongoTemplate.find(query, this.domainType);
    }
}
