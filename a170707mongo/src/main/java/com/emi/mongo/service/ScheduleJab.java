package com.emi.mongo.service;

import com.emi.mongo.common.enums.OrderState;
import com.emi.mongo.common.util.DateUtils;
import com.emi.mongo.domain.Member;
import com.emi.mongo.domain.Orders;
import com.emi.mongo.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by emi on 2017/6/23.
 */
@Component
@Configurable
@EnableScheduling
public class ScheduleJab {

    private final ServiceUtils serviceUtils;
    private final OrdersRepository ordersRepository;

    @Autowired
    public ScheduleJab(ServiceUtils serviceUtils, OrdersRepository ordersRepository) {
        this.serviceUtils = serviceUtils;
        this.ordersRepository = ordersRepository;
    }


    @Scheduled(cron = "0 0 */1 * * ?")
    public void orderMissJob() {
        Date date = new Date();
        List<Orders> ordersList = ordersRepository.findByOrderStateAndOrderEndTimeBeforeAndActiveTrue(OrderState.WaitCome.getValue(), date);
        System.out.println(" -----------------------------------" + DateUtils.sdf1.format(new Date()) + "-----------------------------------");
        for (Orders o : ordersList) {
            Member member = serviceUtils.fetchMember(o.getLesseeId());
            serviceUtils.orderMiss(o, member);
        }
    }
}
