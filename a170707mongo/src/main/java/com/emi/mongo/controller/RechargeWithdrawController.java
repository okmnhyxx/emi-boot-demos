package com.emi.mongo.controller;

import com.emi.mongo.dto.member.MemberInfoResponse;
import com.emi.mongo.dto.recharge.MemberRechargeRequest;
import com.emi.mongo.service.RechargeWithdrawService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by yunni on 2017/6/24.
 */
@RestController
@RequestMapping("/api")
public class RechargeWithdrawController {


    @Autowired
    private RechargeWithdrawService rechargeWithdrawService;

    @ApiOperation("用户充值，充押金")
    @RequestMapping(value = "/member/{memberId}/recharge", method = RequestMethod.POST)
    public MemberInfoResponse recharge(@PathVariable("memberId")@ApiParam(value = "用户Id", required = true) String memberId, @ModelAttribute @Valid MemberRechargeRequest request) {

        return new MemberInfoResponse(rechargeWithdrawService.recharge(memberId, request));
    }
}
