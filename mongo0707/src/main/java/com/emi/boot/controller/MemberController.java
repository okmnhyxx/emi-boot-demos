package com.emi.boot.controller;

import com.emi.boot.common.enums.DomainType;
import com.emi.boot.common.util.ValidUtils;
import com.emi.boot.dto.RestResponse;
import com.emi.boot.dto.member.MemberInfoResponse;
import com.emi.boot.dto.member.MemberListResponse;
import com.emi.boot.dto.member.MemberLoginRequest;
import com.emi.boot.service.MemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by emi on 2017/6/7.
 */
@RestController
@RequestMapping(value = "/api/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public MemberInfoResponse register(@Valid @ModelAttribute MemberLoginRequest request) {

        return memberService.login(request);
    }

    @ApiOperation(value = "获取我的基本信息")
    @RequestMapping(value = "/{memberId}/info", method = RequestMethod.GET)
    public MemberInfoResponse info(@PathVariable("memberId") @ApiParam(value = "用户Id", required = true) String memberId) {

        return new MemberInfoResponse(memberService.info(memberId));
    }


    @ApiOperation("管理员：获取待审核的用户列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public MemberListResponse waitValidMember() {
        return new MemberListResponse(memberService.listWaitValidMember());
    }


    @ApiOperation("管理员：审核用户")
    @RequestMapping(value = "/{memberId}/{state}", method = RequestMethod.POST)
    public RestResponse validMember(@PathVariable("memberId") @ApiParam(value = "用户Id", required = true) String memberId, @PathVariable("state")@ApiParam(value = "2通过， 3不通过", allowableValues = "2,3", required = true) int validState) {
        memberService.validMember(ValidUtils.idNotNull(DomainType.Member, memberId), validState);
        return new RestResponse();
    }


}
