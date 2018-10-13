package com.m1kah.rx;

import java.math.BigDecimal;

public class Ticket {
    private final String movie;
    private final BigDecimal price;
    private SeatingClass seatingClass;

    public Ticket(String movie, BigDecimal price) {
        this.movie = movie;
        this.price = price;
        seatingClass = SeatingClass.standard;
    }

    public String getMovie() {
        return movie;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public SeatingClass getSeatingClass() {
        return seatingClass;
    }

    public void setSeatingClass(SeatingClass seatingClass) {
        this.seatingClass = seatingClass;
    }
}
