package com.jpmc.theater;

import com.google.gson.Gson;
import com.jpmc.theater.repository.MovieScheduleProvider;
import com.jpmc.theater.repository.TestMovieScheduleProvider;
import com.jpmc.theater.utils.DefaultLocalDateProvider;
import com.jpmc.theater.utils.LocalDateProvider;
import com.jpmc.theater.utils.TestLocalDateProvider;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TheaterTests {

    LocalDateProvider localDateProvider = new TestLocalDateProvider();
    MovieScheduleProvider movieScheduleProvider = new TestMovieScheduleProvider();

    @Test
    void totalFeeForCustomer() {
        Theater theater = new Theater(movieScheduleProvider, localDateProvider);
        Customer john = new Customer("John Doe", "id-12345");
        Reservation reservation = theater.reserve(john, 1, 4);
//        System.out.println("You have to pay " + reservation.getTotalFee());
        assertTrue(reservation.totalFee().compareTo(new BigDecimal("44")) == 0);
    }

    @Test
    void invalidSequence() {
        Theater theater = new Theater(movieScheduleProvider, localDateProvider);
        Customer john = new Customer("John Doe", "id-12345");
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                theater.reserve(john, 200, 4)
        );
        assertEquals("not able to find any showing for given sequence 200", exception.getMessage());
    }

    @Test
    void printMovieScheduleAsText() {
        Theater theater = new Theater(movieScheduleProvider, localDateProvider);
        String str = "2022-06-22\n" +
                     "===================================================\n" +
                     "1: 2022-06-22T09:00 Turning Red (1 hour 25 minutes) $11.0\n" +
                     "2: 2022-06-22T09:00 Spider-Man: No Way Home (1 hour 30 minutes) $12.5\n" +
                     "===================================================\n";
        assertEquals(str, theater.scheduleTextFormat());

    }

    @Test
    void printMovieScheduleAsJson() {
        Theater theater = new Theater(movieScheduleProvider, localDateProvider);
        String str = "{\"date\":\"2022-06-22\",\"schedules\":[{\"sequenceOfDay\":1,\"startTime\":\"2022-06-22T09:00:00\",\"runningTime\":\" $11.0\",\"title\":\"Turning Red\"},{\"sequenceOfDay\":2,\"startTime\":\"2022-06-22T09:00:00\",\"runningTime\":\" $12.5\",\"title\":\"Spider-Man: No Way Home\"}]}";
        assertEquals(str, theater.scheduleJsonFormat());
        Map jsonMap = new Gson().fromJson(str, Map.class);
        assertEquals(((List)jsonMap.get("schedules")).size(), 2);
    }
}
