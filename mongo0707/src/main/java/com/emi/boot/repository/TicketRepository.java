package com.emi.boot.repository;

import com.emi.boot.domain.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by emi on 2017/6/22.
 */
public interface TicketRepository extends MongoRepository<Ticket, String> {
}
