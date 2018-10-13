package com.m1kah.rx;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.math.BigDecimal;
import java.util.List;

public class TicketService {
    private static final Logger log = LoggerFactory.getLogger(TicketService.class);
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    Observable<BigDecimal> revenueForFilm(String film) {
        // Call repository. We know that it will execute query in io scheduler
        // and result will be passed back in computation scheduler. Wait
        // for result in calling thread.
        List<Ticket> tickets = ticketRepository.find(film).toList().toBlocking().first();
        // Return calculation observable. Calculation will be completed
        // in calling thread.
        return Observable.from(tickets)
                    .reduce(
                    new BigDecimal("0.00"),
                    (revenue, ticket) -> {
                        log.info("revenue: {}, price: {}", revenue, ticket.getPrice());
                        return revenue.add(ticket.getPrice());
                    });
    }
}
