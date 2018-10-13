package com.m1kah.rx;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.math.BigDecimal;

public class TicketService {
    private static final Logger log = LoggerFactory.getLogger(TicketService.class);
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    Observable<BigDecimal> revenueForFilm(String film) {
        return ticketRepository.find(film)
                .reduce(
                        new BigDecimal("0.00"),
                        (revenue, ticket) -> {
                            log.info("revenue: {}, price: {}", revenue, ticket.getPrice());
                            return revenue.add(ticket.getPrice());
                        });
    }
}
