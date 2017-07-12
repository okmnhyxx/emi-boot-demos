package com.emi.boot.controller;

import com.emi.boot.common.enums.DomainType;
import com.emi.boot.common.util.ValidUtils;
import com.emi.boot.dto.amounts.MemberAmountsListResponse;
import com.emi.boot.service.AmountsDetailService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by emi on 2017/6/27.
 */
@RestController
@RequestMapping("/api")
public class AmountsDetailController {

    private final AmountsDetailService amountsService;

    @Autowired
    public AmountsDetailController(AmountsDetailService amountsService) {
        this.amountsService = amountsService;
    }

    @RequestMapping(value = "/member/{memberId}/amounts/list", method = RequestMethod.GET)
    public MemberAmountsListResponse listMemberAmounts(@PathVariable @ApiParam(name = "memberId", value = "用户Id", required = true) String memberId) {

        return new MemberAmountsListResponse(amountsService.listMemberAmounts(ValidUtils.idNotNull(DomainType.Member, memberId)));
    }
}
