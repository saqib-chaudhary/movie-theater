package com.jpmc.theater;

import com.jpmc.theater.utils.DefaultLocalDateProvider;
import com.jpmc.theater.utils.TestLocalDateProvider;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocalDateProviderTests {
    @Test
    void makeSureCurrentTime() {
        System.out.println("current time is - " + DefaultLocalDateProvider.singleton(). currentDate());
    }

    @Test
    void makeSureTestImplementation() {
        assertEquals(LocalDate.of(2022, 06, 22), new TestLocalDateProvider().currentDate());
    }
}
