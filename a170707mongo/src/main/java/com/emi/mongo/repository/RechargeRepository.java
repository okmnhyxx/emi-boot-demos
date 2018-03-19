package com.emi.mongo.repository;

import com.emi.mongo.domain.Recharge;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by emi on 2017/6/26.
 */
public interface RechargeRepository extends MongoRepository<Recharge, String> {
}
