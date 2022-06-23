package com.jpmc.theater.repository;

import com.jpmc.theater.Movie;
import com.jpmc.theater.MovieType;
import com.jpmc.theater.Showing;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class TestMovieScheduleProvider implements MovieScheduleProvider{
    @Override
    public List<Showing> getMovieShowings() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, MovieType.SPECIAL);
        Movie turningRed = new Movie("Turning Red", Duration.ofMinutes(85), 11);
        return List.of(
                new Showing(turningRed, 1, LocalDateTime.of(LocalDate.of(2022, 06, 22), LocalTime.of(9, 0))),
                new Showing(spiderMan, 2, LocalDateTime.of(LocalDate.of(2022, 06, 22), LocalTime.of(9, 0)))
        );
    }
}
