package com.emi.boot.repository;

import com.emi.boot.domain.Orders;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by emi on 2017/6/21.
 */
public interface OrdersRepository extends MongoRepository<Orders, String> {

    Orders findByOrderNoAndActiveTrue(String orderNo);

    List<Orders> findByOrderStateAndOrderEndTimeBeforeAndActiveTrue(int orderState, Date currentTime);

    List<Orders> findByCommunityIdAndOrderStateAndActiveTrueOrderByIdDesc(long communityId, int value);

    List<Orders> findByLessorIdAndActiveTrueOrderByIdDesc(long lessorId);

    List<Orders> findByLesseeIdAndActiveTrueOrderByIdDesc(long memberId);
}
