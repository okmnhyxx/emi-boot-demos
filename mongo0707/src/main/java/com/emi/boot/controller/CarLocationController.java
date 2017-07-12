package com.emi.boot.controller;

import com.emi.boot.common.enums.DomainType;
import com.emi.boot.common.enums.ParamType;
import com.emi.boot.common.util.ValidUtils;
import com.emi.boot.dto.RestResponse;
import com.emi.boot.dto.location.CarLocationAddResponse;
import com.emi.boot.dto.location.CarLocationListResponse;
import com.emi.boot.dto.location.CarLocationWaitListResponse;
import com.emi.boot.service.CarLocationService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by emi on 2017/6/13.
 */
@RestController
@RequestMapping(value = "/api")
public class CarLocationController {

    private final CarLocationService carLocationService;

    @Autowired
    public CarLocationController(CarLocationService carLocationService) {
        this.carLocationService = carLocationService;
    }

    //todo: api是否正常
    @ApiOperation(value = "新建小区车位")
    @ApiImplicitParam(name = "carLocation", value = "车位", required = true, dataType = "string", paramType = "form")
    @RequestMapping(value = "/member/{memberId}/community/{communityId}/location/add", method = RequestMethod.POST)
    public CarLocationAddResponse addLocation(@PathVariable("memberId")@ApiParam(value = "用户Id", required = true) String memberId, @PathVariable("communityId")@ApiParam(value = "小区Id", required = true) String communityId, String carLocation) {
        return new CarLocationAddResponse(carLocationService.addLocation(ValidUtils.idNotNull(DomainType.Member, memberId), ValidUtils.idNotNull(DomainType.Community, communityId), ValidUtils.notBlank(ParamType.CarLocation, carLocation)));
    }

    @ApiOperation(value = "获取我的小区车位列表")
//    @ApiImplicitParam(name = "carLocation", value = "车位", required = true, dataType = "string", paramType = "form")
    @RequestMapping(value = "/member/{memberId}/community/{communityId}/location/list", method = RequestMethod.GET)
    public CarLocationListResponse listLocation(@PathVariable("memberId")@ApiParam(value = "用户Id", required = true) String memberId, @PathVariable("communityId")@ApiParam(value = "小区Id", required = true) String communityId) {
        return new CarLocationListResponse(carLocationService.listLocation(ValidUtils.idNotNull(DomainType.Member, memberId), ValidUtils.idNotNull(DomainType.Community, communityId)));
    }

    @ApiOperation(value = "管理员：获取待审核的车位列表")
    @RequestMapping(value = "/location/wait/list", method = RequestMethod.GET)
    public CarLocationWaitListResponse waitValidLocation() {
        return new CarLocationWaitListResponse(carLocationService.listWaitValidLocation());
    }

    @ApiOperation("管理员：审核车位")
    @RequestMapping(value = "/location/{locationId}/{state}", method = RequestMethod.POST)
    public RestResponse validLocation(@PathVariable("locationId")@ApiParam(value = "车位Id", required = true) String locationId, @PathVariable("state")@ApiParam(value = "2通过， 3不通过", allowableValues = "2,3", required = true) int validState) {
        carLocationService.validLocation(ValidUtils.idNotNull(DomainType.CarLocation, locationId), validState);
        return new RestResponse();
    }
}
