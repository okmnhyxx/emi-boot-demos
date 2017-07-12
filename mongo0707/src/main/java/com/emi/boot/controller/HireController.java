package com.emi.boot.controller;

import com.emi.boot.dto.hire.HireListRequest;
import com.emi.boot.dto.hire.HireListResponse;
import com.emi.boot.dto.hire.HirePublishRequest;
import com.emi.boot.dto.hire.HirePublishResponse;
import com.emi.boot.service.HireService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by emi on 2017/6/14.
 */
@RestController
@RequestMapping(value = "/api")
public class HireController {

    @Autowired
    private HireService hireService;

    @ApiOperation("发布出租信息")
    @RequestMapping(value = "/member/{memberId}/hire", method = RequestMethod.POST)
    public HirePublishResponse publishHire(@PathVariable("memberId")@ApiParam(value = "出租人Id", required = true) String memberId, @Valid @ModelAttribute HirePublishRequest request) {

        return new HirePublishResponse(hireService.publishHire(memberId, request));
    }

    @ApiOperation("获取某小区今日的出租列表")
    @RequestMapping(value = "/hire/list", method = RequestMethod.GET)
    public HireListResponse hireList(@Valid @ModelAttribute HireListRequest request) {

        return new HireListResponse(hireService.searchList(request));
    }

    @ApiOperation("获取我的发布历史")
    @RequestMapping(value = "/member/{memberId}/hire/list", method = RequestMethod.GET)
    public HireListResponse myHireList(@PathVariable("memberId")@ApiParam(value = "用户Id", required = true) String memberId) {
        return new HireListResponse(hireService.myHireList(memberId));
    }


}
