package com.m1kah.rx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Completable;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class InMemoryRepository implements TicketRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryRepository.class);
    private List<Ticket> tickets = new ArrayList<>();

    @Override
    public Observable<Ticket> find(String film) {
        return Observable.from(findBlocking(film))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation());
    }

    @Override
    public Observable<Integer> count() {
        return Observable
                .fromCallable(this::countBlocking)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation());
    }

    @Override
    public Completable add(Ticket ticket) {
        return Completable.create(s -> {
            addBlocking(ticket);
            s.onCompleted();
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation());
    }

    private List<Ticket> findBlocking(String film) {
        log.info("Finding films in database of {} tickets", tickets.size());
        return tickets.stream()
                .filter(ticket -> ticket.getMovie().equals(film))
                .collect(Collectors.toList());
    }

    private Void addBlocking(Ticket ticket) {
        tickets.add(ticket);
        log.info("Added ticket to database. There are {} tickets now", tickets.size());
        return null;
    }

    private Integer countBlocking() {
        return tickets.size();
    }
}
