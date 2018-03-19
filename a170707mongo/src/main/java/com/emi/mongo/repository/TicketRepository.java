package com.emi.mongo.repository;

import com.emi.mongo.domain.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by emi on 2017/6/22.
 */
public interface TicketRepository extends MongoRepository<Ticket, String> {
}
