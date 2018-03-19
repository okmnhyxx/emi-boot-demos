package com.emi.mongo.service;

import com.emi.mongo.common.enums.SmsType;
import com.emi.mongo.common.util.CommonUtils;
import com.emi.mongo.domain.SmsCode;
import com.emi.mongo.repository.SmsCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by emi on 2017/7/6.
 */
@Service
@Transactional
public class BasicService {

    private final SmsCodeRepository smsCodeRepository;

    @Autowired
    public BasicService(SmsCodeRepository smsCodeRepository) {
        this.smsCodeRepository = smsCodeRepository;
    }

    public void saveSmsCode(String code, String phone, int smsType) {
        SmsCode smsCode = new SmsCode(phone, code, smsType, true);
        smsCodeRepository.save(smsCode);
    }

    public String fetchSms(String phone) {
        SmsCode smsCode = smsCodeRepository.findFirstByPhoneAndSmsTypeOrderByIdDesc(phone, SmsType.Login.getValue());
        if (null == smsCode) {
            smsCode = new SmsCode(phone, CommonUtils.generateRandom(4, 1), SmsType.Login.getValue(), false);
            smsCodeRepository.save(smsCode);
        }
        return smsCode.getCode();
    }
}
