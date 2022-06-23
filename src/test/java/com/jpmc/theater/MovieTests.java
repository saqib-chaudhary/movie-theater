package com.jpmc.theater;

import com.jpmc.theater.utils.LocalDateProvider;
import com.jpmc.theater.utils.TestLocalDateProvider;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MovieTests {
    LocalDateProvider localDateProvider = new TestLocalDateProvider();

    @Test
    void specialMovieWith50PercentDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 100, MovieType.SPECIAL);
        Showing showing = new Showing(spiderMan, 5, LocalDateTime.of(localDateProvider.currentDate(), LocalTime.of(18, 0, 0)));
        assertTrue(new BigDecimal(80).compareTo(showing.getDiscountedMovieFee()) == 0);
    }

    @Test
    void zeroDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 100);
        Showing showing = new Showing(spiderMan, 10, LocalDateTime.of(localDateProvider.currentDate(), LocalTime.of(20, 0, 0)));
        assertTrue(new BigDecimal(100).compareTo(showing.getDiscountedMovieFee()) == 0);
    }

    @Test
    void discountCannotMakePriceNegative() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 1, MovieType.SPECIAL);
        Showing showing = new Showing(spiderMan, 1, LocalDateTime.of(localDateProvider.currentDate(), LocalTime.of(18, 0, 0)));
        assertTrue(new BigDecimal(0).compareTo(showing.getDiscountedMovieFee()) == 0);
    }

    @Test
    void discountForMoviesBetween11and4() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 100);
        Showing showing = new Showing(spiderMan, 4, LocalDateTime.of(localDateProvider.currentDate(), LocalTime.of(11, 30, 0)));
        assertTrue(new BigDecimal(75).compareTo(showing.getDiscountedMovieFee()) == 0);
    }

    @Test
    void discountForMoviesFor7thDay() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 100);
        Showing showing = new Showing(spiderMan, 4, LocalDateTime.of(LocalDate.of(2022, 06, 07), LocalTime.of(18, 30, 0)));
        assertTrue(new BigDecimal(99).compareTo(showing.getDiscountedMovieFee()) == 0);
    }

    @Test
    void maxDiscountForMoviesFor7thDay() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 100, MovieType.SPECIAL);
        Showing showing = new Showing(spiderMan, 1, LocalDateTime.of(LocalDate.of(2022, 06, 07), LocalTime.of(14, 30, 0)));
        assertTrue(new BigDecimal(75).compareTo(showing.getDiscountedMovieFee()) == 0);
    }

}
