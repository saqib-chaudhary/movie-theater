package com.jpmc.theater;

import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Objects;

public class Movie {
    private static int MOVIE_CODE_SPECIAL = 1;
    private String title;
    private String description;
    private Duration runningTime;
    private BigDecimal ticketPrice;
    private MovieType movieType;

    public Movie(String title, Duration runningTime, double ticketPrice, MovieType type) {
        this(title, runningTime, BigDecimal.valueOf(ticketPrice), type);
    }

    public Movie(String title, Duration runningTime, BigDecimal ticketPrice, MovieType type) {
        Validate.notNull(title);
        Validate.notNull(runningTime);
        Validate.notNull(ticketPrice);
        Validate.notNull(type);
        this.title = title;
        this.runningTime = runningTime;
        this.ticketPrice = ticketPrice;
        this.movieType = type;
    }

    public Movie(String title, Duration runningTime, BigDecimal ticketPrice) {
        this(title, runningTime, ticketPrice, MovieType.REGULAR);
    }

    public Movie(String title, Duration runningTime, double ticketPrice) {
        this(title, runningTime, ticketPrice, MovieType.REGULAR);
    }

    public String getTitle() {
        return title;
    }

    public Duration getRunningTime() {
        return runningTime;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public MovieType getMovieType() {
        return movieType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return (movie.ticketPrice).compareTo(ticketPrice) == 0
               && Objects.equals(title, movie.title)
               && Objects.equals(description, movie.description)
               && Objects.equals(runningTime, movie.runningTime)
               && Objects.equals(movieType, movie.movieType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, runningTime, ticketPrice, movieType);
    }
}