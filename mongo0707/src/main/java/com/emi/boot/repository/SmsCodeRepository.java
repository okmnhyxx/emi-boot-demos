package com.emi.boot.repository;

import com.emi.boot.domain.SmsCode;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by emi on 2017/6/13.
 */
public interface SmsCodeRepository extends MongoRepository<SmsCode, String> {

    SmsCode findFirstByPhoneAndSmsTypeOrderByIdDesc(String phone, int value);


}
