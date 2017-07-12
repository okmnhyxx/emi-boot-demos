package com.emi.boot.controller;

import com.emi.boot.common.util.SmsUtils;
import com.emi.boot.dto.RestResponse;
import com.emi.boot.dto.basic.SmsCodeFetchRequest;
import com.emi.boot.dto.basic.SmsCodeFetchResponse;
import com.emi.boot.service.BasicService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by emi on 2017/6/7.
 */
@RestController
@RequestMapping(value = "/api/basic")
public class BasicController {

    private final SmsUtils smsUtils;
    private final BasicService basicService;

    @Autowired
    public BasicController(SmsUtils smsUtils, BasicService basicService) {
        this.smsUtils = smsUtils;
        this.basicService = basicService;
    }

    @ApiOperation("获取短信验证码")
    @RequestMapping(value = "/sms", method = RequestMethod.POST)
    public RestResponse SendSmsCode(@Valid @ModelAttribute SmsCodeFetchRequest request) {

        String smsCode = smsUtils.sendMsg(request.getPhone());
        basicService.saveSmsCode(smsCode, request.getPhone(), request.getSmsType());
        return new RestResponse();
    }

    @ApiOperation(value = "查看数据库，某手机号可供登录的验证码", notes = "非开发接口，用来节省发短信次数的")
    @RequestMapping(value = "/sms/{phone}", method = RequestMethod.GET)
    @ApiImplicitParam(name = "phone", value = "要获取已有验证的手机号", required = true, paramType = "path")
    public SmsCodeFetchResponse fetchSms(@PathVariable String phone) {
        return new SmsCodeFetchResponse(basicService.fetchSms(phone));
    }
}
