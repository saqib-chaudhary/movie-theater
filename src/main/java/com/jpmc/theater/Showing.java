package com.jpmc.theater;

import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Showing {
    private Movie movie;
    private int sequenceOfTheDay;
    private LocalDateTime showStartTime;

    public Showing(Movie movie, int sequenceOfTheDay, LocalDateTime showStartTime) {
        Validate.notNull(movie);
        Validate.notNull(showStartTime);
        this.movie = movie;
        this.sequenceOfTheDay = sequenceOfTheDay;
        this.showStartTime = showStartTime;
    }

    public Movie getMovie() {
        return movie;
    }

    public LocalDateTime getStartTime() {
        return showStartTime;
    }

    public boolean isSequence(int sequence) {
        return this.sequenceOfTheDay == sequence;
    }

    public BigDecimal getMovieFee() {
        return movie.getTicketPrice();
    }

    public BigDecimal getDiscountedMovieFee() {
        BigDecimal discount = calculateDiscount();
        BigDecimal newPrice = movie.getTicketPrice().subtract(discount);
        if (newPrice.compareTo(BigDecimal.ZERO) < 0) return BigDecimal.ZERO;
        return newPrice;
    }

    public int getSequenceOfTheDay() {
        return sequenceOfTheDay;
    }

    private BigDecimal calculateDiscount() {
        Movie movie = getMovie();
        BigDecimal originalTicketPrice = movie.getTicketPrice();

        List<BigDecimal> discounts = new ArrayList<>();
        int showSequence = getSequenceOfTheDay();

        if (movie.getMovieType() == MovieType.SPECIAL) {
            discounts.add(originalTicketPrice.multiply(new BigDecimal("0.2")));  // 20% discount for special movie
        }

        if (showSequence == 1) {
            discounts.add(new BigDecimal("3"));
        } else if (showSequence == 2) {
            discounts.add(new BigDecimal("2"));
        }

        if (getStartTime().getHour() >= 11 && getStartTime().getHour() <= 16){
            discounts.add(originalTicketPrice.multiply(new BigDecimal("0.25")));  // 25% discount for 11 am to 4pm show
        }

        if (getStartTime().getDayOfMonth()==7){
            discounts.add(new BigDecimal(1));  // 25% discount for 11 am to 4pm show
        }

        if (discounts.size() == 0) return BigDecimal.ZERO;
        Optional<BigDecimal> max = discounts.stream()
                                            .max(Comparator.naturalOrder());
        if (max.isPresent()) {
            return max.get();
        }
        return BigDecimal.ZERO;
    }

}
