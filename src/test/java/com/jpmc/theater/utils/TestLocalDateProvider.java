package com.jpmc.theater.utils;

import java.time.LocalDate;

public class TestLocalDateProvider implements LocalDateProvider{
    @Override
    public LocalDate currentDate() {
        return LocalDate.of(2022, 06, 22);
    }
}
