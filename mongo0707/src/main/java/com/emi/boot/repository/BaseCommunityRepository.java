package com.emi.boot.repository;

import com.emi.boot.domain.BaseCommunity;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by emi on 2017/6/19.
 */
public interface BaseCommunityRepository extends MongoRepository<BaseCommunity, String> {
}
