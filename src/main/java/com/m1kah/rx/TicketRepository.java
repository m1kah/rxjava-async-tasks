package com.m1kah.rx;


import rx.Completable;
import rx.Observable;

public interface TicketRepository {
    Completable add(Ticket ticket);
    Observable<Ticket> find(String film);
    Observable<Integer> count();
}
