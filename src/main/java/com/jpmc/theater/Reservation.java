package com.jpmc.theater;

import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;

public class Reservation {
    private Customer customer;
    private Showing showing;
    private int audienceCount;

    public Reservation(Customer customer, Showing showing, int audienceCount) {
        Validate.notNull(customer);
        Validate.notNull(showing);
        this.customer = customer;
        this.showing = showing;
        this.audienceCount = audienceCount;
    }

    public BigDecimal totalFee() {
        return showing.getMovieFee().multiply(BigDecimal.valueOf(audienceCount));
    }
}