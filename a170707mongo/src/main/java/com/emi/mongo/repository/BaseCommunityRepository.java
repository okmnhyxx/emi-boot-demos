package com.emi.mongo.repository;

import com.emi.mongo.domain.BaseCommunity;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by emi on 2017/6/19.
 */
public interface BaseCommunityRepository extends MongoRepository<BaseCommunity, String> {
}
