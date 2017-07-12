package com.emi.boot.controller;

import com.emi.boot.common.enums.DomainType;
import com.emi.boot.common.util.ValidUtils;
import com.emi.boot.dto.order.*;
import com.emi.boot.service.OrderService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by emi on 2017/6/20.
 */
@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ApiOperation("下单 租用车位")
    @RequestMapping(value = "/member/{memberId}/set/order", method = RequestMethod.POST)
    public OrderSetResponse setOrder(@PathVariable("memberId")@ApiParam(value = "下单用户Id", required = true) String memberId, @Valid @ModelAttribute OrderSetRequest request) {

        return new OrderSetResponse(orderService.setOrder(memberId, request));
    }

    @ApiOperation("取消订单")
    @RequestMapping(value = "/member/{memberId}/order/{orderId}/cancel", method = RequestMethod.POST)
    public OrderCancelResponse cancelOrder(@PathVariable("memberId")@ApiParam(value = "用户Id", required = true) String memberId, @PathVariable("orderId")@ApiParam(value = "订单Id", required = true) String orderId) {

        String resultMsg = orderService.cancelOrder(ValidUtils.idNotNull(DomainType.Member, memberId), ValidUtils.idNotNull(DomainType.Orders, orderId));
        return new OrderCancelResponse(resultMsg);
    }

    @ApiOperation("保安扫码，用户入库或出库")
    @ApiImplicitParam(name = "orderNo", value = "订单号", required = true, paramType = "form")
    @RequestMapping(value = "/order/qrcode", method = RequestMethod.POST)
    public OrderComeGoResponse scanner(String orderNo) {

        return orderService.scanner(orderNo);
    }

    @ApiOperation("保安获取小区订单(租用中，已超时)列表")
    @RequestMapping(value = "/community/{communityId}/order/list", method = RequestMethod.GET)
    public OrderUsingListResponse communityOrderList(@PathVariable long communityId) {
        return new OrderUsingListResponse(orderService.communityOrderList(communityId));
    }

    @ApiOperation("我发布的订单列表")
    @RequestMapping(value = "/memberId/{memberId}/published/order/list", method = RequestMethod.GET)
    public OrderMemberListResponse memberPublishedOrderList(@PathVariable("memberId")@ApiParam(value = "用户Id", required = true) long memberId) {

        return new OrderMemberListResponse(orderService.memberPublishedOrderList(memberId));
    }

    @ApiOperation("我租用的订单列表")
    @RequestMapping(value = "/memberId/{memberId}/hired/order/list", method = RequestMethod.GET)
    public OrderMemberListResponse memberHiredOrderList(@PathVariable("memberId")@ApiParam(value = "用户Id", required = true) long memberId) {

        return new OrderMemberListResponse(orderService.memberHiredOrderList(memberId));
    }

}
