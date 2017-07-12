package com.emi.boot.repository;

import com.emi.boot.domain.Recharge;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by emi on 2017/6/26.
 */
public interface RechargeRepository extends MongoRepository<Recharge, String> {
}
