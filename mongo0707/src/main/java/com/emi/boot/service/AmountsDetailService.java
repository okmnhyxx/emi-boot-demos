package com.emi.boot.service;

import com.emi.boot.dto.amounts.vo.AmountsVo;
import com.emi.boot.repository.AmountsDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by emi on 2017/6/27.
 */
@Service
@Transactional
public class AmountsDetailService {

    private final ServiceUtils serviceUtils;
    private final AmountsDetailRepository amountsDetailRepository;

    @Autowired
    public AmountsDetailService(ServiceUtils serviceUtils, AmountsDetailRepository amountsDetailRepository) {
        this.serviceUtils = serviceUtils;
        this.amountsDetailRepository = amountsDetailRepository;
    }

    public List<AmountsVo> listMemberAmounts(String memberId) {

        serviceUtils.fetchMember(memberId);
        return amountsDetailRepository.findByMemberId(memberId, new Sort(Sort.Direction.DESC, "id"));
    }
}
