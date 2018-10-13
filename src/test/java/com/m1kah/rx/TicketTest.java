package com.m1kah.rx;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.math.BigDecimal;

public class TicketTest {
    private static final Logger log = LoggerFactory.getLogger(TicketTest.class);
    TicketService ticketService;
    TicketRepository ticketRepository;

    @Before
    public void setup() {
        ticketRepository = new InMemoryRepository();
        ticketService = new TicketService(ticketRepository);
    }

    @Test
    public void shouldCalculateRevenue() {
        log.info("Starting test");
        injectData();
        log.info("Calculating revenue");
        BigDecimal revenue = ticketService.revenueForFilm("Matrix 4")
                .toBlocking()
                .last();
        log.info("Revenue: {}", revenue);
    }

    private void injectData() {
        Observable.fromCallable(this::makeTicket)
                .repeat(10)
                .flatMap(ticket -> ticketRepository.add(ticket).toObservable())
                .doOnCompleted(() -> log.info("There are {} tickets", ticketRepository.count().toBlocking().first()))
                .subscribe();
    }

    private Ticket makeTicket() {
        return new Ticket("Matrix 4", new BigDecimal("19.50"));
    }
}
