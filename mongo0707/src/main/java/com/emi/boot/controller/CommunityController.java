package com.emi.boot.controller;

import com.emi.boot.common.enums.DomainType;
import com.emi.boot.common.util.ValidUtils;
import com.emi.boot.dto.RestResponse;
import com.emi.boot.dto.community.CommunityAddRequest;
import com.emi.boot.dto.community.CommunityAddResponse;
import com.emi.boot.dto.community.CommunityListResponse;
import com.emi.boot.dto.community.CommunityWaitListResponse;
import com.emi.boot.service.CommunityService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by emi on 2017/6/13.
 */
@RestController
@RequestMapping(value = "/api")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    //todo: api是否正常
//    @ApiImplicitParam(name = "memberId", value = "用户Id", required = true, dataType = "long", paramType = "path")
    @ApiOperation(value = "新建小区信息 [application/json]", notes = "首次添加小区时需传入身份信息进行验证，请求及返回的memberVo都不为空，app要将返回的memberVo更新到本地；非首次添加请忽略请求与返回的memberVo")
    @RequestMapping(value = "/member/{memberId}/community/add", method = RequestMethod.POST)
    public CommunityAddResponse addCommunity(@PathVariable("memberId")@ApiParam(value = "用户Id", required = true) String memberId, @RequestBody @Valid CommunityAddRequest request) {
        return communityService.addCommunity(memberId, request);
    }

    @ApiOperation(value = "获取我的小区列表", notes = "该接口用到的地方有两处， ①是小区管理，此时的小区状态如何显示请按需求来， ②是发布出租信息时，获取小区列表， 此时需要app自己过滤状态为“已审核”的小区，即validState=2的")
    @RequestMapping(value = "/member/{memberId}/community/list", method = RequestMethod.GET)
    public CommunityListResponse listCommunity(@PathVariable("memberId")@ApiParam(value = "用户Id", required = true) String memberId) {
        return new CommunityListResponse(communityService.listCommunity(memberId));
    }

    @ApiOperation("管理员：获取待审核的小区列表")
    @RequestMapping(value = "/community/wait/list", method = RequestMethod.GET)
    public CommunityWaitListResponse waitValidCommunity() {
        return new CommunityWaitListResponse(communityService.listWaitValidCommunity());
    }

    @ApiOperation("管理员：审核小区")
    @RequestMapping(value = "/community/{communityId}/{state}", method = RequestMethod.POST)
    public RestResponse validCommunity(@PathVariable("communityId")@ApiParam(value = "小区Id", required = true) String communityId, @PathVariable("state")@ApiParam(value = "2通过， 3不通过", allowableValues = "2,3", required = true) int validState) {
        communityService.validCommunity(ValidUtils.idNotNull(DomainType.Community, communityId), validState);
        return new RestResponse();
    }


}
